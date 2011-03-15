package com.ewconline.timesheet

class EtimeSecurityService {
    static String SELF_ROLE = 'self_role'
    static String ACCOUNTANT_ROLE = 'accountant_role'
    static String APPROVER_ROLE = 'approver_role'
    static String ADMIN_ROLE = 'administrator_role'
    
    //static transactional = true

    def isInRole(user) {
//
//            def employeeRole = new Role(description:"Employee Role",
//            authority:"self_role"
//		).save()
//
//		def approverRole = new Role(description:"Approver Role",
//			authority:"approver_role"
//		).save()
//
//		def accountantRole = new Role(description:"Accountant Role",
//			authority:"accountant_role"
//		).save()
//
//        def config = ConfigurationHolder.config
//        String keystore = config.timesheet.keystore;
//        System.setProperty("javax.net.ssl.trustStore", keystore);
//        Hashtable env = new Hashtable();
//        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//        env.put(Context.PROVIDER_URL, config.timesheet.ldap.url);
//        env.put(Context.SECURITY_PRINCIPAL, params.username + config.timesheet.email.suffix);
//        env.put(Context.SECURITY_CREDENTIALS, params.passwd);
//        env.put(Context.SECURITY_PROTOCOL, "ssl");
//        InitialLdapContext ctx = null;
//        def user
//        try {
//            //ctx = new InitialLdapContext( env)
//            ctx = new InitialLdapContext(env)
//            println('yay you made it into the application ' + params.username )
//
//            SearchControls ctls = new SearchControls();
//            ctls.setReturningObjFlag (true);
//            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//            String filter = "(userPrincipalName="+params.username+"${config.timesheet.email.suffix})";
//            NamingEnumeration answer =  ctx.search(config.timesheet.search.users, filter, ctls);
//
//            while(answer.hasMore()) {
//                SearchResult item = (SearchResult) answer.next();
//                Attributes attr = item.getAttributes();
//
//                // Get the display name
//                user.userRealName = attr?.get("displayName")?.get()
//
//                // Get the primary email address
//                user.email = attr.get("mail").get()
//
//                // role information
//                def memberOf = "${attr?.get("memberOf")?.get()}"
//                if (memberOf) {
//                    if (memberOf.indexOf('Administrators')){
//
//                    }
//                }
//                println "memberOf: ${attr?.get("memberOf")?.get()}"
//
//            }
//        } finally {
//            if (ctx != null) {
//                // Close the context when we're done
//                ctx.close();
//            }
//        }
//
//        def tempUser = User.findByUsername(params.username)
//        if (!tempUser) {
//            user = new User();
//            user.username = params.username
//            user = user.save(flush: true)
//        } else {
//            user = tempUser
//        }
//
//        return user
    } // authenticate
}
