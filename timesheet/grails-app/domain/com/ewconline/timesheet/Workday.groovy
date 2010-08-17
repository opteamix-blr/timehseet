package com.ewconline.timesheet

class Workday {
	
	Date dateWorked
	double hoursWorked
	
	static belongsTo = TimesheetEntry
	
    static constraints = {
    }
}
