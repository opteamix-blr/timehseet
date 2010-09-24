package com.ewconline.timesheet

/** This represents a row on a time sheet.
*/
class TimesheetEntry {
	static belongsTo = [timesheet:Timesheet]
	
	Task task

    static constraints = {
		task()
    }
    static hasMany = [workdays : Workday]

    
}
