package com.ewconline.timesheet


import java.util.Date;
import org.codehaus.groovy.grails.commons.*
import grails.util.Environment;
import com.ewconline.timesheet.User;
import java.lang.reflect.UndeclaredThrowableException;
import org.springframework.security.authentication.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import java.util.Hashtable;
import grails.util.Environment;
class AccessController {
    def ldapAuthenticationService
    def etimeSecurityService
    //def index = { }
    def login = {
		
    }
	
    def logout = {
        // TODO remove session user
        if (session) {
            if (session.user) {
                log.debug session.user.username + " is logging out."
            }
        }
        session.user = null
        session.invalidate()
        redirect(action:login)
    }
    def accessDenied = {
        render(view: "accessDenied", model: [user: session.user])
    }
    def authenticate = {
        // 1) ldap authenticate
        // 2) check database if user exists
        // 3) create session with user object in session.
        log.info "User agent: " + request.getHeader("User-Agent")
        // check database for user exists.
        log.debug "user name is : " + params.username
        
        def user
        try {
            def config = ConfigurationHolder.config

            if (config?.timesheet?.useLdap) {
                user = ldapAuthenticationService.authenticate(params.username, params.passwd)
            } else {

                if (Environment.current == Environment.DEVELOPMENT) {
                    user = User.findByUsername(params.username)
                    if (!user) {
                        flash.message = 'Invalid username or password.'
                        redirect(action:login)
                        return
                    }
                }
            }

        } catch (Exception e) {
            if (e instanceof UndeclaredThrowableException) {
                UndeclaredThrowableException proxyExc = (UndeclaredThrowableException) e
                if (proxyExc.getCause() instanceof AuthenticationException) {
                    flash.message = 'Invalid username or password.'
                    println("User: ${params.username} login failed. " )
                }
            } else {
                flash.message = "Unable to login. system error occurred."
                println("User: ${params.username} other login failure. " + e.getMessage() )
                        
            }
            redirect(action:login)
            return
        }
        // user made it.
        if (user) {
            //def user = User.get(userId)
            //                    for ( r in user.authorities ) {
            //                        println("*****> user ${user.username} is in role: ${r.authority}" )
            //                    }
            // obtain a session
            if (!session || !session.user) {
                session.user = user
            } else {
                // just swap out the user
                session.user = user
            }

            for ( r in user.authorities ) {
                if (r.authority == etimeSecurityService.APPROVER_ROLE) {
                    session.approverRole = true;
                } else if (r.authority == etimeSecurityService.ACCOUNTANT_ROLE) {
                    session.accountantRole = true;
                } else if (r.authority == etimeSecurityService.SELF_ROLE) {
                    session.employeeRole = true;
                } else if (r.authority == etimeSecurityService.ADMIN_ROLE) {
                    session.adminRole = true;
                }
            }
            log.debug "user named " + session.user.username + " is logged in"
            redirect(controller: "timesheet", action: "listTimesheets")
        } else {
            redirect(action:login)
        }
    }
}
