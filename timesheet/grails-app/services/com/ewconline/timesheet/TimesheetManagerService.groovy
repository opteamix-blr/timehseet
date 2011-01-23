package com.ewconline.timesheet

import hirondelle.date4j.DateTime 
import java.util.TimeZone
class TimesheetManagerService {
	
	static transactional = true
   /* 
	* NOT_STARTED - saving - OPEN_SAVED
	* OPEN_SAVED - signing - SIGNED
	* OPEN_SAVED - saving - OPEN_SAVED
	* SIGNED - approving - APPROVED
	* SIGNED - disapproving - OPEN_NOT_SAVED
	* APPROVED - disapproving - OPEN_NOT_SAVED
	* OPEN_NOT_SAVED - saving - OPEN_SAVED
	*/

	// States
	static NOT_STARTED = "NOT_STARTED"
	static OPEN_SAVED = "OPEN_SAVED"
	static SIGNED = "SIGNED"
	static APPROVED = "APPROVED"
	static OPEN_NOT_SAVED = "OPEN_NOT_SAVED"

	// transitions
	static saving = "saving"
	static signing = "signing"
	static approving = "approving"
	static disapproving = "disapproving"

	static STATE_MAP = [(NOT_STARTED+saving) : OPEN_SAVED,
						(OPEN_SAVED+signing): SIGNED,
						(OPEN_SAVED+saving): OPEN_SAVED,
						(SIGNED+approving): APPROVED,
						(SIGNED+disapproving): OPEN_NOT_SAVED,
						(APPROVED+disapproving): OPEN_NOT_SAVED,
						(OPEN_NOT_SAVED+saving): OPEN_SAVED
	]
	
	/**
	 * Generates or builds an instance of a Timesheet object. This is normally for 
	 * create screens. 
	 * @return Timesheet - containing the timesheet entries (not persisted).
	 */
    def generateWeeklyTimesheet(User user) {
		
		DateTime currentDay = DateTime.today(TimeZone.getDefault())
		DateTime saturday = currentDay.minusDays(currentDay.getWeekDay()).getStartOfDay()
		DateTime friday = saturday.plusDays(6).getEndOfDay() 

		Timesheet ts = new Timesheet(
			startDate:new Date(saturday.getMilliseconds(TimeZone.getDefault())), 
			endDate:new Date(friday.getMilliseconds(TimeZone.getDefault())),
			user: user,
			currentState: NOT_STARTED
		)
		
		
		def taskAssignments = []
		user.taskAssignments.each { ta ->
			if (ta.enabled) {
				taskAssignments.add ta
			}
		}
		for (ta in taskAssignments){
			def timesheetEntry = new TimesheetEntry(taskAssignment:ta);
			for (x in (0..6)){
				timesheetEntry.addToWorkdays(new Workday(dateWorked:new Date(saturday.plusDays(x).getEndOfDay().getMilliseconds(TimeZone.getDefault()))))
			}
			ts.addToTimesheetEntries(timesheetEntry)
		}
		return ts
    }
	
	def retrieveCurrentTimesheet(User user) {
		def systemUser = User.get(user.id)
		def c = Timesheet.createCriteria()
		DateTime currentDay = DateTime.today(TimeZone.getDefault())
		int dayNum = currentDay.getWeekDay() % 7 + 1
		
		DateTime saturday = currentDay.minusDays(dayNum - 1).getStartOfDay()
		DateTime friday = saturday.plusDays(6).getEndOfDay()
		
		def previousTimesheet = c.list {
			eq("user.id", systemUser.id)
			ge("startDate", new Date(saturday.getMilliseconds(TimeZone.getDefault())))
			le("endDate", new Date(friday.getMilliseconds(TimeZone.getDefault())))
		}
		if (previousTimesheet && previousTimesheet.size() > 0) {
			return previousTimesheet[0]
		} else {
			return null;
		}
	}
	
	def createWeeklyTimesheet(Timesheet timesheet) {

		def ts = retrieveCurrentTimesheet(timesheet.user)
		if (ts != null) {
			throw new RuntimeException("The current or weekly timesheet was already created.")
		}
		updateState(timesheet, saving)
		timesheet.lastUpdated = new Date()
		return timesheet.save(flush:true)
	}
	
	def update(Timesheet timesheet) {
		updateState(timesheet, saving)
		timesheet.lastUpdated = new Date()
		return timesheet.save(flush:true)
	}
	def sign(Timesheet timesheet) {
		updateState(timesheet, signing)
		timesheet.lastUpdated = new Date()
		return timesheet.save(flush:true)
	}
	
	def approve(Timesheet timesheet) {
		updateState(timesheet, approving)
		timesheet.lastUpdated = new Date()
		return timesheet.save(flush:true)
	}
	
	def disapprove(Timesheet timesheet) {
		updateState(timesheet, disapproving)
		timesheet.lastUpdated = new Date()
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
	
	
	def validateState(int tsId, String transition){
		Timesheet timesheetInstance = Timesheet.get(tsId)
		//println("====>state " + timesheetInstance.currentState)
		//println("====>trans " + transition)
		
		def resultantState = STATE_MAP[(timesheetInstance.currentState+transition)]
		//println("====>result " + resultantState)
		// if null is returned its an illegal state !!!
		// example the user tried to save after it was approved.
		if (!resultantState) {
			throw new RuntimeException("${transition} timesheet is not allowed. Current state of the timesheet is ${timesheetInstance.currentState}.")
		}
	}
	
	def updateState(Timesheet ts, String transition) {
		
		def resultantState = STATE_MAP[(ts.currentState+transition)]
		//TODO if null is returned its an illegal state !!! 
		// example the user tried to save after it was approved.
		if (!resultantState) {
			throw new RuntimeException("${transition} timesheet is not allowed. Current state of the timesheet is ${ts.currentState}.")
		}
		ts.currentState = resultantState
		
		return resultantState
   /* 
	* NOT_STARTED - saving - OPEN_SAVED
	* OPEN_SAVED - signing - SIGNED
	* OPEN_SAVED - saving - OPEN_SAVED
	* SIGNED - approving - APPROVED
	* SIGNED - disapproving - OPEN_NOT_SAVED
	* APPROVED - disapproving - OPEN_NOT_SAVED
	* OPEN_NOT_SAVED - saving - OPEN_SAVED
	*/
		
	}
	
	def deepCopyTimesheet(Timesheet ts) {
		
		Timesheet copyTs = new Timesheet(
			id:ts.id,
			startDate:ts.startDate, 
			endDate:ts.endDate,
			user: ts.user,
			currentState: ts.currentState
		)
		
		
		for (te in ts.getTimesheetEntries()){
			def timesheetEntry = new TimesheetEntry(id:te.id, taskAssignment:te.taskAssignment);
			for (wd in te.workdays){
				timesheetEntry.addToWorkdays(new Workday(id:wd.id, dateWorked:wd.dateWorked, hoursWorked:wd.hoursWorked))
			}
			timesheetEntry.timesheet = copyTs
			copyTs.addToTimesheetEntries(timesheetEntry)
		}
		return copyTs
    }
	

}

