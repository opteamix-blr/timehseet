package com.ewconline.timesheet.domain

/** This represents a row on a time sheet.
*/
class TimesheetEntry {
    Task task
    LaborCategory laborCategory
    ChargeCode chargeCode

    static constraints = {
    }
    
    static hasMany = [workDays:WorkDay]

    
}
