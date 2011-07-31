package com.ewconline.timesheet

class ApproverController {
    def timesheetManagerService
    def index = { }
	
    def approverListTimesheet = {

        def user = User.get(session.user.id)
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def timesheetList = Timesheet.createCriteria().list {
            and {
                eq('currentState', 'SIGNED')

            }
            maxResults(params.max)
            order('lastUpdated', 'desc')
        }
		
        [timesheetList: timesheetList, timesheetInstanceTotal: timesheetList.count()]
    }
	
    def userApprove = {
	
		
    }
	
	
    def accountantApprove = {
		
        def user = User.get(session.user.id)
		
        Timesheet timesheetInstance = Timesheet.get(params.id)
		
        if (params.version) {
            def version = params.version.toLong()
            if (timesheetInstance.version > version) {
                timesheetInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'timesheet.label', default: 'Timesheet')] as Object[], "Another user has updated this Timesheet while you were editing")
                render(view: "edit", model: [timesheetInstance: timesheetInstance])
                return
            }
        }
		
		
        if (!timesheetInstance.hasErrors()) {
            try {
                timesheetManagerService.approve(timesheetInstance, user, session.accountantRole, session.approverRole )
            } catch (Exception e) {
                flash.message = e.getMessage()
            }
        }
        redirect(action: "approverListTimesheet")
    }

    def accountantDisapprove = {
		
        def user = User.get(session.user.id)
			
        Timesheet timesheetInstance = Timesheet.get(params.id)
			
			
        if (params.version) {
            def version = params.version.toLong()
            if (timesheetInstance.version > version) {
                timesheetInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'timesheet.label', default: 'Timesheet')] as Object[], "Another user has updated this Timesheet while you were editing")
                render(view: "edit", model: [timesheetInstance: timesheetInstance])
                return
            }
        }
        // TODO MUST LOG COMMENT AS TO WHY DISAPPROVE
        // if open allow state to be pending

			
        if (!timesheetInstance.hasErrors()) {
				
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), timesheetInstance.id])}"
            timesheetManagerService.disapprove(timesheetInstance, user, session.accountantRole, session.approverRole)
            redirect(action: "approverListTimesheet")
        }
    }
	
    def approvedTimesheets = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
        def timesheets = Timesheet.createCriteria().list {
            and {
                eq('currentState', 'APPROVED')
            }
            maxResults(params.max)
            order('lastUpdated', 'desc')
        }
        [timesheetList: timesheets, timesheetInstanceTotal: timesheets.count()]
    }

    def viewTimesheet = {
        def ts = Timesheet.get(params.id)
        [timesheetInstance:ts]
    }
}
