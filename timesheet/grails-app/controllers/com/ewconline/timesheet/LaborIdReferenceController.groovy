package com.ewconline.timesheet

class LaborIdReferenceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [laborIdReferenceInstanceList: LaborIdReference.list(params), laborIdReferenceInstanceTotal: LaborIdReference.count()]
    }

    def create = {
        def laborIdReferenceInstance = new LaborIdReference()
        laborIdReferenceInstance.properties = params
        return [laborIdReferenceInstance: laborIdReferenceInstance]
    }

    def save = {
        def laborIdReferenceInstance = new LaborIdReference(params)
        if (laborIdReferenceInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'laborIdReference.label', default: 'LaborIdReference'), laborIdReferenceInstance.id])}"
            redirect(action: "show", id: laborIdReferenceInstance.id)
        }
        else {
            render(view: "create", model: [laborIdReferenceInstance: laborIdReferenceInstance])
        }
    }

    def show = {
        def laborIdReferenceInstance = LaborIdReference.get(params.id)
        if (!laborIdReferenceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'laborIdReference.label', default: 'LaborIdReference'), params.id])}"
            redirect(action: "list")
        }
        else {
            [laborIdReferenceInstance: laborIdReferenceInstance]
        }
    }

    def edit = {
        def laborIdReferenceInstance = LaborIdReference.get(params.id)
        if (!laborIdReferenceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'laborIdReference.label', default: 'LaborIdReference'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [laborIdReferenceInstance: laborIdReferenceInstance]
        }
    }

    def update = {
        def laborIdReferenceInstance = LaborIdReference.get(params.id)
        if (laborIdReferenceInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (laborIdReferenceInstance.version > version) {
                    
                    laborIdReferenceInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'laborIdReference.label', default: 'LaborIdReference')] as Object[], "Another user has updated this LaborIdReference while you were editing")
                    render(view: "edit", model: [laborIdReferenceInstance: laborIdReferenceInstance])
                    return
                }
            }
            laborIdReferenceInstance.properties = params
            if (!laborIdReferenceInstance.hasErrors() && laborIdReferenceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'laborIdReference.label', default: 'LaborIdReference'), laborIdReferenceInstance.id])}"
                redirect(action: "show", id: laborIdReferenceInstance.id)
            }
            else {
                render(view: "edit", model: [laborIdReferenceInstance: laborIdReferenceInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'laborIdReference.label', default: 'LaborIdReference'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def laborIdReferenceInstance = LaborIdReference.get(params.id)
        if (laborIdReferenceInstance) {
            try {
                laborIdReferenceInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'laborIdReference.label', default: 'LaborIdReference'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'laborIdReference.label', default: 'LaborIdReference'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'laborIdReference.label', default: 'LaborIdReference'), params.id])}"
            redirect(action: "list")
        }
    }
}
