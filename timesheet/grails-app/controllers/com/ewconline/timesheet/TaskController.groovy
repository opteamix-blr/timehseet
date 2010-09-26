package com.ewconline.timesheet

import org.codehaus.groovy.runtime.DefaultGroovyMethods 

class TaskController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [taskInstanceList: Task.list(params), taskInstanceTotal: Task.count()]
    }

    def create = {
        def taskInstance = new Task()
        taskInstance.properties = params
		
		def allChargeCodes = ChargeCode.list([sort: 'chargeNumber', order: 'asc'])
		def allLaborCategories = LaborCategory.list([sort: 'name', order: 'asc'])
		

        return [taskInstance: taskInstance,
			allChargeCodes:allChargeCodes,
			allLaborCategories:allLaborCategories
		]
    }

    def save = {
        def taskInstance = new Task(params)
		
        if (taskInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'task.label', default: 'Task'), taskInstance.id])}"
            redirect(action: "show", id: taskInstance.id)
        }
        else {
            render(view: "create", model: [taskInstance: taskInstance])
        }
    }

    def show = {
        def taskInstance = Task.get(params.id)
        if (!taskInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'task.label', default: 'Task'), params.id])}"
            redirect(action: "list")
        }
        else {
            [taskInstance: taskInstance]
        }
    }

    def edit = {
        def taskInstance = Task.get(params.id)
		def allChargeCodes = ChargeCode.list([sort: 'chargeNumber', order: 'asc'])
		def availChargeCodes = DefaultGroovyMethods.minus(allChargeCodes, taskInstance.chargeCodes)
	
		def allLaborCategories = LaborCategory.list([sort: 'name', order: 'asc'])
		def availLaborCategories = DefaultGroovyMethods.minus(allLaborCategories, taskInstance.laborCategories)

        if (!taskInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'task.label', default: 'Task'), params.id])}"
            redirect(action: "list")
        }
        else {
			return [
				taskInstance:taskInstance,
				allChargeCodes:availChargeCodes,
				allLaborCategories:availLaborCategories,
				chargeCodes:taskInstance.chargeCodes,		
				laborCategories:taskInstance.laborCategories
			]
        }
    }

    def update = {
        def taskInstance = Task.get(params.id)
        if (taskInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (taskInstance.version > version) {
                    
                    taskInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'task.label', default: 'Task')] as Object[], "Another user has updated this Task while you were editing")
                    render(view: "edit", model: [taskInstance: taskInstance])
                    return
                }
            }
            taskInstance.properties = params
            if (!taskInstance.hasErrors() && taskInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'task.label', default: 'Task'), taskInstance.id])}"
                redirect(action: "show", id: taskInstance.id)
            }
            else {
                render(view: "edit", model: [taskInstance: taskInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'task.label', default: 'Task'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def taskInstance = Task.get(params.id)
        if (taskInstance) {
            try {
                taskInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'task.label', default: 'Task'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'task.label', default: 'Task'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'task.label', default: 'Task'), params.id])}"
            redirect(action: "list")
        }
    }
}
