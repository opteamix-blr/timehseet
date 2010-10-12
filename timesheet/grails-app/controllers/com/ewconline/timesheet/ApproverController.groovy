package com.ewconline.timesheet

class ApproverController {
	def timesheetManagerService
    def index = { }
	
	def approverListTimesheet = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def timesheetList = [Timesheet.findByApproveState("pending")]
        [timesheetList: timesheetList, timesheetInstanceTotal: timesheetList.size()]
    }
	
	def userApprove = {
	
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
		
		// if open allow state to be pending
		if (timesheetInstance.approveState == "open") {
			timesheetInstance.approveState = "pending"
		} else {
			flash.message = "${message(code: 'default.approveState.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), timesheetInstance.id])}"
			redirect(controller:"timesheet", action: "listTimesheets")
			return
		}
		
		if (!timesheetInstance.hasErrors() && timesheetInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), timesheetInstance.id])}"
			redirect(controller:"timesheet", action: "show", id: timesheetInstance.id)
		} else {
			render(controller:"timesheet", view: "edit", model: [timesheetInstance: timesheetInstance])
		}
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
			
			// if open allow state to be pending
			if (timesheetInstance.approveState == "pending") {
				timesheetInstance.approveState = "approved"
			} else {
				flash.message = "${message(code: 'default.approveState.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), timesheetInstance.id])}"
				redirect(controller:"approver", action: "approverListTimesheet")
				return
			}
			
			if (!timesheetInstance.hasErrors() && timesheetInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), timesheetInstance.id])}"
				redirect(action: "approverListTimesheet")
			}
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
			if (timesheetInstance.approveState == "pending") {
				timesheetInstance.approveState = "open"
			} else {
				flash.message = "${message(code: 'default.approveState.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), timesheetInstance.id])}"
				redirect(controller:"approver", action: "approverListTimesheet")
				return
			}
			
			if (!timesheetInstance.hasErrors() && timesheetInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), timesheetInstance.id])}"
				redirect(action: "approverListTimesheet")
			}
	}

}
