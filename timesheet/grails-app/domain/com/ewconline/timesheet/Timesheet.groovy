package com.ewconline.timesheet.domain

class Timesheet {

    static constraints = {
    }
    static hasMany = [timesheetEntries: TimesheetEntry]
}
