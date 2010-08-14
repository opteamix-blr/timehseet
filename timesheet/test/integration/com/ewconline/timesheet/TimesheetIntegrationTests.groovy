package com.ewconline.timesheet

import grails.test.*

class TimesheetIntegrationTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCreateTimeSheetEntry(){
        Timesheet t = createTimesheet().save()
        ChargeCode c = createChargeCode()
        LaborCategory lc = createLaborCategory()
        TimesheetEntry te1 = new TimesheetEntry(laborCategory:lc, chargeCode:c, dateWorked:new Date("07/01/2010"), hoursWorked:8.5)
        assertEquals(new Date("07/01/2010"), te1.dateWorked)
        assertNotNull(te1)
        t.addToEntries(te1)

        assertEquals(1, t.entries.size())
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
