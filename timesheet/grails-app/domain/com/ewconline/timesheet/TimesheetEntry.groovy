package com.ewconline.timesheet

/** This represents a row on a time sheet.
*/
class TimesheetEntry {
	static belongsTo = [timesheet:Timesheet]
	
	LaborCategory laborCategory
	ChargeCode chargeCode

    static constraints = {
		laborCategory()
		chargeCode()
    }
    //static hasMany = [workdays : Workday]

    
}
