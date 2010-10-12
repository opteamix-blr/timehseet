
package com.ewconline.timesheet

import java.util.SortedSet;

class Timesheet {

    Date dateCreated
	Date lastUpdated

    Date startDate
    Date endDate
	/* open, pending, approved */
	String approveState = "open"

    static constraints = {
        startDate(blank:false)
        endDate(blank:false)
    }
	SortedSet timesheetEntries
    static hasMany = [timesheetEntries: TimesheetEntry]

    static belongsTo = [user:User]
}
