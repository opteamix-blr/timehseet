package com.ewconline.timesheet


import java.util.TimeZone;

import hirondelle.date4j.DateTime;

import com.ewconline.timesheet.Timesheet 

class TimesheetController {
	def timesheetManagerService
	def signatureService
	def scafold = true
    def index = { }
	
	
	//  http://localhost:8080/Timesheet/timesheet/listTimesheets
	def listTimesheets = {
		def user = User.get(session.user.id)
		def timesheetList = timesheetManagerService.retrieveTimesheets(user)
		// TODO use paging abilities pagination next, prev.
		//		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		//		[userInstanceList: User.list(params), userInstanceTotal: User.count()]
		
		[timesheetList:timesheetList, timesheetInstanceTotal:timesheetList.count()]
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
		// validate from form 
		def weekdaysModified = validateWorkdaysFromForm(timesheetInstance)
		if (weekdaysModified.count()> 0) {
			println (" " + weekdaysModified.count())
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
			flash.message = e.getMessage()
//			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), timesheetInstance.id])}"
			redirect(action: "show", id: timesheetInstance.id)
		}
	}
	def validateWorkdaysFromForm(timesheetInstance){
		def weekdaysModified = []
		
		timesheetInstance.timesheetEntries.eachWithIndex {
			tse, index ->
			def daysOfWeek = obtainWeekdays(tse.taskAssignment)
			tse.workdays.eachWithIndex { workday, indx ->
				def sysHoursWorked = workday?.hoursWorked
				if (sysHoursWorked == null) {
					sysHoursWorked = ""
				}
				if (daysOfWeek[indx] != sysHoursWorked.toString()) {
					Workday changedWorkday = new Workday()
					changedWorkday.dateWorked = workday?.dateWorked
					changedWorkday.hoursWorked = daysOfWeek[indx].toFloat()
						
					weekdaysModified.add changedWorkday
					println ("changedWorkday day" + indx + " value: was "  + workday?.hoursWorked)
					println ("changedWorkday day" + indx + " value: to  "  + changedWorkday.hoursWorked)
				}	
			}
		}
		return weekdaysModified
	}
	def obtainWeekdays(taskAssignment) {
		[ params["day1_${taskAssignment?.id}"],
			params["day2_${taskAssignment?.id}"],
			params["day3_${taskAssignment?.id}"],
			params["day4_${taskAssignment?.id}"],
			params["day5_${taskAssignment?.id}"],
			params["day6_${taskAssignment?.id}"],
			params["day7_${taskAssignment?.id}"]
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
	def signform = {
		def user = User.get(session.user.id)
		def tsId = params['id']
		def ts
		if (tsId) {
			ts = Timesheet.get(tsId)
		} else {
			ts = timesheetManagerService.retrieveCurrentTimesheet(user)
		}
		flash.message = ""
		[timesheetId:tsId]
	}
	
	def sign = {
		def user = User.get(session.user.id)
		
		// @todo must validate to not allow duplicates
		def tsId = params['timesheetId']
		
		Timesheet ts
		if (tsId) {
			ts = Timesheet.get(tsId)
		} else {
			ts = timesheetManagerService.retrieveCurrentTimesheet(user)
		}
		
		try {
			// update state of the timesheet
			def authenticateUser = User.findByUsernameAndPasswd(params.username, params.passwd)
			if (!authenticateUser || authenticateUser.id != user.id) {
				flash.message = "Authenticate failure, unable to sign timesheet"
				render(view: "edit", model: [timesheetInstance: ts])
			} else {
				signatureService.signTimesheet(ts, user)
				if (timesheetManagerService.sign(ts)){
					flash.message = "${message(code: 'timesheet signed successfully', args: [message(code: 'timesheet.label', default: 'Timesheet'), user.id])}"
					redirect(action: "listTimesheets", id: user.id)
				}
				else {
					render(view: "edit", model: [timesheetInstance: ts])
				}
			}
		} catch (Exception e) {
			flash.message = e.getMessage()
			redirect(action: "listTimesheets", id: user.id)
		}
	}
	
	
	def lookupSearchForm = {
		
	}
	
	def lookupMyTimesheets = {
		
		// TODO OBTAIN THE DATE RANGES
		
		def user = User.get(session.user.id)
		def timesheetList = timesheetManagerService.retrieveTimesheets(user)
		
		render(view: "listTimesheets", model: [timesheetList:timesheetList, timesheetInstanceTotal:timesheetList.count()])
	}
	def viewTimesheet = {
		def user = User.get(session.user.id)
		def ts = Timesheet.get(params.id)
		if (ts) {
			if (ts.user.id != session.user.id) {
				throw new RuntimeException("Access denied. This is not your timesheet")
			}
		}
		[timesheetInstance:ts]
	}
}
