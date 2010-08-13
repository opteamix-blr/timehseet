package com.ewconline.timesheet.domain

/** This represents individual cells in a Timesheet Entry.
 */
class WorkDay {
    Date calendarDate
    float hoursWorked
    static constraints = {
    }
    static belongsTo = [timesheetEntry: TimesheetEntry]
}
