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
		def timesheetList = Timesheet.list()
		[timesheetList:timesheetList]
	}
	
	def create = {
		// TODO Validate is user allowed to create a timesheet?
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

		[timesheet:ts]
	}
}
