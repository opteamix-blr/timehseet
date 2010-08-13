package com.ewconline.timesheet

/** This represents a row on a time sheet.
*/
class TimesheetEntry {
    LaborCategory laborCategory
    ChargeCode chargeCode
    Date dateWorked
    double hoursWorked

    static constraints = {
        chargeCode(blank:false)
        laborCategory(blank:false)
        workDate(blank:false)
        hoursWorked(blank:false)
    }
    static belongsTo = [timesheet:Timesheet]

    
}
