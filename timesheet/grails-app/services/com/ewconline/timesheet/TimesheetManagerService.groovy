package com.ewconline.timesheet

import hirondelle.date4j.DateTime 
import java.util.TimeZone
import java.util.*

class TimesheetManagerService {
    def etimeSecurityService
    static transactional = true
    /*
     * NOT_STARTED - saving - OPEN_SAVED
     * OPEN_SAVED - signing - SIGNED
     * OPEN_SAVED - saving - OPEN_SAVED
     * SIGNED - approving - APPROVED
     * SIGNED - disapproving - OPEN_NOT_SAVED
     * APPROVED - disapproving - OPEN_NOT_SAVED
     * OPEN_NOT_SAVED - saving - OPEN_SAVED
     */

    // States
    static NOT_STARTED = "NOT_STARTED"
    static OPEN_SAVED = "OPEN_SAVED"
    static SIGNED = "SIGNED"
    static APPROVED = "APPROVED"
    static OPEN_NOT_SAVED = "OPEN_NOT_SAVED"

    // transitions
    static saving = "saving"
    static signing = "signing"
    static approving = "approving"
    static disapproving = "disapproving"

    static STATE_MAP = [(NOT_STARTED+saving) : OPEN_SAVED,
        (OPEN_SAVED+signing): SIGNED,
        (OPEN_SAVED+saving): OPEN_SAVED,
        (SIGNED+approving): APPROVED,
        (SIGNED+disapproving): OPEN_NOT_SAVED,
        (APPROVED+disapproving): OPEN_NOT_SAVED,
        (OPEN_NOT_SAVED+saving): OPEN_SAVED
    ]

    /**dateOnWeek is a string with format of yyyy-MM-dd
     */
    def generatePastTimesheet(user, dateOnWeek, taskAssignmentIds) {

        DateTime targetDay = null
        if (dateOnWeek && dateOnWeek.length() > 0 && (taskAssignmentIds && taskAssignmentIds.size() >0 )) {
            targetDay = new DateTime(dateOnWeek)
            DateTime saturday = null
            if (targetDay.getWeekDay() == 7) {
                saturday = targetDay.getStartOfDay()
            }else {
                saturday = targetDay.minusDays(targetDay.getWeekDay()).getStartOfDay()
            }

            DateTime friday = saturday.plusDays(6).getEndOfDay()

            Timesheet ts = new Timesheet(
                startDate:new Date(saturday.getMilliseconds(TimeZone.getDefault())),
                endDate:new Date(friday.getMilliseconds(TimeZone.getDefault())),
                user: user,
                currentState: NOT_STARTED
            )

            // add task assignments where the ids are selected
            def taskAssignments = []
            user.taskAssignments.each { ta ->
                taskAssignmentIds.each { taId ->   
                    if (taId == "${ta.id}") {
                       taskAssignments.add ta
                    }
                }
            }
            
            for (ta in taskAssignments){
                def timesheetEntry = new TimesheetEntry(taskAssignment:ta, currentState: NOT_STARTED);
                for (x in (0..6)){
                    timesheetEntry.addToWorkdays(new Workday(dateWorked:new Date(saturday.plusDays(x).getEndOfDay().getMilliseconds(TimeZone.getDefault()))))
                }
                ts.addToTimesheetEntries(timesheetEntry)
            }
            return ts
        }

        
    }
    /**dateOnWeek is a string with format of yyyy-MM-dd
     */
    def findTimesheet(user, dateOnWeek) {
        def systemUser = User.get(user.id)
        def c = Timesheet.createCriteria()
        DateTime targetDay = null
        DateTime saturday = null
        if (dateOnWeek && dateOnWeek.length() > 0) {
            targetDay = new DateTime(dateOnWeek)
            if (targetDay.getWeekDay() == 7) {
                saturday = targetDay.getStartOfDay()
            } else {
                saturday = targetDay.minusDays(targetDay.getWeekDay()).getStartOfDay()
            }
            DateTime friday = saturday.plusDays(6).getEndOfDay()

            def previousTimesheet = c.list {
                eq("user.id", systemUser.id)
                ge("startDate", new Date(saturday.getMilliseconds(TimeZone.getDefault())))
                le("endDate", new Date(friday.getMilliseconds(TimeZone.getDefault())))
            }
            if (previousTimesheet && previousTimesheet.size() > 0) {
                return previousTimesheet[0]
            } else {
                return null;
            }
        }   
    }
    /**
     * Generates or builds an instance of a Timesheet object. This is normally for
     * create screens.
     * @return Timesheet - containing the timesheet entries (not persisted).
     */
    def generateWeeklyTimesheet(User user) {
	
        Timesheet ts = null
        ts = retrieveCurrentTimesheet(user)
        if (ts != null) {
            throw new RuntimeException("The current or weekly timesheet was already created.")
        }
	
        DateTime currentDay = DateTime.today(TimeZone.getDefault())
        DateTime saturday = null

        if (currentDay.getWeekDay() == 7) {
            saturday = currentDay.getStartOfDay()
        }else {
            saturday = currentDay.minusDays(currentDay.getWeekDay()).getStartOfDay()
        }

        DateTime friday = saturday.plusDays(6).getEndOfDay()

        ts = new Timesheet(
            startDate:new Date(saturday.getMilliseconds(TimeZone.getDefault())),
            endDate:new Date(friday.getMilliseconds(TimeZone.getDefault())),
            user: user,
            currentState: NOT_STARTED
        )
		
		
        def taskAssignments = []
        user.taskAssignments.each { ta ->
            if (ta.enabled) {
                taskAssignments.add ta
            }
        }
        for (ta in taskAssignments){
            def timesheetEntry = new TimesheetEntry(taskAssignment:ta, currentState: NOT_STARTED);
            for (x in (0..6)){
                timesheetEntry.addToWorkdays(new Workday(dateWorked:new Date(saturday.plusDays(x).getEndOfDay().getMilliseconds(TimeZone.getDefault()))))
            }
            ts.addToTimesheetEntries(timesheetEntry)
        }
        return ts
    }
	
