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
				timesheetEntry.addToWorkdays(new Workday(dateWorked:new Date(sunday.plusDays(x).getMilliseconds(TimeZone.getDefault()))))
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
		// TODO validate duplicate timesheet.
		Date currentDay = new Date()
		
		
		def c = Timesheet.createCriteria()
		def previousTimesheet = c.list {
			 eq("user.id", timesheet.user.id)
			 lt("startDate", currentDay)
			 gt("endDate", currentDay)
		}
		if (previousTimesheet.size() > 0) {
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
	
	Timesheet getDuplicateTimesheet(Timesheet tsToCheck, User user){
		Timesheet duplicateTimesheet = Timesheet.findByUserAndStartDate(user, tsToCheck.startDate)
		
		return duplicateTimesheet
	}
}

