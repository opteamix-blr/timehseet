package com.ewconline.timesheet

import java.util.Date;
import org.codehaus.groovy.grails.commons.*
import grails.util.Environment;
import com.ewconline.timesheet.User;
import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import java.util.Hashtable;

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
            //ctx = new InitialLdapContext( env)
            ctx = new InitialLdapContext(env)
            //println('yay you made it into the application ' + username )

            SearchControls ctls = new SearchControls();
            ctls.setReturningObjFlag (true);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String filter = "(userPrincipalName=${username}${config.timesheet.email.suffix})";
            NamingEnumeration answer =  ctx.search(config.timesheet.search.users, filter, ctls);
            def roles = []
            Role selfRole = Role.findByAuthority(etimeSecurityService.SELF_ROLE)
            roles.add(selfRole)
            
            while(answer.hasMore()) {
                SearchResult item = (SearchResult) answer.next();
                Attributes attr = item.getAttributes();

                // get guid on this object
                user.guid = attr?.get("objectGUID")?.get()
                
                // Get the display name
                user.userRealName = attr?.get("displayName")?.get()

                // Get the primary email address
                user.email = attr.get("mail").get()

                // build role information
                def memberOf = "${attr?.get("memberOf")?.get()}"
                
                
                if (memberOf) {

                    if (memberOf.indexOf('Administrators') > -1){
                        Role role = Role.findByAuthority(etimeSecurityService.ADMIN_ROLE)
                        if (!roles.contains(role)) {
                            roles.add(role)
                        }
                    }
                    if (memberOf.indexOf('Accountants') > -1){
                        Role role = Role.findByAuthority(etimeSecurityService.ACCOUNTANT_ROLE)
                        if (!roles.contains(role)) {
                            roles.add(role)
                        }
                    }
//                    if (memberOf.indexOf('Employees') > -1){
//                        Role role = Role.findByAuthority(etimeSecurityService.SELF_ROLE)
//                        if (!roles.contains(role)) {
//                            roles.add(role)
//                        }
//                    }
                }
                //println "memberOf: ${attr?.get("memberOf")?.get()}"
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
