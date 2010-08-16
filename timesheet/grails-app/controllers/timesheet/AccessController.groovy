package timesheet

class AccessController {

    //def index = { }
	def login = {
		
	}
	
	def logout = {
		// todo remove session user
		redirect(action:login)
	}
}
