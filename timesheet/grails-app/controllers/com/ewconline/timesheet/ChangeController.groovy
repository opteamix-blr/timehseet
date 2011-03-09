package com.ewconline.timesheet

class ChangeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [changeInstanceList: Change.list(params), changeInstanceTotal: Change.count()]
    }

    def create = {
        def changeInstance = new Change()
        changeInstance.properties = params
        return [changeInstance: changeInstance]
    }

    def save = {
        def changeInstance = new Change(params)
        if (changeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'change.label', default: 'Change'), changeInstance.id])}"
            redirect(action: "show", id: changeInstance.id)
        }
        else {
            render(view: "create", model: [changeInstance: changeInstance])
        }
    }

    def show = {
        def changeInstance = Change.get(params.id)
        if (!changeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'change.label', default: 'Change'), params.id])}"
            redirect(action: "list")
        }
        else {
            [changeInstance: changeInstance]
        }
    }

    def edit = {
        def changeInstance = Change.get(params.id)
        if (!changeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'change.label', default: 'Change'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [changeInstance: changeInstance]
        }
    }

    def update = {
        def changeInstance = Change.get(params.id)
        if (changeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (changeInstance.version > version) {
                    
                    changeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'change.label', default: 'Change')] as Object[], "Another user has updated this Change while you were editing")
                    render(view: "edit", model: [changeInstance: changeInstance])
                    return
                }
            }
            changeInstance.properties = params
            if (!changeInstance.hasErrors() && changeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'change.label', default: 'Change'), changeInstance.id])}"
                redirect(action: "show", id: changeInstance.id)
            }
            else {
                render(view: "edit", model: [changeInstance: changeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'change.label', default: 'Change'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def changeInstance = Change.get(params.id)
        if (changeInstance) {
            try {
                changeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'change.label', default: 'Change'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'change.label', default: 'Change'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'change.label', default: 'Change'), params.id])}"
            redirect(action: "list")
        }
    }
}
