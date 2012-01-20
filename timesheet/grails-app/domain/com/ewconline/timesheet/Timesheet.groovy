
package com.ewconline.timesheet

import java.util.SortedSet;

class Timesheet extends AuditableObject{

    Date dateCreated
    Date lastUpdated

    Date startDate
    Date endDate

    String toString(){
        return startDate.toString()
    }

    /* State - transition - State
     * NOT_STARTED - saving - OPEN_SAVED
     * OPEN_SAVED - signing - SIGNED
     * OPEN_SAVED - saving - OPEN_SAVED
     * SIGNED - approving - APPROVED
     * SIGNED - disapproving - OPEN_SAVED
     * APPROVED - disapproving - OPEN_SAVED
     */
    String currentState
    Long signature

    static constraints = {
        startDate(blank:false)
        endDate(blank:false)
        currentState(blank:false)
        signature(nullable:true)
    }

    static hasMany = [timesheetEntries: TimesheetEntry,
        notes: Note]

    static belongsTo = [user:User]

    def getSortedTimesheetEntries(){
        if (timesheetEntries) {
            return [timesheetEntries].flatten().sort{ self, oth -> self?.taskAssignment?.task?.id <=> oth?.taskAssignment?.task?.id}
        }
        return [];
    }

    /**
     * 1= saturday
     * 2= sunday
     * 3= monday
     * .
     * 7= friday
     * @param dayNum
     * @return
     */
    def sumHoursByDay(int dayNum) {
        float total=0.0
        timesheetEntries.each { we ->
            if (we.workdays.size() >= dayNum) {
                if (we?.workdays[dayNum-1]) {
                    if (we?.workdays[dayNum-1].hoursWorked) {
                        total = total + we.workdays[dayNum-1]?.hoursWorked
                    }
                }
            }
        }
        total.round(2)
    }
    def sumAllHours() {
        float total=0.0
        timesheetEntries.each { te ->
            te.workdays.each { wd ->
                if(wd?.hoursWorked) {
                    total = total + wd?.hoursWorked
                }
            }
        }
        total.round(2)
    }
	
    def hasModifications() {
        boolean hasNotes = false
        timesheetEntries.each { te ->
            te.workdays.each { wd ->
                if(wd?.notes.size() > 0) {
                    hasNotes = true
                }
            }
        }
        return hasNotes
    }
}
