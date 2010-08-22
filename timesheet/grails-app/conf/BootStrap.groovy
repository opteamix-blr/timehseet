import com.ewconline.timesheet.Role;
import com.ewconline.timesheet.User;

import grails.util.Environment;

class BootStrap {

    def init = { servletContext ->
		
    	switch(Environment.current) {
			case Environment.DEVELOPMENT:
			case Environment.TEST:
				createAdminUserIfRequired()
				break;
			case Environment.PRODUCTION:
				break;
		}
		
	}
    def destroy = {
    }

	void createAdminUserIfRequired() {
		if (!User.findByUsername("admin")) {
			println "Creating an admin user for $Environment.current environment"
			def adminRole = new Role(description:"Admin Role",
				authority:"all"
			).save()
			
			def user = new User(username:"admin",
				passwd:"p@ssw0rd1",
				userRealName:"Administrator",
				description:"Dev Admin user"
			)
			adminRole.addToPeople(user)
			println "Created successfully username:" + user?.username
		} else {
			println "Existing admin user, skipping creation"
		}
	}
	
}
