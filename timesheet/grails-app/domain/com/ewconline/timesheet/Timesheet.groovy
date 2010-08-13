package com.ewconline.timesheet

class Timesheet {

    Date dateCreated
    Date startDate
    Date endDate

    static constraints = {
        startDate(blank:false)
        endDate(blank:false)
    }
    static hasMany = [entries: TimesheetEntry]

    //static belongsTo = [user:User]
}
