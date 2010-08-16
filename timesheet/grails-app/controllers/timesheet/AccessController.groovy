package timesheet

class AccessController {

    //def index = { }
	def login = {
		
	}
	
	def logout = {
		// TODO remove session user
		redirect(action:login)
	}
	
	def authenticate = {
		// TODO use ldap to check username and password
		redirect(controller: "timesheet", action: "listTimesheets")
	}
}
