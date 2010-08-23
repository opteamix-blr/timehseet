package com.ewconline.timesheet

import grails.test.GrailsUnitTestCase;
import groovy.lang.MetaClass;

class ParentTimesheetTestCase extends GrailsUnitTestCase {
	def timesheetIdsToDelete=[]
	def roleIdsToDelete=[]
	def userIdsToDelete=[]
	def laborCatIdsToDelete=[]
	def chargeCodeIdsToDelete=[]
	
	protected void tearDown() {
		super.tearDown()
		timesheetIdsToDelete.each { it ->
			def t = Timesheet.get(it)
			if (t) {
				t.delete()
			}
		}
		timesheetIdsToDelete.removeAll()
		
		laborCatIdsToDelete.each { it ->
			def l = LaborCategory.get(it)
			if (l) {
				l.delete()
			}
		}
		laborCatIdsToDelete.removeAll()
		
		chargeCodeIdsToDelete.each { it ->
			def ch = ChargeCode.get(it)
			if (ch) {
				ch.delete()
			}
		}
		chargeCodeIdsToDelete.removeAll()

		userIdsToDelete.each { it ->
			def u = User.get(it)
			if (u) {
				u.delete()
			}
		}
		userIdsToDelete.removeAll()
		
		roleIdsToDelete.each { it ->
			def r = Role.get(it)
			if (r) {
				r.delete()
			}
		}
		roleIdsToDelete.removeAll()
	
	}
	
    LaborCategory createLaborCategory(){
        def l = LaborCategory(name:"Sys Eng 3", description:"System Engineer 3")
		l.save()
		laborCatIdsToDelete.add(l.id)
		return l
    }

    ChargeCode createChargeCode(){
        def ch = ChargeCode(chargeNumber:"EPM II - BA5 - TTO 3C", description:"Test charge #")
		ch.save()
		chargeCodeIdsToDelete.add(ch.id)
		return ch
    }
	
	Role createRole() {
		def Role r = new Role(description: "test description", authority: "test authority")
		r.save()
		roleIdsToDelete.add(r.id)
		return r
	}
	
	User createUser(String username, String userRealName) {
		def user = new User(username: username, userRealName: userRealName)
		user.save()
		userIdsToDelete.add(user.id)
		return user
	}
	
}
