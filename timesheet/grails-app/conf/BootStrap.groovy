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
			authority:"self_role"
		).save()
		
		def approverRole = new Role(description:"Approver Role",
			authority:"approver_role"
		).save()
		
		def accountantRole = new Role(description:"Accountant Role",
			authority:"accountant_role"
		).save()
		
		def assistantAccountantRole = new Role(description:"Accounting Assistant Role",
			authority:"accounting_assistant_role"
		).save()
		

	}

	void createAdminUserIfRequired() {
		if (!User.findByUsername("admin")) {
			println "Creating an admin user for $Environment.current environment"
			def adminRole = new Role(description:"Administrator Role",
				authority:"administrator_role"
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
				description:"Employee",
				email:"test@test.com"
		)
		Role.findByAuthority("self_role").addToPeople(user1)
		
		def user2 = new User(username:"approver1",
			passwd:"p@ssw0rd1",
			userRealName:"Jane Doe",
			description:"Employee and Approver"
		)
		
		Role.findByAuthority("self_role").addToPeople(user2)
		Role.findByAuthority("approver_role").addToPeople(user2)
		user2.save();
		
		def user3 = new User(username:"accountant1",
			passwd:"p@ssw0rd1",
			userRealName:"Fred Sanford",
			description:"Employee and Accountant"
		)
		
		Role.findByAuthority("self_role").addToPeople(user3)
		Role.findByAuthority("accountant_role").addToPeople(user3)
		user3.save();
		
		def task1 = new Task(name:"Task 1", 
			description:"Test Task 1"
		).save()
		
		def task2 = new Task(name:"Task 2",
			description:"Test Task 2"
		).save()
		
		def chargeCode1 = new ChargeCode(chargeNumber:"000-111-1111", 
			description:"Test Charge Code 1",
			task:task1
		).save()
		def chargeCode2 = new ChargeCode(chargeNumber:"000-222-2222",
			description:"Test Charge Code 2",
			task:task1
		).save()
		
		def chargeCode3 = new ChargeCode(chargeNumber:"000-333-3333",
			description:"Test Charge Code 3",
			task:task2
		).save()
		
		def laborCategory1 = new LaborCategory( name: "Engineer 1",
			description:"Test labor cat 1",
			task:task1
		).save()
		
		def laborCategory2 = new LaborCategory( name: "Engineer 2",
			description:"Test labor cat 2",
			task:task1
		).save()

		def laborCategoryA = new LaborCategory( name: "Truck driver",
			description:"Test Truck driver",
			task:task2
		).save()
		
		def laborCategoryB = new LaborCategory( name: "Plumber",
			description:"Test Plumber",
			task:task2
		).save()

		
		// user1 has work on two different tasks and two diff labor cats.
		def taskAssignment1 = new TaskAssignment( displayName: "job1",
			notes: "taskAssignment1 ",
			task: task1,
			chargeCode:chargeCode1,
			laborCategory:laborCategory1
		).save()
		
		// a second task assignment means the employee 
		// will get two entries in their time sheet.
		def taskAssignment2 = new TaskAssignment( displayName: "job2",
			notes: "taskAssignment2 ",
			task: task2,
			chargeCode:chargeCode3,
			laborCategory:laborCategoryB
		).save()
		
		
		user1.addToTaskAssignments(taskAssignment1)
		user1.addToTaskAssignments(taskAssignment2)
		user1.save()

//		// @TODO: create task assignments for approvers.
// below is was a timesheet on a weekend boundary. timesheet before current.
//		//DateTime currentDay = DateTime.today(TimeZone.getDefault())
//		DateTime saturday = new DateTime("2010-12-18").getStartOfDay();
//		DateTime friday = new DateTime("2010-12-24").getEndOfDay();
//		//DateTime saturday = currentDay.minusDays(currentDay.getWeekDay())
//		//DateTime friday = saturday.plusDays(6)
//
//		Timesheet ts = new Timesheet(
//			startDate:new Date(saturday.getMilliseconds(TimeZone.getDefault())),
//			endDate:new Date(friday.getMilliseconds(TimeZone.getDefault())),
//			user: user1,
//			currentState: timesheetManagerService.NOT_STARTED
//		)
//		
//		
//		def taskAssignments = user1.taskAssignments
//		for (ta in taskAssignments){
//			def timesheetEntry = new TimesheetEntry(taskAssignment:ta);
//			for (x in (0..6)){
//				timesheetEntry.addToWorkdays(new Workday(hoursWorked:8.0, 
//												dateWorked:new Date(saturday.plusDays(x)
//													                        .getEndOfDay()
//																			.getMilliseconds(TimeZone.getDefault())))
//				                             )
//			} // for loop
//			ts.addToTimesheetEntries(timesheetEntry)
//		}
//		
//		timesheetManagerService.createWeeklyTimesheet(ts)
	}
	
}
