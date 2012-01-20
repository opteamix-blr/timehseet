package com.ewconline.timesheet

/** This represents a row on a time sheet.
 */
class TimesheetEntry extends AuditableObject{
    static belongsTo = [timesheet:Timesheet]
    List workdays
    TaskAssignment taskAssignment

    /* State - transition - State
     * NOT_STARTED - saving - OPEN_SAVED
     * OPEN_SAVED - signing - SIGNED
     * OPEN_SAVED - saving - OPEN_SAVED
     * SIGNED - approving - APPROVED
     * SIGNED - disapproving - OPEN_SAVED
     * APPROVED - disapproving - OPEN_SAVED
     */
    String currentState

    String toString(){
        return taskAssignment.toString()
    }

    static constraints = {
        taskAssignment()
        currentState(blank:false)
    }
    static hasMany = [workdays : Workday,
        notes: Note]
	
    def sumHours() {
        float total=0.0
        workdays.each { wd ->
            if (wd.hoursWorked){
                total = total +  wd.hoursWorked
            }
        }
        total.round(2)
    }
}
