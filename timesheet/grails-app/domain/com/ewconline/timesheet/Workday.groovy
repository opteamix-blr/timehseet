package com.ewconline.timesheet

class Workday  implements Comparable {
	
	Date dateWorked
	double hoursWorked
	
	static hasMany = [notes: Note]
	
	static belongsTo = [timesheetEntry:TimesheetEntry]
	
    static constraints = {
		dateWorked()
		hoursWorked()
		notes(nullable: true)
    }
	int compareTo(obj) {
		dateWorked.compareTo(obj.dateWorked)
	}
}
