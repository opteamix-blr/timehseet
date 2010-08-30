package com.ewconline.timesheet

class ChargeCodeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [chargeCodeInstanceList: ChargeCode.list(params), chargeCodeInstanceTotal: ChargeCode.count()]
    }

    def create = {
        def chargeCodeInstance = new ChargeCode()
        chargeCodeInstance.properties = params
        return [chargeCodeInstance: chargeCodeInstance]
    }

    def save = {
        def chargeCodeInstance = new ChargeCode(params)
        if (chargeCodeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'chargeCode.label', default: 'ChargeCode'), chargeCodeInstance.id])}"
            redirect(action: "show", id: chargeCodeInstance.id)
        }
        else {
            render(view: "create", model: [chargeCodeInstance: chargeCodeInstance])
        }
    }

    def show = {
        def chargeCodeInstance = ChargeCode.get(params.id)
        if (!chargeCodeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chargeCode.label', default: 'ChargeCode'), params.id])}"
            redirect(action: "list")
        }
        else {
            [chargeCodeInstance: chargeCodeInstance]
        }
    }

    def edit = {
        def chargeCodeInstance = ChargeCode.get(params.id)
        if (!chargeCodeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chargeCode.label', default: 'ChargeCode'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [chargeCodeInstance: chargeCodeInstance]
        }
    }

    def update = {
        def chargeCodeInstance = ChargeCode.get(params.id)
        if (chargeCodeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (chargeCodeInstance.version > version) {
                    
                    chargeCodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'chargeCode.label', default: 'ChargeCode')] as Object[], "Another user has updated this ChargeCode while you were editing")
                    render(view: "edit", model: [chargeCodeInstance: chargeCodeInstance])
                    return
                }
            }
            chargeCodeInstance.properties = params
            if (!chargeCodeInstance.hasErrors() && chargeCodeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'chargeCode.label', default: 'ChargeCode'), chargeCodeInstance.id])}"
                redirect(action: "show", id: chargeCodeInstance.id)
            }
            else {
                render(view: "edit", model: [chargeCodeInstance: chargeCodeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chargeCode.label', default: 'ChargeCode'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def chargeCodeInstance = ChargeCode.get(params.id)
        if (chargeCodeInstance) {
            try {
                chargeCodeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'chargeCode.label', default: 'ChargeCode'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'chargeCode.label', default: 'ChargeCode'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'chargeCode.label', default: 'ChargeCode'), params.id])}"
            redirect(action: "list")
        }
    }
}
