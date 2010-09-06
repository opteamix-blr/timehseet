package com.ewconline.timesheet

import grails.test.*

class UserDeleteTests extends ParentTimesheetTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCreateAndDeleteUser() {
		def User u = super.createUser ("tuser", "Test User")
		def User tu = User.get(u.id)
		assertNotNull tu
		
		tu.delete()
		
		tu = User.get(u.id)
		assertNull tu
    }
	
	void testCreateAndDeleteUserTimesheet(){
		def User u = super.createUser ("tuser", "Test User")
		def Timesheet ts = super.createTimesheet (u, new Date("07/01/2010"), new Date("07/15/2010"))
		assertNotNull ts
		
		u.delete()
		
		def Timesheet foundt = Timesheet.get(ts.id)
		assertNull foundt		
	}
}
