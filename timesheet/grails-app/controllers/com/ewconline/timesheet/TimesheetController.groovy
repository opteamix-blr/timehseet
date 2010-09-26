package com.ewconline.timesheet


import java.util.TimeZone;

import hirondelle.date4j.DateTime;

import com.ewconline.timesheet.Timesheet 

class TimesheetController {
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
		DateTime currentDay = DateTime.today(TimeZone.getDefault())
		DateTime sunday = currentDay.minusDays(currentDay.getWeekDay() - 1)
		DateTime saturday = sunday.plusDays(6)
		Timesheet ts = new Timesheet(
			startDate:new Date(sunday.format("MM/DD/YYYY")), 
			endDate:new Date(saturday.format("MM/DD/YYYY"))
		)
		def timesheetEntry
		
		def user = User.get(session.user.id)
		def taskAssignments = user.taskAssignments
		for (ta in taskAssignments){
			timesheetEntry = new TimesheetEntry(taskAssignment:ta);
			for (x in (0..6)){
				timesheetEntry.addToWorkdays(new Workday(dateWorked:sunday.plusDays (x)))
			}
			ts.addToTimesheetEntries(timesheetEntry)
		}
		
		[timesheet:ts]
	}
}
