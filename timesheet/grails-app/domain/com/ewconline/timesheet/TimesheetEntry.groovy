package com.ewconline.timesheet

/** This represents a row on a time sheet.
*/
class TimesheetEntry implements Comparable{
	static belongsTo = [timesheet:Timesheet]
	SortedSet workdays
	TaskAssignment taskAssignment

    static constraints = {
		taskAssignment()
    }
    static hasMany = [workdays : Workday,
					  notes: Note]
	
	int compareTo(obj) {
		taskAssignment?.task?.name.compareTo(obj.taskAssignment?.task?.name)
	}
    
}
