
package com.ewconline.timesheet

class Timesheet {

    Date dateCreated
	Date lastUpdated

    Date startDate
    Date endDate

    static constraints = {
        startDate(blank:false)
        endDate(blank:false)
    }
    static hasMany = [timesheetEntries: TimesheetEntry]

    static belongsTo = [user:User]
}
