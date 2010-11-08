package com.ewconline.timesheet

import hirondelle.date4j.DateTime 
import java.util.TimeZone
class TimesheetManagerService {

    static transactional = true
	/* newtimesheet - saving - saved
	* saved - signing - signed
	* saved - modify - changed
	* saved - saving - saved
	* changed - cancel - saved
	* signed - disapprove - saved
	* signed - modify - changed
	* signed - approve - approved
	* approved - make ready - pending
	* approved - override - saved
	* pending - override - approved
	* pending - finalize - completed
	*/
	// States
	static NEWTIMESHEET = "NEWTIMESHEET"
	static SAVED = "SAVED"
	static CHANGED = "CHANGED"
	static SIGNED = "SIGNED"
	static APPROVED = "APPROVED"
	static PENDING = "PENDING" 
	static COMPLETED = "COMPLETED"
	
	// transitions
	static saving = "saving"
	static signing = "signing"
	static modify = "modify"
	static cancel = "cancel"
	static disapprove = "disapprove"
	static approve = "approve"
	static makeReady = "makeready"
	static override = "override"
	static finalize = "finalize"

	static STATE_MAP = [(NEWTIMESHEET+saving) :SAVED,
						(SAVED+signing): SIGNED,
						(SAVED+modify): CHANGED,
						(SAVED+saving): SAVED,
						(CHANGED+cancel): SAVED,
						(SIGNED+disapprove): SAVED,
						(SIGNED+modify): CHANGED,
						(SIGNED+approve): APPROVED,
						(APPROVED+makeReady): PENDING,
						(APPROVED+override): SAVED,
						(PENDING+override): APPROVED,
						(PENDING+finalize): COMPLETED
	]
	
	/**
	 * Generates or builds an instance of a Timesheet object. This is normally for 
	 * create screens. 
	 * @return Timesheet - containing the timesheet entries (not persisted).
	 */
    def generateWeeklyTimesheet(User user) {
		
		DateTime currentDay = DateTime.today(TimeZone.getDefault())
		DateTime saturday = currentDay.minusDays(currentDay.getWeekDay())
		DateTime friday = saturday.plusDays(6)

		Timesheet ts = new Timesheet(
			startDate:new Date(saturday.format("MM/DD/YYYY")), 
			endDate:new Date(friday.format("MM/DD/YYYY")),
			user: user,
			currentState: NEWTIMESHEET
		)
		
		
		def taskAssignments = user.taskAssignments
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
		Date currentDay = new Date()
		def previousTimesheet = c.list {
			eq("user.id", systemUser.id)
			lt("startDate", currentDay)
			gt("endDate", currentDay)
		}
		if (previousTimesheet && previousTimesheet.size() > 0) {
			return previousTimesheet[0]
		}
	}
	
	def createWeeklyTimesheet(Timesheet timesheet) {

		def ts = retrieveCurrentTimesheet(timesheet.user)
		if (ts != null) {
			throw new RuntimeException("The current or weekly timesheet was already created.")
		}
		updateState(timesheet, saving)
			

		return timesheet.save(flush:true)
	}
	
	def update(Timesheet timesheet) {
		
		updateState(timesheet, saving)
		
		return timesheet.save(flush:true)
	}
	def sign(Timesheet timesheet) {
		
		updateState(timesheet, signing)
		
		return timesheet.save(flush:true)
	}
	
	def approve(Timesheet timesheet) {
		
		updateState(timesheet, approve)
		
		return timesheet.save(flush:true)
	}
	
	def disapprove(Timesheet timesheet) {
		
		updateState(timesheet, disapprove)
		
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
	
	def updateState(Timesheet ts, String transition) {
		
		def resultantState = STATE_MAP[(ts.currentState+transition)]
		//TODO if null is returned its an illegal state !!! 
		// example the user tried to save after it was approved.
		if (!resultantState) {
			throw new RuntimeException("Current state of the timesheet is ${ts.currentState} you were ${transition} timesheet.")
		}
		ts.currentState = resultantState
		
		return resultantState
		/* newtimesheet - saving - saved
		* saved - signing - signed
		* saved - modified - changed
		* saved - saving - saved
		* changed - cancel - saved
		* signed - disapprove - saved
		* signed - modified - changed
		* signed - approve - approved
		* approved - make ready - pending
		* approved - override - saved
		* pending - override - approved
		* pending - finalize - completed
		*/
		
	}

}

