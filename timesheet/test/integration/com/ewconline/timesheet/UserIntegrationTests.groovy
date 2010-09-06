package com.ewconline.timesheet

import grails.test.*

class UserIntegrationTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCreateUser() {
		def Role r = new Role(description: "test description", authority: "test authority").save()
		r.addToPeople(new User(username: "Kyle", userRealName: "Kyle Burall"))
		def User u = r.people.find { it.username == "Kyle" }
		assertNotNull(u)
		
		u.addToTimesheets(new Timesheet(startDate: new Date("07/01/2010"), endDate: new Date("07/15/2010")))
		def Timesheet foundt = u.timesheets.find { it.startDate.getDateString() == new Date("07/01/2010").getDateString()}
		assertNotNull(foundt)
		
    }
}
