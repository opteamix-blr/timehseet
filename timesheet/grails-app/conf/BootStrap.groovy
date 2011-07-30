import java.util.TimeZone;

import com.ewconline.timesheet.ChargeCode 
import com.ewconline.timesheet.LaborCategory 
import com.ewconline.timesheet.Role;
import com.ewconline.timesheet.Task 
import com.ewconline.timesheet.TaskAssignment;
import com.ewconline.timesheet.Timesheet 
import com.ewconline.timesheet.TimesheetEntry 
import com.ewconline.timesheet.User;
import com.ewconline.timesheet.Workday 

import grails.util.Environment;
import hirondelle.date4j.DateTime 

class BootStrap {
    def timesheetManagerService
    def etimeSecurityService
    def init = { servletContext ->
		
    	switch(Environment.current) {
                case Environment.DEVELOPMENT:
                case Environment.TEST:
                        createRolesIfRequired()
                        createAdminUserIfRequired()
                        createTestUsers()
                        break;
                case Environment.PRODUCTION:
                        createRolesIfRequired()
                        break;
        }
    }

    def destroy = {
    }

	void createRolesIfRequired() {
                if (!Role.findByAuthority(etimeSecurityService.SELF_ROLE)) {
                    def employeeRole = new Role(description:"Employee Role",
			authority:etimeSecurityService.SELF_ROLE
                    ).save()
                }

                if (!Role.findByAuthority(etimeSecurityService.APPROVER_ROLE)) {
                    def approverRole = new Role(description:"Approver Role",
                            authority:etimeSecurityService.APPROVER_ROLE
                    ).save()
                }

                if (!Role.findByAuthority(etimeSecurityService.ACCOUNTANT_ROLE)) {
                    def accountantRole = new Role(description:"Accountant Role",
			authority:etimeSecurityService.ACCOUNTANT_ROLE
                    ).save()
                }

		if (!Role.findByAuthority(etimeSecurityService.ADMIN_ROLE)) {
                    def adminRole = new Role(description:"Administrator Role",
                            authority:etimeSecurityService.ADMIN_ROLE
                    ).save()
                }
	}

	void createAdminUserIfRequired() {
		if (!User.findByUsername("accountant")) {
			println "Creating an admin user for ${Environment.current} environment"
			def accountantRole = Role.findByAuthority(etimeSecurityService.ACCOUNTANT_ROLE)

			def user = new User(username:"accountant",
				passwd:"p@ssw0rd1",
				userRealName:"Accountant User",
				description:"Dev Accountant user",
                                employeeId:"004",
                                guid:java.util.UUID.randomUUID().toString()
			)
			accountantRole.addToPeople(user)

			println "Created successfully username:" + user?.username
		} else {
			println "Existing admin user, skipping creation"
		}
	}
	void createTestUsers() {
		def user1 = new User(username:"emp1",
				passwd:"p@ssw0rd1",
				userRealName:"John Doe",
				description:"Employee",
				email:"test@test.com",
                                employeeId:"001",
                                guid:java.util.UUID.randomUUID().toString()
		)
		user1.save()
		Role.findByAuthority("self_role").addToPeople(user1)
		
		def user2 = new User(username:"approver1",
			passwd:"p@ssw0rd1",
			userRealName:"Jane Doe",
			description:"Employee and Approver",
                        employeeId:"002",
                        guid:java.util.UUID.randomUUID().toString()
		)
		
		Role.findByAuthority(etimeSecurityService.SELF_ROLE).addToPeople(user2)
		Role.findByAuthority(etimeSecurityService.APPROVER_ROLE).addToPeople(user2)
		user2.save();
		
		def user3 = new User(username:"accountant1",
			passwd:"p@ssw0rd1",
			userRealName:"Fred Sanford",
			description:"Employee and Accountant",
                        employeeId:"003",
                        guid:java.util.UUID.randomUUID().toString()
		)
		
		Role.findByAuthority(etimeSecurityService.SELF_ROLE).addToPeople(user3)
		Role.findByAuthority(etimeSecurityService.ACCOUNTANT_ROLE).addToPeople(user3)
		user3.save();
		
			
		def chargeCode1 = new ChargeCode(chargeNumber:"000-111-1111", 
			description:"Test Charge Code 1"
		).save()

		def chargeCode2 = new ChargeCode(chargeNumber:"000-222-2222",
			description:"Test Charge Code 2"
		).save()
		
		def chargeCode3 = new ChargeCode(chargeNumber:"000-333-3333",
			description:"Test Charge Code 3"
		).save()
		
		def laborCategory1 = new LaborCategory( name: "Engineer 1",
			description:"Test labor cat 1"
		).save()
		
		def laborCategory2 = new LaborCategory( name: "Engineer 2",
			description:"Test labor cat 2"
		).save()

		def laborCategoryA = new LaborCategory( name: "Truck driver",
			description:"Test Truck driver"
		).save()
		
		def laborCategoryB = new LaborCategory( name: "Plumber",
			description:"Test Plumber"
		).save()

                def task1 = new Task(name:"Task 1",
			description:"Test Task 1"
		).save()

                task1.addToChargeCodes(chargeCode1)
                task1.addToLaborCategories(laborCategory1)

		def task2 = new Task(name:"Task 2",
			description:"Test Task 2"
		).save()

                task2.addToChargeCodes(chargeCode2)
                task2.addToLaborCategories(laborCategory2)

		
		// user1 has work on two different tasks and two diff labor cats.
		def taskAssignment1 = new TaskAssignment( displayName: "job1",
			notes: "taskAssignment1 ",
			task: task1,
			chargeCode:chargeCode1,
			laborCategory:laborCategory1
		)
                user1.addToTaskAssignments(taskAssignment1)
		
		// a second task assignment means the employee 
		// will get two entries in their time sheet.
		def taskAssignment2 = new TaskAssignment( displayName: "job2",
			notes: "taskAssignment2 ",
			task: task2,
			chargeCode:chargeCode3,
			laborCategory:laborCategoryB
		)
                user1.addToTaskAssignments(taskAssignment2)
	}
	
}
