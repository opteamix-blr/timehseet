package com.ewconline.timesheet

import java.util.Date;
import org.codehaus.groovy.grails.commons.*
import grails.util.Environment;
import com.ewconline.timesheet.User;
import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import java.util.Hashtable;
/**
 * This is responsible authenticating users and populating a user object with
 * role information. The only significant role is Accountant Role which is
 * controlled by Active Directory Server. All people
 * will be 'self' role meaning they are employees.
 */
class LdapAuthenticationService {
    static transactional = true
    def etimeSecurityService
    def authenticate(username, password) {

        def config = ConfigurationHolder.config
        String keystore = config.timesheet.keystore;
        System.setProperty("javax.net.ssl.trustStore", keystore);
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, config.timesheet.ldap.url);
        env.put(Context.SECURITY_PRINCIPAL, username + config.timesheet.email.suffix);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        InitialLdapContext ctx = null;
        def user = new User(username:username, passwd:'nothing')
        user.authorities = []
        try {
            ctx = new InitialLdapContext(env)

            SearchControls ctls = new SearchControls();
            ctls.setReturningObjFlag (true);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String filter = "(userPrincipalName=${username}${config.timesheet.email.suffix})";
            NamingEnumeration answer =  ctx.search(config.timesheet.search.users, filter, ctls);
            def roles = []
            Role selfRole = Role.findByAuthority(etimeSecurityService.SELF_ROLE)
            roles.add(selfRole)

            // obtain all attributes to populate user object.
            while(answer.hasMore()) {
                SearchResult item = (SearchResult) answer.next();
                Attributes attr = item.getAttributes();

                // get guid on this object
                user.guid = attr?.get("objectGUID")?.get()
                
                // Get the display name
                //Removed to support first name and last name
//                user.userRealName = attr?.get("displayName")?.get()

                // Get the primary email address
                user.email = attr?.get("mail")?.get()
                if (!user.email) {
                    user.email = "${username}${config.timesheet.email.suffix}"
                }

                // build role information
                int size = attr?.get("memberOf")?.size()
                def memberOf = attr?.get("memberOf").getAll()
                
                if (size > 0) {
                    while (memberOf.hasMore()) {
                        def mValue = memberOf.next();
                        if (mValue.contains('CN=Administrators')){
                            Role role = Role.findByAuthority(etimeSecurityService.ADMIN_ROLE)
                            if (!roles.contains(role)) {
                                roles.add(role)
                            }
                        }
                        if (mValue.contains('CN=Accountants')){
                            Role role = Role.findByAuthority(etimeSecurityService.ACCOUNTANT_ROLE)
                            if (!roles.contains(role)) {
                                roles.add(role)
                            }
                            break;
                        }
                        //println "memberOf: ${mValue}"
                   }
                }
            }
            
            user.authorities.addAll(roles)

        } finally {
            if (ctx != null) {
                // Close the context when we're done
                ctx.close();
            }
        }
        
        def tempUser = User.findByUsername(username)
        if (!tempUser) {
            user.save(flush: true)
            for ( r in user.authorities ) {
                r.addToPeople(user)
            }
        } else {
            // union or revoke roles based on (left is db right is ldap)
            //  employee, approver
            boolean changes = false

            // accountant role
            Role accountantRole = Role.findByAuthority(etimeSecurityService.ACCOUNTANT_ROLE)
            if (!tempUser.authorities.contains(accountantRole) &&
                user.authorities.contains(accountantRole)) {
                Role.findByAuthority(etimeSecurityService.ACCOUNTANT_ROLE).addToPeople(tempUser)
                changes = true
            } else if (tempUser.authorities.contains(accountantRole) &&
                !user.authorities.contains(accountantRole)) {
                Role.findByAuthority(etimeSecurityService.ACCOUNTANT_ROLE).removeFromPeople(tempUser)
                changes = true
            }

            // admin role
            Role adminRole = Role.findByAuthority(etimeSecurityService.ACCOUNTANT_ROLE)
            if (!tempUser.authorities.contains(adminRole) &&
                user.authorities.contains(adminRole)) {
                user.authorities.add(adminRole)
                Role.findByAuthority(etimeSecurityService.ADMIN_ROLE).addToPeople(tempUser)
                changes = true
            } else if (tempUser.authorities.contains(adminRole) &&
                !user.authorities.contains(adminRole)) {
                user.authorities.add(adminRole)
                Role.findByAuthority(etimeSecurityService.ADMIN_ROLE).removeFromPeople(tempUser)
                changes = true
            }
            // insure all authenticated people are employees (too many not in employee role)
            Role selfRole = Role.findByAuthority(etimeSecurityService.SELF_ROLE)
            if (!tempUser.authorities.contains(selfRole)) {
                tempUser.authorities.add(selfRole)
                selfRole.addToPeople(tempUser)
                changes = true
            }
            if (changes) {
                tempUser.save(flush: true)
            }
        }
        def user2 = User.findByUsername(username)
        
        return user2
    } // authenticate
}
