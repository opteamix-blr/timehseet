package com.ewconline.timesheet

class ApproverController {
	def timesheetManagerService
    def index = { }
	
	def approverListTimesheet = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def timesheetList = [Timesheet.findByCurrentState(timesheetManagerService.SIGNED)]
        [timesheetList: timesheetList, timesheetInstanceTotal: timesheetList.size()]
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
				timesheetManagerService.approve(timesheetInstance)
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

			
			if (!timesheetInstance.hasErrors()) {
				
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'timesheet.label', default: 'Timesheet'), timesheetInstance.id])}"
				timesheetManagerService.disapprove(timesheetInstance)
				redirect(action: "approverListTimesheet")
			}
	}

}
