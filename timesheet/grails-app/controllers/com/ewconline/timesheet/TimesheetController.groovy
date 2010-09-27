package com.ewconline.timesheet


import java.util.TimeZone;

import hirondelle.date4j.DateTime;

import com.ewconline.timesheet.Timesheet 

class TimesheetController {
	def timesheetManagerService
	def scafold = true
    def index = { }
	
	
	//  http://localhost:8080/Timesheet/timesheet/listTimesheets
	def listTimesheets = {
		def user = User.get(session.user.id)
		def timesheetList = timesheetManagerService.retrieveTimesheets(user)
		// TODO use paging abilities pagination next, prev.
		//		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		//		[userInstanceList: User.list(params), userInstanceTotal: User.count()]
		
		[timesheetList:timesheetList]
	}
	
	def create = {
		// >validate "Am I allowed to create a timesheet?"
		// >is it in a prior timesheet
		// >  Yes,
		// >     any timesheets?
		// >     get the most recent and check if it falls in date range.
		//   No,
		//      Create a work week Mon-Sun (the timesheet).
		//      calculate from current date to closest monday in the past (could be today).
		//   
		// Doing the NO section.
		// 
		def user = User.get(session.user.id)
		Timesheet ts = timesheetManagerService.generateWeeklyTimesheet(user)

		[timesheetInstance:ts]
	}
	
	def save = {
		def user = User.get(session.user.id)
		
		// @todo must validate to not allow duplicates
		Timesheet timesheetInstance = timesheetManagerService.generateWeeklyTimesheet(user)
		timesheetInstance.user=user
		// obtain params
		
//		println "avail taskassignments:"
//		timesheetInstance.timesheetEntries.eachWithIndex { 
//			listValue, index -> println ">>>> chargeCode${index}" + params["chargeCode${index}"] 
//		}
//		for (ta in ts.taskAssignments) {
//			println ">>>> " + params["id"]
//		}
		try {
			if (timesheetManagerService.createWeeklyTimesheet(timesheetInstance)){
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), user.id])}"
				redirect(action: "listTimesheets", id: user.id)
			}
			else {
				render(view: "create", model: [timesheetInstance: timesheetInstance])
			}
		} catch (Exception e) {
			flash.message = e.getMessage()
			redirect(action: "listTimesheets", id: user.id)
		}
	}
	
	def show = {
		def timesheetInstance = Timesheet.get(params.id)
		if (!timesheetInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'Timesheet'), params.id])}"
			redirect(action: "list")
		}
		else {
			[timesheetInstance: timesheetInstance]
		}
	}
}
