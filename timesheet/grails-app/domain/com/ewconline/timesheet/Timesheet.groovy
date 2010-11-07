
package com.ewconline.timesheet

import java.util.SortedSet;

class Timesheet {

    Date dateCreated
	Date lastUpdated

    Date startDate
    Date endDate
	
	/* STATES: new timesheet, saved, changed, signed, approved, pending, complete */
	/*
	 * newtimesheet - save - saved
	 * saved - sign - signed 
	 * saved - modified - changed
	 * changed - cancel - saved
	 * signed - disapprove - saved
	 * signed - modified - changed
	 * signed - approve - approved
	 * approved - make ready - pending
	 * approved - override - saved
	 * pending - override - approved
	 * pending - finalize - completed
	 * 
	 */
	String currentState

    static constraints = {
        startDate(blank:false)
        endDate(blank:false)
		currentState(blank:false)
    }
	SortedSet timesheetEntries
    static hasMany = [timesheetEntries: TimesheetEntry,
					  notes: Note]

    static belongsTo = [user:User]
}
