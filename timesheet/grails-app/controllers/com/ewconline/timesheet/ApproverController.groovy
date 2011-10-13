package com.ewconline.timesheet

class ApproverController {
    def timesheetManagerService
    def etimeSecurityService
    
    def index = { }

    // recently signed
    def approverListTimesheet = {

        def user = User.get(session.user.id)
        params.max = Math.min(params?.max?.toInteger() ?: 10, 100)
        params.offset = params?.offset?.toInteger() ?: 0
        
        def timesheetList
        def totCount
        if(user.authorities.find{
             it.authority = etimeSecurityService.ACCOUNTANT_ROLE
        }){
            timesheetList = Timesheet.createCriteria().list(max: params.max, offset: params.offset) {
                eq('currentState', 'SIGNED')
                order('lastUpdated', 'desc')
            }
            totCount = Timesheet.createCriteria().list {
                eq('currentState', 'SIGNED')
                order('lastUpdated', 'desc')
            }.size()
        } else {
            timesheetList = Timesheet.createCriteria().list(max: params.max, offset: params.offset) {
                timesheetEntries{
                    taskAssignment{
                        taskAssignmentApprovals{
                            eq("user", user)
                        }
                    }
                }
                eq('currentState', 'SIGNED')
                order('lastUpdated', 'desc')
            }
            timesheetList = timesheetList.unique()
        
            totCount = Timesheet.createCriteria().list {
                timesheetEntries{
                    taskAssignment{
                        taskAssignmentApprovals{
                            eq("user", user)
                        }
                    }
                }
                eq('currentState', 'SIGNED')
                order('lastUpdated', 'desc')
            }.size()
        }
        [timesheetList: timesheetList, timesheetInstanceTotal: totCount]
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

    // Approved timesheets
    def approvedTimesheets = {
        params.max = Math.min(params?.max?.toInteger() ?: 10, 100)
        params.offset = params?.offset?.toInteger() ?: 0

        // get page of approved
        def timesheetList
        def totCount
        if(user.authorities.find{
             it.authority = etimeSecurityService.ACCOUNTANT_ROLE
        }){
            timesheetList = Timesheet.createCriteria().list(max: params.max, offset: params.offset) {
                eq('currentState', 'SIGNED')
                order('lastUpdated', 'desc')
            }
            totCount = Timesheet.createCriteria().list {
                eq('currentState', 'SIGNED')
                order('lastUpdated', 'desc')
            }.size()
        } else {
            timesheetList = Timesheet.createCriteria().list(max: params.max, offset: params.offset) {
                timesheetEntries{
                    taskAssignment{
                        taskAssignmentApprovals{
                            eq("user", user)
                        }
                    }
                }
                eq('currentState', 'SIGNED')
                order('lastUpdated', 'desc')
            }
            timesheetList = timesheetList.unique()

            totCount = Timesheet.createCriteria().list {
                timesheetEntries{
                    taskAssignment{
                        taskAssignmentApprovals{
                            eq("user", user)
                        }
                    }
                }
                eq('currentState', 'SIGNED')
                order('lastUpdated', 'desc')
            }.size()
        }
        
        [timesheetList: timesheetList, timesheetInstanceTotal: totCount]
    }

    def viewTimesheet = {
        def ts = Timesheet.get(params.id)
        [timesheetInstance:ts]
    }
}
