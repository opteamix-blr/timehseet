
package com.ewconline.timesheet

import java.util.SortedSet;

class Timesheet {

    Date dateCreated
	Date lastUpdated

    Date startDate
    Date endDate
	
   /* State - transition - State
	* NOT_STARTED - saving - OPEN_SAVED
	* OPEN_SAVED - signing - SIGNED
	* OPEN_SAVED - saving - OPEN_SAVED
	* SIGNED - approving - APPROVED
	* SIGNED - disapproving - OPEN_NOT_SAVED
	* APPROVED - disapproving - OPEN_NOT_SAVED
	* OPEN_NOT_SAVED - saving - OPEN_SAVED
	*/
	String currentState
	Long signature

    static constraints = {
        startDate(blank:false)
        endDate(blank:false)
		currentState(blank:false)
		signature(nullable:true)
    }
	SortedSet timesheetEntries
    static hasMany = [timesheetEntries: TimesheetEntry,
					  notes: Note]

    static belongsTo = [user:User]
}
