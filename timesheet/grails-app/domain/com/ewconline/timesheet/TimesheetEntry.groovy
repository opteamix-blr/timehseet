package com.ewconline.timesheet

/** This represents a row on a time sheet.
*/
class TimesheetEntry {
    LaborCategory laborCategory
	ChargeCode chargeCode

    static constraints = {
    }
    static hasMany = [workdays : Workday]
	static belongsTo = [timesheet : Timesheet]
    
}
