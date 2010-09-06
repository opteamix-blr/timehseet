package com.ewconline.timesheet

import grails.test.*

class WorkdayUpdateTests extends ParentTimesheetTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCreateAndUpdateWorkday() {
		User u = super.createUser ("tuser", "Test User")
		LaborCategory lc = super.createLaborCategory()
		ChargeCode cc = super.createChargeCode()
		Timesheet ts = super.createTimesheet (u, new Date("07/1/2010"), new Date("07/15/2010"))
		
		Timesheet foundt = Timesheet.findAllByUser(u)[0]
		assertNotNull foundt
		
		def TimesheetEntry te = super.createTimesheetEntry (ts, lc, cc)
		assertNotNull te
		
		def Workday wd = super.createWorkday (te, new Date("07/05/2010"), 8.0)
		assertNotNull wd
		assertEquals wd.hoursWorked, 8.0
		
		wd.hoursWorked = 9.5
		
		u.save()
		
		def totalHours = 0.0
		totalHours = te.workdays.inject(totalHours) {
			total, item -> total + item.hoursWorked
		}
		
		assertEquals(totalHours, 9.5)
		
    }
}
