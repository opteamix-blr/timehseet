package com.ewconline.timesheet

import hirondelle.date4j.DateTime 

class TimesheetManagerService {

    static transactional = true

	/**
	 * Generates or builds an instance of a Timesheet object. This is normally for 
	 * create screens. 
	 * @return Timesheet - containing the timesheet entries (not persisted).
	 */
    def generateWeeklyTimesheet(User user) {
		
		DateTime currentDay = DateTime.today(TimeZone.getDefault())
		DateTime sunday = currentDay.minusDays(currentDay.getWeekDay() - 1)
		DateTime saturday = sunday.plusDays(6)

		Timesheet ts = new Timesheet(
			startDate:new Date(sunday.format("MM/DD/YYYY")), 
			endDate:new Date(saturday.format("MM/DD/YYYY"))
		)
		
		def taskAssignments = user.taskAssignments
		for (ta in taskAssignments){
			def timesheetEntry = new TimesheetEntry(taskAssignment:ta);
			for (x in (0..6)){
				timesheetEntry.addToWorkdays(new Workday(dateWorked:sunday.plusDays (x)))
			}
			ts.addToTimesheetEntries(timesheetEntry)
		}
		return ts
    }
}
