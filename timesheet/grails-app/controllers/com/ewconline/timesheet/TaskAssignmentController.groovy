package com.ewconline.timesheet

class TaskAssignmentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [taskAssignmentInstanceList: TaskAssignment.list(params), taskAssignmentInstanceTotal: TaskAssignment.count()]
    }

    def create = {
        def taskAssignmentInstance = new TaskAssignment()
        taskAssignmentInstance.properties = params
        return [taskAssignmentInstance: taskAssignmentInstance]
    }

    def save = {
        def taskAssignmentInstance = new TaskAssignment()
		taskAssignmentInstance.properties = params
		
		def chargeCode = ChargeCode.get(params?.chargeCode.id)
		def laborCategory = LaborCategory.get(params?.laborCategory.id)
		def task = Task.get(params?.task.id)

		taskAssignmentInstance.task = task
		taskAssignmentInstance.chargeCode = chargeCode
		taskAssignmentInstance.laborCategory = laborCategory
		
        if (taskAssignmentInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'taskAssignment.label', default: 'TaskAssignment'), taskAssignmentInstance.id])}"
            redirect(action: "show", id: taskAssignmentInstance.id)
        }
        else {
            render(view: "create", model: [taskAssignmentInstance: taskAssignmentInstance])
        }
    }

    def show = {
        def taskAssignmentInstance = TaskAssignment.get(params.id)
        if (!taskAssignmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'taskAssignment.label', default: 'TaskAssignment'), params.id])}"
            redirect(action: "list")
        }
        else {
            [taskAssignmentInstance: taskAssignmentInstance]
        }
    }

    def edit = {
        def taskAssignmentInstance = TaskAssignment.get(params.id)
        if (!taskAssignmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'taskAssignment.label', default: 'TaskAssignment'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [taskAssignmentInstance: taskAssignmentInstance]
        }
    }

    def update = {
        def taskAssignmentInstance = TaskAssignment.get(params.id)
        if (taskAssignmentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (taskAssignmentInstance.version > version) {
                    
                    taskAssignmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'taskAssignment.label', default: 'TaskAssignment')] as Object[], "Another user has updated this TaskAssignment while you were editing")
                    render(view: "edit", model: [taskAssignmentInstance: taskAssignmentInstance])
                    return
                }
            }
            taskAssignmentInstance.properties = params
            if (!taskAssignmentInstance.hasErrors() && taskAssignmentInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'taskAssignment.label', default: 'TaskAssignment'), taskAssignmentInstance.id])}"
                redirect(action: "show", id: taskAssignmentInstance.id)
            }
            else {
                render(view: "edit", model: [taskAssignmentInstance: taskAssignmentInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'taskAssignment.label', default: 'TaskAssignment'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def taskAssignmentInstance = TaskAssignment.get(params.id)
        if (taskAssignmentInstance) {
            try {
                taskAssignmentInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'taskAssignment.label', default: 'TaskAssignment'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'taskAssignment.label', default: 'TaskAssignment'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'taskAssignment.label', default: 'TaskAssignment'), params.id])}"
            redirect(action: "list")
        }
    }
}
