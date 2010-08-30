package com.ewconline.timesheet

/** This represents a row on a time sheet.
*/
class TimesheetEntry {
	static belongsTo = [timesheet:Timesheet]
	
	LaborCategory laborCategory
	ChargeCode chargeCode
	Task task

    static constraints = {
		task()
		laborCategory()
		chargeCode()
    }
    static hasMany = [workdays : Workday]

    
}