    def retrieveCurrentTimesheet(User user) {
        def systemUser = User.get(user.id)
        def c = Timesheet.createCriteria()
        DateTime currentDay = DateTime.today(TimeZone.getDefault())
        int dayNum = currentDay.getWeekDay() % 7 + 1
		
        DateTime saturday = currentDay.minusDays(dayNum - 1).getStartOfDay()
        DateTime friday = saturday.plusDays(6).getEndOfDay()
		
        def previousTimesheet = c.list {
            eq("user.id", systemUser.id)
            ge("startDate", new Date(saturday.getMilliseconds(TimeZone.getDefault())))
            le("endDate", new Date(friday.getMilliseconds(TimeZone.getDefault())))
        }
        if (previousTimesheet && previousTimesheet.size() > 0) {
            return previousTimesheet[0]
        } else {
            return null;
        }
    }

    def validateTimesheetEntries(timesheet, user){
        DateTime currentDay = DateTime.today(TimeZone.getDefault())
        int dayNum = currentDay.getWeekDay() % 7 + 1
        DateTime saturday = currentDay.minusDays(dayNum - 1).getStartOfDay()
        DateTime friday = saturday.plusDays(6).getEndOfDay()

        def activeTaskAssignments = user.taskAssignments.findAll {
            it.enabled == true
        }
        
        def existingTaskAssignments = timesheet.timesheetEntries.collect {
            it.taskAssignment
        }

        def taskAssignmentsToAdd = activeTaskAssignments - existingTaskAssignments

        taskAssignmentsToAdd.each {ta ->
            def timesheetEntry = new TimesheetEntry(taskAssignment:ta, currentState: NOT_STARTED);
            for (x in (0..6)){
                timesheetEntry.addToWorkdays(new Workday(dateWorked:new Date(saturday.plusDays(x).getEndOfDay().getMilliseconds(TimeZone.getDefault()))))
            }
            timesheet.addToTimesheetEntries(timesheetEntry)
        }
        timesheet.validate()
        if(timesheet.hasErrors()){
            timesheet.errors.each{
                log.info it
            }
        }
        timesheet.save()
        return timesheet
    }

    def createWeeklyTimesheet(Timesheet timesheet) {
        updateState(timesheet, saving)
        timesheet.lastUpdated = new Date()
        return timesheet.save(flush:true)
    }
	
    def update(Timesheet timesheet) {
        updateState(timesheet, saving)
        timesheet.lastUpdated = new Date()
        return timesheet.save(flush:true)
    }
    def sign(Timesheet timesheet) {
        updateState(timesheet, signing)
        timesheet.lastUpdated = new Date()
        return timesheet.save(flush:true)
    }
	
