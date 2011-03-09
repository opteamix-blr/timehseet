package com.ewconline.timesheet

class Workday extends AuditableObject implements Comparable {
	
	Date dateWorked
	Float hoursWorked
	
	static hasMany = [notes: Note]
	
	static belongsTo = [timesheetEntry:TimesheetEntry]

        String toString(){
            dateWorked.toString()
        }

    static constraints = {
		dateWorked()
		hoursWorked(nullable: true)
		notes(nullable: true)
    }
	int compareTo(obj) {
		dateWorked.compareTo(obj.dateWorked)
	}
}
