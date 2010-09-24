import com.ewconline.timesheet.ChargeCode 
import com.ewconline.timesheet.LaborCategory 
import com.ewconline.timesheet.Role;
import com.ewconline.timesheet.Task 
import com.ewconline.timesheet.User;

import grails.util.Environment;

class BootStrap {

    def init = { servletContext ->
		
    	switch(Environment.current) {
			case Environment.DEVELOPMENT:
			case Environment.TEST:
				createRolesIfRequired()
				createAdminUserIfRequired()
				createTestUsers()
				break;
			case Environment.PRODUCTION:
				break;
		}

	}
    def destroy = {
    }

	void createRolesIfRequired() {
		def employeeRole = new Role(description:"Employee Role",
			authority:"self"
		).save()
		
		def approverRole = new Role(description:"Approver Role",
			authority:"approve"
		).save()
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
	void createTestUsers() {
		def user1 = new User(username:"emp1",
				passwd:"p@ssw0rd1",
				userRealName:"John Doe",
				description:"Employee"
		)
		Role.findByAuthority("self").addToPeople(user1)
		def user2 = new User(username:"approver1",
			passwd:"p@ssw0rd1",
			userRealName:"Jane Doe",
			description:"Employee and Approver"
		)
		
		Role.findByAuthority("self").addToPeople(user2)
		Role.findByAuthority("approve").addToPeople(user2)
		
		def task1 = new Task(name:"Task 1", 
			description:"Test Task 1"
		)
		def task2 = new Task(name:"Task 2",
			description:"Test Task 2"
		)
		
		def chargeCode1 = new ChargeCode(chargeNumber:"000-111-1111", 
			description:"Test Charge Code 1"
		).save()
		def chargeCode2 = new ChargeCode(chargeNumber:"000-222-2222",
			description:"Test Charge Code 2"
		).save()
		
		def laborCategory1 = new LaborCategory( name: "Engineer 1",
			description:"Test labor cat 1"
		).save()
		
		def laborCategory2 = new LaborCategory( name: "Engineer 2",
			description:"Test labor cat 2"
		).save()
		
		task1.laborCategory = laborCategory1
		task1.chargeCode = chargeCode1
		task1.save()
		
		task2.laborCategory = laborCategory2
		task2.chargeCode = chargeCode2
		task2.save()
		
		//user1.addToLaborCategories(laborCategory1)
		user1.addToTasks(task1)
		//user1.addToChargeCodes(chargeCode1)
		
	}
	
}
