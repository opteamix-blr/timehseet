package com.ewconline.timesheet

import grails.test.*

class TimesheetIntegrationTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void testCreatingWorkWeek(){
		Timesheet foundt = createTimesheetForWeek()
		assertNotNull(foundt)
		def TimesheetEntry foundte1 = foundt.timesheetEntries.find{it.laborCategory.name = "Sys Eng 3"}
		assertNotNull(foundte1)
		assertEquals(6, foundte1.workdays.size())
		def TimesheetEntry foundte2 = foundt.timesheetEntries.find{it.laborCategory.name = "SW Eng 4"}
		assertNotNull(foundte2)
		assertEquals(6, foundte2.workdays.size())
	}	
	
	void testWorkWeekProjections(){
		Timesheet foundt = createTimesheetForWeek()
		assertNotNull(foundt)
		def totalHours = 0
		
		for (entry in foundt.timesheetEntries){
			totalHours = entry.workdays.inject(totalHours) { 
				total, item -> total + item.hoursWorked 
				}
			}
		
		assertEquals(48, totalHours)
		
	}
	
	void testSaveTimesheet(){
		def Timesheet t = createTimesheet()
		assertNotNull(t)
	}

    void testCreateTimeSheetEntry(){
		def Timesheet t = createTimesheet()
		def TimesheetEntry te = new TimesheetEntry(laborCategory:createLaborCategory(), chargeCode:createChargeCode())
		t.addToTimesheetEntries(te)
		assertEquals 1, t.timesheetEntries.size()
    }
	
	void testAddWorkday(){
		def Timesheet t = createTimesheet()
		def TimesheetEntry te = new TimesheetEntry(laborCategory:createLaborCategory(), chargeCode:createChargeCode())
		t.addToTimesheetEntries(te)
		def Workday w = new Workday(dateWorked:new Date("07/01/2010"), hoursWorked:8.5)
		te.addToWorkdays(w)
		
		assertEquals(1, t.timesheetEntries.size())
		assertEquals(1, t.timesheetEntries.find{it.laborCategory.name == "Sys Eng 3"}.workdays.size())
	}

    private LaborCategory createLaborCategory(){
        return new LaborCategory(name:"Sys Eng 3", description:"System Engineer 3")
    }

    private ChargeCode createChargeCode(){
        return new ChargeCode(chargeNumber:"EPM II - BA5 - TTO 3C", description:"Test charge #")
    }
    private Timesheet createTimesheet(){
		def Role r = new Role(description: "test description", authority: "test authority").save()
		r.addToPeople(new User(username: "tuser", userRealName: "Test User"))
		def User u = r.people.find { it.username == "tuser" }
		assertNotNull(u)
		
		u.addToTimesheets(new Timesheet(startDate: new Date("07/01/2010"), endDate: new Date("07/15/2010")))
		def Timesheet foundt = u.timesheets.find { it.startDate.getDateString() == new Date("07/01/2010").getDateString()}
		assertNotNull(foundt)
		return foundt
    }
	 
	private Timesheet createTimesheetForWeek(){
		def Timesheet t = createTimesheet()
		def TimesheetEntry rowOne = new TimesheetEntry(
			laborCategory: new LaborCategory(name: "Sys Eng 3",
			description: "Systems Engineer 3"))
		def TimesheetEntry rowTwo = new TimesheetEntry(
			laborCategory: new LaborCategory(name: "SW Eng 4",
			description: "Software Engineer 4"))
		t.addToTimesheetEntries(rowOne)
		t.addToTimesheetEntries(rowTwo)
		
		
		def Date d = new Date("7/1/2010")
		
		for (x in 0..5){
			rowOne.addToWorkdays(dateWorked:d + x, hoursWorked:4.0)
			rowTwo.addToWorkdays(dateWorked:d + x, hoursWorked:4.0)
		}
		
		return t
	}
}
