package com.ewconline.timesheet

class Workday {
	
	Date dateWorked
	double hoursWorked
	
	static belongsTo = [timesheetEntry:TimesheetEntry]
	
    static constraints = {
    }
}
