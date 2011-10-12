package com.ewconline.timesheet

import org.codehaus.groovy.grails.commons.*
import grails.util.Environment
import hirondelle.date4j.DateTime 
import java.util.TimeZone
import java.util.Date

import com.ewconline.timesheet.Timesheet 

class TimesheetController {
    def timesheetManagerService
    def signatureService
    def auditingService
    def ldapAuthenticationService
    def exportService
    def dataTransferService
    def scafold = true
    def index = { }

    def askPastTimesheetInfo = {
        def user = User.get(session.user.id)
	[userInstance:user]
    }

    def createPastTimesheet = {
        def user = User.get(session.user.id)
        def dateOnWeek = params["dateOnWeek"]
        def taskAssignmentIds = params["taskAssignmentIds"]
        def dateOnWeek2 = null
        if (dateOnWeek) {
            dateOnWeek2 = "${dateOnWeek?.format("yyyy-MM-dd")}"
        }

        Timesheet duplicateTimesheet = timesheetManagerService.findTimesheet(user, dateOnWeek2)
        if(duplicateTimesheet){
            // already created
            flash.message = "Timesheet already created going to edit mode."
            redirect(action:"edit", params:[id:duplicateTimesheet.id])
        } else {
            // obtain the newly created timesheet in the past
            Timesheet prevTimesheet = timesheetManagerService.generatePastTimesheet(user, dateOnWeek2, taskAssignmentIds)
            if (prevTimesheet) {
                // populate the create screen with the previous timesheet
                render(view: "create", model: [timesheetInstance:prevTimesheet, isCurrentWeek:false])
            } else {
                // error validation on required fields
                flash.message = "Date of week or at least one Task Assignment must be selected to creating a previous timesheet."
                render(view: "askPastTimesheetInfo", model: [userInstance:user])
            }
        }
     
    }
    def listTimesheets = {
                
        def user = User.get(session.user.id)
        auditingService.setCurrentUserName(user.username)
        def timesheetList = timesheetManagerService.retrieveTimesheets(user, params)

        if(params?.format && params.format != "html"){
            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=books.${params.extension}")

            def taskDto = dataTransferService.sevenDayReport(null)

            exportService.export(params.format, response.outputStream,taskDto, [:], [:]) }

        [timesheetList:timesheetList, timesheetInstanceTotal:params?.totCount?.toInteger()]
    }

    def create = {

        def user = User.get(session.user.id)
		

        Timesheet duplicateTimesheet = timesheetManagerService.retrieveCurrentTimesheet(user)
		
        if(duplicateTimesheet){
            redirect(action:"edit", params:[id:duplicateTimesheet.id])
        } else {
            Timesheet ts = timesheetManagerService.generateWeeklyTimesheet(user)
            [timesheetInstance:ts]
        }
    }
	
