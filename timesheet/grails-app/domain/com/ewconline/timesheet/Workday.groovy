package com.ewconline.timesheet

class Workday  implements Comparable {
	
	Date dateWorked
	double hoursWorked
	
	static belongsTo = [timesheetEntry:TimesheetEntry]
	
    static constraints = {
    }
	int compareTo(obj) {
		dateWorked.compareTo(obj.dateWorked)
	}
}
