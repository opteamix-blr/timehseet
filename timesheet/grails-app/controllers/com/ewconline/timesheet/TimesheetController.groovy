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

		Timesheet duplicateTimesheet = timesheetManagerService.retrieveCurrentTimesheet(user)
		
		if(duplicateTimesheet){
			redirect(action:"edit", params:[id:duplicateTimesheet.id])
		} else {
			[timesheetInstance:ts]
		}
	}
	
	def save = {
		def user = User.get(session.user.id)
		
		// @todo must validate to not allow duplicates
		Timesheet timesheetInstance = timesheetManagerService.generateWeeklyTimesheet(user)
		populateWorkdaysFromForm(timesheetInstance)
		
		try {
			// update state of the timesheet
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
	
	def edit = {

		def user = User.get(session.user.id)
		def ts = Timesheet.get(params.id)
		if (ts == null) {
			ts = timesheetManagerService.retrieveCurrentTimesheet(user)
			if (ts == null) {
				flash.message = "No current timesheet. Please create a new timesheet for the week."
				redirect(action: "listTimesheets")
				return
			}
		}
		if (ts.user.id != user.id) {
			throw new RuntimeException("Attempting to update a timesheet assigned to a different user.")
		}
		[timesheetInstance:ts]
	}
	
	def update = {
		def user = User.get(session.user.id)
		Timesheet timesheetInstance = Timesheet.get(params.id)
		
		if (params.version) {
			def version = params.version.toLong()
			if (timesheetInstance.version > version) {
				timesheetInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'timesheet.label', default: 'Timesheet')] as Object[], "Another user has updated this Timesheet while you were editing")
				render(view: "edit", model: [timesheetInstance: timesheetInstance])
				return
			}
		}
		
		// update from form
		populateWorkdaysFromForm(timesheetInstance)
		try {
			if (!timesheetInstance.hasErrors()) {
				timesheetManagerService.update(timesheetInstance)
				redirect(action: "show", id: timesheetInstance.id)
			} else {
				render(view: "edit", model: [timesheetInstance: timesheetInstance])
			}
		} catch (Exception e) {
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), timesheetInstance.id])}"
			redirect(action: "show", id: timesheetInstance.id)
		}
	}
	
	def obtainWeekdays(taskAssignment) {
		[ params["day1_${taskAssignment?.chargeCode.id}"],
			params["day2_${taskAssignment?.chargeCode.id}"],
			params["day3_${taskAssignment?.chargeCode.id}"],
			params["day4_${taskAssignment?.chargeCode.id}"],
			params["day5_${taskAssignment?.chargeCode.id}"],
			params["day6_${taskAssignment?.chargeCode.id}"],
			params["day7_${taskAssignment?.chargeCode.id}"]
		]
	}
	
	def populateWorkdaysFromForm(timesheetInstance){
		timesheetInstance.timesheetEntries.eachWithIndex {
			tse, index ->
			def daysOfWeek = obtainWeekdays(tse.taskAssignment)
			tse.workdays.eachWithIndex { workday, indx ->
				
				if (daysOfWeek[indx] == "") {
					workday.hoursWorked = null
				} else {
					try {
						workday.hoursWorked = daysOfWeek[indx].toFloat()
					} catch (Exception e) {
						workday.hoursWorked = null
					}
				}
				
			} // clean up bad numbers.
		}
	}	
}
