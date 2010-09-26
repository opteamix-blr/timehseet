package com.ewconline.timesheet

/** This represents a row on a time sheet.
*/
class TimesheetEntry {
	static belongsTo = [timesheet:Timesheet]
	
	TaskAssignment taskAssignment

    static constraints = {
		taskAssignment()
    }
    static hasMany = [workdays : Workday]

    
}