    def save = {
        def user = User.get(session.user.id)
        Timesheet timesheetInstance = null

        // detect if its this week is a current week type.
        params.each{
            log.info it
        }
        if (params["isCurrentWeek"] == 'null' || 
            params["isCurrentWeek"] == '' || 
            params["isCurrentWeek"] == null ||
            params["isCurrentWeek"] == "true") {
            timesheetInstance = timesheetManagerService.generateWeeklyTimesheet(user)
        } else {
            def taskAssignmentIds = params["taskAssignmentIds"]
            
            def dateOnWeek = params["dateOnWeek"]

            timesheetInstance = timesheetManagerService.generatePastTimesheet(user, dateOnWeek, taskAssignmentIds)
        }

        try {
            validateHoursPerDay(timesheetInstance)
        } catch (Exception e) {
            flash.message = "Invalid value. Must be a decimal from 0.0 to 24.0"
            populateWorkdaysFromForm(timesheetInstance)
            render(view: "create", model: [timesheetInstance: timesheetInstance])
            return
        }
		
		
        try {
			
            populateWorkdaysFromForm(timesheetInstance)
            // update state of the timesheet
            if (timesheetManagerService.createWeeklyTimesheet(timesheetInstance)){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), user.id])}"
                redirect(action: "listTimesheets", id: user.id)
            }
            else {
                render(view: "create", model: [timesheetInstance: timesheetInstance])
            }
        } catch (Exception e) {
            flash.message = e.getMessage()
            redirect(action: "listTimesheets", id: user.id)
        }
    }
	
    def show = {
        def timesheetInstance = Timesheet.get(params.id)
        if (!timesheetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'Timesheet'), params.id])}"
            redirect(action: "list")
        }
        else {
            [timesheetInstance: timesheetInstance]
        }
    }
	
    def edit = {

        def user = User.get(session.user.id)
        def ts = Timesheet.get(params.id)
        if (ts == null) {
            ts = timesheetManagerService.retrieveCurrentTimesheet(user)
            if (ts == null) {
                flash.message = "No current timesheet. Please create a new timesheet for the week."
                redirect(action: "listTimesheets")
                return
            }
        }
        if (ts.user.id != user.id) {
            throw new RuntimeException("Attempting to update a timesheet assigned to a different user.")
        }
        ts = timesheetManagerService.validateTimesheetEntries(ts, user)
        [timesheetInstance:ts]
    }
	
    def update = {
        def user = User.get(session.user.id)
        // original
        Timesheet timesheetInstance = Timesheet.get(params.id.toInteger())
        try {
            timesheetManagerService.validateState(params.id.toInteger(), timesheetManagerService.saving)
        }catch(Exception e) {
            //e.printStackTrace()
            flash.message = "Unable to save timesheet. The current state is " + timesheetInstance.currentState
            redirect(action: "show", id: params.id.toInteger())
            return
        }
		
		
		
        if (params.version) {
            def version = params.version.toLong()
            if (timesheetInstance.version > version) {
                timesheetInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'timesheet.label', default: 'Timesheet')] as Object[], "Another user has updated this Timesheet while you were editing")
                render(view: "edit", model: [timesheetInstance: timesheetInstance])
                return
            }
        }
        // validate from form
        try {
            validateHoursPerDay(timesheetInstance)
        }catch(Exception e) {
            flash.message = "Invalid value. Each day can only add up to 24 hours. Must be a decimal between 0.0 to 24.0"
            Timesheet timesheetCopy = timesheetManagerService.deepCopyTimesheet(timesheetInstance.id)

            populateWorkdaysFromForm(timesheetCopy)

            // for some reason this works... deep copy doesn't copy id
            // maybe populateWorkdaysFromForm() is messing with the id.
            // this works with the setting of the id here below:
            timesheetCopy.id = timesheetInstance.id
            render(view: "edit", model: [timesheetInstance: timesheetCopy])
            return
        }
		
        // forwards to edit page when prior days have been modified.
        def weekdaysModified = validateWorkDayChangedExceptToday(timesheetInstance)
        if (weekdaysModified.size()> 0 ) {
            flash.message = "Modified work hours on a prior date requires a reason."
            // update from form
            // copy
            Timesheet timesheetCopy = timesheetManagerService.deepCopyTimesheet(timesheetInstance.id)
			
            populateWorkdaysFromForm(timesheetCopy)
            timesheetCopy.id = timesheetInstance.id
            def previousHourValues = obtainPreviousValues(timesheetInstance)
            render(view: "modifyWithNotes", model: [timesheetInstance: timesheetCopy, weekdaysModified:weekdaysModified, previousHourValues:previousHourValues])
            return
        }

        try {
            if (!timesheetInstance.hasErrors()) {
                populateWorkdaysFromForm(timesheetInstance)
                timesheetManagerService.update(timesheetInstance)
                redirect(action: "show", id: timesheetInstance.id)
            } else {
                render(view: "edit", model: [timesheetInstance: timesheetInstance])
            }
        } catch (Exception e) {
            flash.message = e.getMessage()
            redirect(action: "show", id: timesheetInstance.id)
        }
    }
	
    def updateWithNotes = {
        def user = User.get(session.user.id)
        // original
        Timesheet timesheetInstance = Timesheet.get(params.id.toInteger())
        try {
            timesheetManagerService.validateState(params.id.toInteger(), timesheetManagerService.saving)
        }catch(Exception e) {
			
            flash.message = "Unable to save timesheet. The current state is " + timesheetInstance.currentState
            redirect(action: "show", id: params.id.toInteger())
            return
        }
		
        // copy
        Timesheet timesheetCopy = timesheetManagerService.deepCopyTimesheet(timesheetInstance.id)
		
        if (params.version) {
            def version = params.version.toLong()
            if (timesheetInstance.version > version) {
                timesheetInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'timesheet.label', default: 'Timesheet')] as Object[], "Another user has updated this Timesheet while you were editing")
                render(view: "modifyWithNotes", model: [timesheetInstance: timesheetInstance])
                return
            }
        }
	
        try {
            timesheetCopy.id = timesheetInstance.id
            validateEmptyNotes(timesheetCopy)
            timesheetCopy.properties = params
            populateModifiesFromForm(timesheetInstance)
            populateTodaysDateFromForm(timesheetInstance)
        } catch (Exception e) {
            flash.message = e.getMessage()
            timesheetCopy.id = timesheetInstance.id
            populateWorkdaysFromForm(timesheetCopy)
            def notes = obtainNotesFromForm(timesheetCopy)
            def weekdaysModified = validateWorkDayChangedExceptToday(timesheetInstance)
            def previousHourValues = obtainPreviousValues(timesheetInstance)
            render(view: "modifyWithNotes", model: [timesheetInstance: timesheetCopy, weekdaysModified:weekdaysModified, previousHourValues:previousHourValues, notes:notes])
            return
        }

        try {
            if (!timesheetInstance.hasErrors()) {
                timesheetManagerService.update(timesheetInstance)
                redirect(action: "show", id: timesheetInstance.id)
            } else {
                render(view: "modifyWithNotes", model: [timesheetInstance: timesheetInstance])
            }
        } catch (Exception e) {
            flash.message = e.getMessage()
            redirect(action: "show", id: timesheetInstance.id)
        }
    }

    def validateEmptyNotes(timesheetInstance) {
        timesheetInstance.timesheetEntries.eachWithIndex {
            tse, index ->
            def taNotes = obtainModifiedDayNotes(tse.taskAssignment)
            taNotes.eachWithIndex { oneNote, indx ->
                if (oneNote != null) {
                    if (oneNote.trim() == "") {
                        throw new RuntimeException("Error cannot leave Note/Reason blank.")
                    }
                }
            }
        }
    }
	
    def obtainNotesFromForm(timesheetInstance) {
        def notes = []
        timesheetInstance.timesheetEntries.eachWithIndex {
            tse, index ->
            def taNotes = obtainModifiedDayNotes(tse.taskAssignment)
            taNotes.eachWithIndex { oneNote, indx ->
                if (oneNote != null) {
                    notes.add oneNote
                }
            }
        }
        return notes
    }
	
	
    def obtainWeekdays(taskAssignment) {
        [ params["day1_${taskAssignment?.id}"],
            params["day2_${taskAssignment?.id}"],
            params["day3_${taskAssignment?.id}"],
            params["day4_${taskAssignment?.id}"],
            params["day5_${taskAssignment?.id}"],
            params["day6_${taskAssignment?.id}"],
            params["day7_${taskAssignment?.id}"]
        ]
    }
	
    def obtainModifiedDayHrs(taskAssignment) {
        [ params["modDay1_${taskAssignment?.id}_hrs"], //modDay1_2_note
            params["modDay2_${taskAssignment?.id}_hrs"],
            params["modDay3_${taskAssignment?.id}_hrs"],
            params["modDay4_${taskAssignment?.id}_hrs"],
            params["modDay5_${taskAssignment?.id}_hrs"],
            params["modDay6_${taskAssignment?.id}_hrs"],
            params["modDay7_${taskAssignment?.id}_hrs"]
        ]
    }
    def obtainModifiedDayNotes(taskAssignment) {
        [ params["modDay1_${taskAssignment?.id}_note"], //modDay1_2_note
            params["modDay2_${taskAssignment?.id}_note"],
            params["modDay3_${taskAssignment?.id}_note"],
            params["modDay4_${taskAssignment?.id}_note"],
            params["modDay5_${taskAssignment?.id}_note"],
            params["modDay6_${taskAssignment?.id}_note"],
            params["modDay7_${taskAssignment?.id}_note"]
        ]
    }

    def populateWorkdaysFromForm(timesheetInstance){
        timesheetInstance?.timesheetEntries.eachWithIndex {
            tse, index ->
            def daysOfWeek = obtainWeekdays(tse.taskAssignment)
            tse.workdays.eachWithIndex { workday, indx ->
				
                if (daysOfWeek[indx] == "") {
                    workday.hoursWorked = null
                } else {
                    try {
                        workday.hoursWorked = daysOfWeek[indx].toFloat()
                    } catch (Exception e) {
                        workday.hoursWorked = null
                    }
                }
				
            }
        }
    }
	
    def populateModifiesFromForm(timesheetInstance){
        timesheetInstance.timesheetEntries.eachWithIndex {
            tse, index ->
            def notes = obtainModifiedDayNotes(tse.taskAssignment)
            def dayHrs = obtainModifiedDayHrs(tse.taskAssignment)
            def daysOfWeek = obtainWeekdays(tse.taskAssignment)
            
            tse.workdays.eachWithIndex { workday, indx ->
                Float oldValue = workday?.hoursWorked

                //println ("   old value is ${oldValue} new value is ${dayHrs[indx]?.toFloat()}")
                if (dayHrs[indx] == "" || dayHrs[indx] == null) {
                    // skip
                    if (daysOfWeek[indx] && oldValue == null) {
                        workday.hoursWorked = daysOfWeek[indx].toFloat()
                        workday.save()
                    }
                } else {                     
                    workday.hoursWorked = dayHrs[indx].toFloat();
                    //println("mod hours: ${dayHrs[indx]}")
                    //println("mod notes: ${notes[indx]}")
                    if (workday.hoursWorked != oldValue && notes[indx] == "") {
                        throw new RuntimeException("Cannot leave a reason field blank.")
                    }
                    if (notes[indx]) {
                        Note note = new Note(dateCreated:new Date(),
                            noteType:'change',
                            oldValue: oldValue,
                            newValue:workday?.hoursWorked,
                            comment:notes[indx]
                        ).save()
                       
                        workday.notes.add note
                    }
                   
                    
                }
				
            }
        }
    }
    def populateTodaysDateFromForm(timesheetInstance){
        DateTime currentDay = DateTime.today(TimeZone.getDefault())
        currentDay = currentDay.getEndOfDay().truncate(DateTime.Unit.SECOND)
        timesheetInstance.timesheetEntries.eachWithIndex {
            tse, index ->
            def daysOfWeek = obtainWeekdays(tse.taskAssignment)
            tse.workdays.eachWithIndex { workday, indx ->
                DateTime dtWorkday = new DateTime(workday.dateWorked.toString()).getEndOfDay().truncate(DateTime.Unit.SECOND)
                if (currentDay.equals(dtWorkday)){
                    if (daysOfWeek[indx]) {
                        workday.hoursWorked = daysOfWeek[indx].toFloat()
                    }
                }
            }
        }
    }
	
    def signform = {
        def user = User.get(session.user.id)
        def tsId = params['id']
        def ts
        if (tsId) {
            ts = Timesheet.get(tsId)
        } else {
            ts = timesheetManagerService.retrieveCurrentTimesheet(user)
        }
        flash.message = ""
        [timesheetId:tsId]
    }
	
    def sign = {
        def user = User.get(session.user.id)
		
        // @todo must validate to not allow duplicates
        def tsId = params['timesheetId']
		
        Timesheet ts
        if (tsId) {
            ts = Timesheet.get(tsId)
        } else {
            ts = timesheetManagerService.retrieveCurrentTimesheet(user)
        }
		
        try {
			
            // update state of the timesheet
            timesheetManagerService.validateState(ts.id.toInteger(), timesheetManagerService.signing)

            def config = ConfigurationHolder.config

            if (config?.timesheet?.useLdap && config?.timesheet?.useLdap == true) {
                user = ldapAuthenticationService.authenticate(session.user.username, params.passwd)
            } else {
                if (Environment.current == Environment.DEVELOPMENT) {
                    user = User.findByUsernameAndPasswd(session.user.username, params.passwd)
                }
            }

            if (!user) {
                flash.message = "Authentication failed, unable to sign timesheet"
                render(view: "edit", model: [timesheetInstance: ts])
                return
            } else {
                signatureService.signTimesheet(ts, user)
                if (timesheetManagerService.sign(ts)){
                    flash.message = "${message(code: 'timesheet signed successfully', args: [message(code: 'timesheet.label', default: 'Timesheet'), user.id])}"
                    redirect(action: "listTimesheets", id: user.id)
                }
                else {
                    render(view: "edit", model: [timesheetInstance: ts])
                }
            }
        } catch (Exception e) {
            flash.message = e.getMessage()
            redirect(action: "listTimesheets", id: user.id)
        }
    }
	
	
    def lookupSearchForm = {
		
    }
	
    def lookupMyTimesheets = {
		
        // TODO OBTAIN THE DATE RANGES
		
        def startDate = params["startDate"]
        def endDate = params["endDate"]
		
        def user = User.get(session.user.id)
        def c = Timesheet.createCriteria()
        def timesheetList = c.list{
            between("startDate", startDate, endDate)
            eq("user", user)
        }

        if(!timesheetList){
            flash.message = "No timesheets found."
            redirect(action:"listTimesheets")
        }
//        def timesheetList = Timesheet.findAllByStartDateBetween(startDate, endDate)
		
        render(view: "listTimesheets", model: [timesheetList:timesheetList, timesheetInstanceTotal:timesheetList.count()])
    }
    def viewTimesheet = {
        def user = User.get(session.user.id)
        def ts = Timesheet.get(params.id)
        if (ts) {
            if (ts.user.id != session.user.id) {
                throw new RuntimeException("Access denied. This is not your timesheet")
            }
        }
        [timesheetInstance:ts]
    }
	
    def validateHoursPerDay(timesheetInstance) {
        // go through 7 days
        for (day in 1..7) {
            def subTotal = 0.0;
            timesheetInstance.timesheetEntries.eachWithIndex {  tse, index ->
                def dayField = params["day${day}_${tse?.taskAssignment?.id}"]
                if (dayField != "") {
                    try {
                        float hours = dayField.toFloat()
                        if (hours < 0 || hours > 24.0) {
                            throw new RuntimeException();
                        }
                        subTotal = subTotal + hours;
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                }
            }
            if (subTotal < 0 || subTotal > 24.0) {
                throw new RuntimeException();
            }
        }
    }
	
    // returns all days that was modified with a list of previous values.
    def obtainPreviousValues(timesheetInstance){
        def weekdaysModified = []
        DateTime currentDay = DateTime.today(TimeZone.getDefault())
        currentDay = currentDay.getEndOfDay().truncate(DateTime.Unit.SECOND)
		
        timesheetInstance.timesheetEntries.eachWithIndex {
            tse, index ->
            def daysOfWeek = obtainWeekdays(tse.taskAssignment)
            tse.workdays.eachWithIndex { workday, indx ->
                def sysHoursWorked = workday?.hoursWorked
                if (sysHoursWorked == null) {
                    sysHoursWorked = ""
                }
                DateTime dtWorkday = new DateTime(workday.dateWorked.toString()).getEndOfDay().truncate(DateTime.Unit.SECOND)

                if (!currentDay.equals(dtWorkday)) {
                    if (daysOfWeek[indx] != sysHoursWorked.toString()) {
                        Workday changedWorkday = new Workday()
                        changedWorkday.dateWorked = workday?.dateWorked
                        changedWorkday.hoursWorked = workday?.hoursWorked
                        changedWorkday.timesheetEntry = tse
                        weekdaysModified.add changedWorkday
                    }
                }
            }
        }
        return weekdaysModified
    }
	
    def validateWorkDayChangedExceptToday(timesheetInstance){
        def weekdaysModified = []
        DateTime currentDay = DateTime.today(TimeZone.getDefault())
        currentDay = currentDay.getEndOfDay().truncate(DateTime.Unit.SECOND)
		
        timesheetInstance.timesheetEntries.eachWithIndex {
            tse, index ->
            def daysOfWeek = obtainWeekdays(tse.taskAssignment)
            tse.workdays.eachWithIndex { workday, indx ->
                def sysHoursWorked = workday?.hoursWorked
                
                DateTime dtWorkday = new DateTime(workday.dateWorked.toString()).getEndOfDay().truncate(DateTime.Unit.SECOND)

                if (!currentDay.equals(dtWorkday)) {

                    Float formWorkHour = null
                    if (daysOfWeek[indx] && daysOfWeek[indx].isFloat()) {
                        formWorkHour = daysOfWeek[indx].toFloat()
                    }

                    if (sysHoursWorked != null) {
                        if (formWorkHour == null) {
                            formWorkHour = 0f;
                        }
                        if (formWorkHour != sysHoursWorked) {
                            Workday changedWorkday = new Workday()
                            changedWorkday.dateWorked = workday?.dateWorked
                            changedWorkday.hoursWorked = formWorkHour
                            changedWorkday.timesheetEntry = tse
                            weekdaysModified.add changedWorkday
                        }
                    }
                }
            }
        }
        return weekdaysModified
    }
	
    def updateWorkDayChangedToday(timesheetInstance){
        DateTime currentDay = DateTime.today(TimeZone.getDefault())
        currentDay = currentDay.getEndOfDay().truncate(DateTime.Unit.SECOND)
        timesheetInstance.timesheetEntries.eachWithIndex {
            tse, index ->
            def daysOfWeek = obtainWeekdays(tse.taskAssignment)
            tse.workdays.eachWithIndex { workday, indx ->
                def sysHoursWorked = workday?.hoursWorked
                if (sysHoursWorked == null) {
                    sysHoursWorked = ""
                }

                DateTime dtWorkday = new DateTime(workday.dateWorked.toString()).getEndOfDay().truncate(DateTime.Unit.SECOND)
                //println("currentDay = ${currentDay}")
                //println("dtWorkday = ${dtWorkday}")
                if (currentDay.equals(dtWorkday)) {
                    if (daysOfWeek[indx] != sysHoursWorked.toString()) {
                        //def todayMod = new Workday()
                        //todayMod.dateWorked = workday?.dateWorked
                        if (daysOfWeek[indx]) {
                            workday?.hoursWorked = daysOfWeek[indx].toFloat()
//                            println("modified workday?.hoursWorked= ${workday?.hoursWorked}")
							
                        }
                    }
                }
            } // wds
        } // te eachWithIndex
    } // updateWorkDayChangedToday
}