    def approve(Timesheet timesheet, user, isAccountantRole, isApproverRole) {
        // validate against user
        if (!isAccountantRole && !isApproverRole) {
            throw new RuntimeException("${user.userRealName} is not allowed to approve timesheets.${isAccountantRole} ${isApproverRole}")
        }
        int numApproved = 0
        timesheet.timesheetEntries.each {
            te ->

            if (isAccountantRole) {
                te.currentState = APPROVED
                numApproved++
            } else {
                for ( taa in te.taskAssignment.taskAssignmentApprovals ) {
                   if (taa.user.id == user.id && isApproverRole) {
                        te.currentState = APPROVED
                        numApproved++
                        break
                    }
                }
            }
        }
        
        if (timesheet.timesheetEntries.size() == numApproved) {
            updateState(timesheet, approving)
        }
        
        timesheet.lastUpdated = new Date()
        return timesheet.save(flush:true)
    }
	
    def disapprove(Timesheet timesheet, user, isAccountantRole, isApproverRole) {
        if (!isAccountantRole && !isApproverRole) {
            throw new RuntimeException("${user.userRealName} is not allowed to disapprove timesheets.${isAccountantRole} ${isApproverRole}")
        }
        boolean disapproveIt = false

        // if accountant go disaprove
        if (isAccountantRole) {
            disapproveIt = true
        } else {
            // check if user is one of the approvers
            for ( te in timesheet.timesheetEntries) {
                for ( taa in te.taskAssignment.taskAssignmentApprovals ) {
                   if (taa.user.id == user.id && isApproverRole) {
                        disapproveIt = true
                        break
                    }
                }
                if (disapproveIt) {
                    break
                }
            }
        }

        // do it
        if (disapproveIt) {
            updateState(timesheet, disapproving)
            timesheet.timesheetEntries.each {
                te ->
                te.currentState = timesheet.currentState
            }
            timesheet.lastUpdated = new Date()
            return timesheet.save(flush:true)
        }
    }
	
    def retrieveTimesheets(User user) {
        def c = Timesheet.createCriteria()
        def allUserTimesheets = c.list {
            eq("user.id", user.id)
            order("startDate", "desc")
        }
        return allUserTimesheets
    }
	
	
    def validateState(int tsId, String transition){
        Timesheet timesheetInstance = Timesheet.get(tsId)
        //println("====>state " + timesheetInstance.currentState)
        //println("====>trans " + transition)
		
        def resultantState = STATE_MAP[(timesheetInstance.currentState+transition)]
        //println("====>result " + resultantState)
        // if null is returned its an illegal state !!!
        // example the user tried to save after it was approved.
        if (!resultantState) {
            throw new RuntimeException("${transition} timesheet is not allowed. Current state of the timesheet is ${timesheetInstance.currentState}.")
        }
    }
	
    def updateState(Timesheet ts, String transition) {
		
        def resultantState = STATE_MAP[(ts.currentState+transition)]
        //TODO if null is returned its an illegal state !!!
        // example the user tried to save after it was approved.
        if (!resultantState) {
            throw new RuntimeException("${transition} timesheet is not allowed. Current state of the timesheet is ${ts.currentState}.")
        }
        ts.currentState = resultantState
		
        return resultantState
        /*
         * NOT_STARTED - saving - OPEN_SAVED
         * OPEN_SAVED - signing - SIGNED
         * OPEN_SAVED - saving - OPEN_SAVED
         * SIGNED - approving - APPROVED
         * SIGNED - disapproving - OPEN_NOT_SAVED
         * APPROVED - disapproving - OPEN_NOT_SAVED
         * OPEN_NOT_SAVED - saving - OPEN_SAVED
         */
		
    }
	
    def deepCopyTimesheet(tsId) {
        Timesheet ts = Timesheet.get(tsId)
        Timesheet copyTs = new Timesheet(
            id:tsId,
            startDate:ts.startDate,
            endDate:ts.endDate,
            user: ts.user,
            currentState: ts.currentState
        )
		
		
        for (te in ts.getTimesheetEntries()){
            def timesheetEntry = new TimesheetEntry(id:te.id, taskAssignment:te.taskAssignment);
            for (wd in te.workdays){
                timesheetEntry.addToWorkdays(new Workday(id:wd.id, dateWorked:wd.dateWorked, hoursWorked:wd.hoursWorked))
            }
            timesheetEntry.timesheet = copyTs
            copyTs.addToTimesheetEntries(timesheetEntry)
        }
        return copyTs
    }
	

}

