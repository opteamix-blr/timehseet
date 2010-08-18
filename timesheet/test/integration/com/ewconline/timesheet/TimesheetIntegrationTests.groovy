package com.ewconline.timesheet

import grails.test.*

class TimesheetIntegrationTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void testSaveTimesheet(){
		def Timesheet t = createTimesheet().save()
		def Timesheet foundt = Timesheet.get(t.id)
		assertNotNull(foundt)
	}

    void testCreateTimeSheetEntry(){
		def Timesheet t = new Timesheet(startDate:new Date("07/01/2010"), endDate:new Date("07/15/2010")).save()
		def TimesheetEntry te = new TimesheetEntry(laborCategory:createLaborCategory(), chargeCode:createChargeCode())
		t.addToTimesheetEntries(te)
		assertNotNull(Timesheet.get(t.id))
		assertEquals 1, Timesheet.get(t.id).timesheetEntries.size()
    }
	
	void testAddWorkday(){
		def Timesheet t = createTimesheet().save()
		def TimesheetEntry te = new TimesheetEntry(laborCategory:createLaborCategory(), chargeCode:createChargeCode())
		t.addToTimesheetEntries(te)
		def Workday w = new Workday(dateWorked:new Date("07/01/2010"), hoursWorked:8.5)
		te.addToWorkdays(w)
		
		def Timesheet foundt = Timesheet.get(t.id)
		assertNotNull(foundt)
		assertEquals(1, foundt.timesheetEntries.size())
		assertEquals(1, foundt.timesheetEntries.find{it.laborCategory.name == "Sys Eng 3"}.workdays.size())
	}

    private LaborCategory createLaborCategory(){
        return new LaborCategory(name:"Sys Eng 3", description:"System Engineer 3")
    }

    private ChargeCode createChargeCode(){
        return new ChargeCode(chargeNumber:"EPM II - BA5 - TTO 3C", description:"Test charge #")
    }
     private Timesheet createTimesheet(){
        return new Timesheet(startDate:new Date("07/01/2010"), endDate:new Date("07/15/2010"))
    }
}
