package com.ewconline.timesheet


import java.util.Date;

import grails.util.Environment;
import com.ewconline.timesheet.User;

class AccessController {

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
	
	def authenticate = {
		// TODO use ldap to check username and password
		// 1) ldap authenticate
		// 2) check database if user exists
		// 3) create session with user object in session.
		log.info "User agent: " + request.getHeader("User-Agent")
		// check database for user exists.
		log.debug "user name is : " + params.username
		
		def user
		if (Environment.current == Environment.DEVELOPMENT || Environment.current == Environment.TEST) {
			user = User.findByUsernameAndPasswd(params.username, params.passwd)
			log.debug "password is  : " + params.passwd
		} else {
			user = User.findByUsername(params.username)
		}

		if (user) {
			// obtain a session
			if (!session || !session.user) {
				 
				 session.user = user
				 log.debug "user named " + session.user.username + " is logged in"
			}
			
			redirect(controller: "timesheet", action: "listTimesheets")
		} else {
			redirect(action:login)
		}
	}
}
