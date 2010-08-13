package com.ewconline.timesheet

import grails.test.*

class TimesheetTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCreateTimesheet() {
        Timesheet t = createTimesheet()
        assertEquals(t.startDate, new Date("07/01/2010"))
        assertEquals(t.endDate, new Date("07/15/2010"))
    }

    private Timesheet createTimesheet(){
        return new Timesheet(startDate:new Date("07/01/2010"), endDate:new Date("07/15/2010"))
    }
}
