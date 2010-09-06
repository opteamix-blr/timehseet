package com.ewconline.timesheet

import grails.test.GrailsUnitTestCase;
import groovy.lang.MetaClass;

class ParentTimesheetTestCase extends GrailsUnitTestCase {
	
	protected void tearDown() {
		super.tearDown()	
	}
	
	protected void setUp(){
		super.setUp()
	}
	
	TimesheetEntry createTimesheetEntry(Timesheet ts, LaborCategory lc, ChargeCode cc){
		def TimesheetEntry te = new TimesheetEntry(
			laborCategory: lc,
			chargeCode: cc)
		ts.addToTimesheetEntries(te)
		return te
	}
	
	Workday createWorkday(TimesheetEntry te, Date dateWorked, double hoursWorked){
		Workday wd = new Workday(dateWorked:dateWorked, hoursWorked:hoursWorked)
		te.addToWorkdays(wd)
		return wd
	}
	
	Timesheet createTimesheet(User u, Date startDate, Date endDate){
		u.addToTimesheets(new Timesheet(startDate: startDate, endDate: endDate))
		def Timesheet foundt = u.timesheets.find { it.startDate.getDateString() == startDate.getDateString()}
		assertNotNull(foundt)
		return foundt
	}
	
    LaborCategory createLaborCategory(){
        def l = new LaborCategory(name:"Sys Eng 3", description:"System Engineer 3")
		l.save()
		return l
    }

    ChargeCode createChargeCode(){
        def ch = new ChargeCode(chargeNumber:"EPM II - BA5 - TTO 3C", description:"Test charge #")
		ch.save()
		return ch
    }
	
	Role createRole() {
		def Role r = new Role(description: "test description", authority: "test authority")
		r.save()
		return r
	}
	
	User createUser(String username, String userRealName) {
		def user = new User(username: username, userRealName: userRealName)
		user.save()
		return user
	}
	
}
