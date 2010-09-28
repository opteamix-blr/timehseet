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
		// populate from form hours for each timesheet entry.

		timesheetInstance.timesheetEntries.eachWithIndex { 
			tse, index -> //println ">>>> chargeCode${index}" + params["chargeCode${index}"]
			def daysOfWeek = [ params["day1_${tse?.taskAssignment?.chargeCode.id}"],
				params["day2_${tse?.taskAssignment?.chargeCode.id}"],
				params["day3_${tse?.taskAssignment?.chargeCode.id}"],
				params["day4_${tse?.taskAssignment?.chargeCode.id}"],
				params["day5_${tse?.taskAssignment?.chargeCode.id}"],
				params["day6_${tse?.taskAssignment?.chargeCode.id}"],
				params["day7_${tse?.taskAssignment?.chargeCode.id}"]
			]
			
			tse.workdays.eachWithIndex { workday, indx ->
				
				if (daysOfWeek[indx] == "") {
					workday.hoursWorked = 0
				} else {
					try {
						workday.hoursWorked = daysOfWeek[indx].toDouble()
					} catch (Exception e) {
						workday.hoursWorked = 0
					}
				}
				
			} // clean up bad numbers.
		}
		
//		// debug to display hours
//		for (te in timesheetInstance.timesheetEntries) {
//			for (wd in te.workdays) {
//				println "wd >>>> " + wd.hoursWorked
//			}
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
	
	def edit = {

		
		def user = User.get(session.user.id)
		def ts = Timesheet.get(params.id)
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
		
		
		timesheetInstance.timesheetEntries.eachWithIndex {
			tse, index -> 
			def daysOfWeek = [ params["day1_${tse.taskAssignment?.chargeCode.id}"],
				params["day2_${tse.taskAssignment?.chargeCode.id}"],
				params["day3_${tse.taskAssignment?.chargeCode.id}"],
				params["day4_${tse.taskAssignment?.chargeCode.id}"],
				params["day5_${tse.taskAssignment?.chargeCode.id}"],
				params["day6_${tse.taskAssignment?.chargeCode.id}"],
				params["day7_${tse.taskAssignment?.chargeCode.id}"]
			]
			
			tse.workdays.eachWithIndex { workday, indx ->
				
				if (daysOfWeek[indx] == "") {
					workday.hoursWorked = 0
				} else {
					try {
						workday.hoursWorked = daysOfWeek[indx].toDouble()
					} catch (Exception e) {
						workday.hoursWorked = 0
					}
				}
				
			} // clean up bad numbers.
		}
		
		if (!timesheetInstance.hasErrors() && timesheetInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), timesheetInstance.id])}"
			redirect(action: "show", id: timesheetInstance.id)
		}
		else {
			render(view: "edit", model: [timesheetInstance: timesheetInstance])
		}
		
	}
}
