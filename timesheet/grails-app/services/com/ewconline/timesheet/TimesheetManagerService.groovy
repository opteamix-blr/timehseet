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
		DateTime saturday = currentDay.minusDays(currentDay.getWeekDay() - 2)
		DateTime friday = saturday.plusDays(5)

		Timesheet ts = new Timesheet(
			startDate:new Date(saturday.format("MM/DD/YYYY")), 
			endDate:new Date(friday.format("MM/DD/YYYY"))
		)
		
		
		def taskAssignments = user.taskAssignments
		for (ta in taskAssignments){
			def timesheetEntry = new TimesheetEntry(taskAssignment:ta);
			for (x in (0..6)){
				timesheetEntry.addToWorkdays(new Workday(dateWorked:new Date(saturday.plusDays(x).getMilliseconds(TimeZone.getDefault()))))
			}
			ts.addToTimesheetEntries(timesheetEntry)
		}
		return ts
    }
	
	def retrieveCurrentTimesheet(User user) {
		
		def c = Timesheet.createCriteria()
		Date currentDay = new Date()
		def previousTimesheet = c.list {
			eq("user.id", user.id)
			lt("startDate", currentDay)
			gt("endDate", currentDay)
		}
		if (previousTimesheet.size() > 0) {
			return previousTimesheet[0]
		}
	}
	
	def createWeeklyTimesheet(Timesheet timesheet) {

		def ts = retrieveCurrentTimesheet(timesheet.user)
		if (ts != null) {
			throw new RuntimeException("The current or weekly timesheet was already created.")
		}

		return timesheet.save(flush:true)
	}
	
	def retrieveTimesheets(User user) {
		def c = Timesheet.createCriteria()
		def allUserTimesheets = c.list {
			eq("user.id", user.id)
			order("startDate", "desc")
	    }
		return allUserTimesheets
	}

}

