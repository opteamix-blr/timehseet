package com.ewconline.timesheet

import grails.test.*

class TimesheetDeleteTests extends ParentTimesheetTestCase {
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCreateAndDeleteTimesheet() {
		User u = super.createUser ("tuser", "Test User")
		Timesheet ts = super.createTimesheet (u, new Date("07/01/2010"), new Date("07/15/2010"))
		assertNotNull(ts)
		
		Timesheet foundt = Timesheet.findAllByUser(u)[0]
		assertNotNull(foundt)
		
		u.timesheets.remove(foundt)
		foundt.delete()
		assertFalse Timesheet.exists(foundt.id)		
    }
	
	void testCreateAndDeleteTimeSheetentry(){
		LaborCategory lc = super.createLaborCategory()
		ChargeCode cc = super.createChargeCode()		
		User u = super.createUser("tuser", "Test User")
		Timesheet ts = super.createTimesheet(u, new Date("07/01/2010"), new Date("07/15/2010"))
		assertNotNull ts
		
		Timesheet foundt = Timesheet.findAllByUser(u)[0]
		assertNotNull foundt
		
		TimesheetEntry te = super.createTimesheetEntry(foundt, lc, cc)
		assertNotNull te
		
		foundt.timesheetEntries.remove(te)
		assertEquals(foundt.timesheetEntries.size(), 0)
		
		te.delete()
		assertFalse TimesheetEntry.exists(te.id)
		
	}
}
