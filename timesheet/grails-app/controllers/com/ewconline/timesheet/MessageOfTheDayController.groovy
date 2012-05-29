package com.ewconline.timesheet

class MessageOfTheDayController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [messageOfTheDayInstanceList: MessageOfTheDay.list(params), messageOfTheDayInstanceTotal: MessageOfTheDay.count()]
    }

    def create = {
        def messageOfTheDayInstance = new MessageOfTheDay()
        messageOfTheDayInstance.properties = params
        return [messageOfTheDayInstance: messageOfTheDayInstance]
    }

    def save = {
        def messageOfTheDayInstance = new MessageOfTheDay(params)
        if (messageOfTheDayInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'messageOfTheDay.label', default: 'MessageOfTheDay'), messageOfTheDayInstance.id])}"
            redirect(action: "show", id: messageOfTheDayInstance.id)
        }
        else {
            render(view: "create", model: [messageOfTheDayInstance: messageOfTheDayInstance])
        }
    }

    def show = {
        def messageOfTheDayInstance = MessageOfTheDay.get(params.id)
        if (!messageOfTheDayInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'messageOfTheDay.label', default: 'MessageOfTheDay'), params.id])}"
            redirect(action: "list")
        }
        else {
            [messageOfTheDayInstance: messageOfTheDayInstance]
        }
    }

    def edit = {
        def messageOfTheDayInstance = MessageOfTheDay.get(params.id)
        if (!messageOfTheDayInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'messageOfTheDay.label', default: 'MessageOfTheDay'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [messageOfTheDayInstance: messageOfTheDayInstance]
        }
    }

    def update = {
        def messageOfTheDayInstance = MessageOfTheDay.get(params.id)
        if (messageOfTheDayInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (messageOfTheDayInstance.version > version) {
                    
                    messageOfTheDayInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'messageOfTheDay.label', default: 'MessageOfTheDay')] as Object[], "Another user has updated this MessageOfTheDay while you were editing")
                    render(view: "edit", model: [messageOfTheDayInstance: messageOfTheDayInstance])
                    return
                }
            }
            messageOfTheDayInstance.properties = params
            if (!messageOfTheDayInstance.hasErrors() && messageOfTheDayInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'messageOfTheDay.label', default: 'MessageOfTheDay'), messageOfTheDayInstance.id])}"
                redirect(action: "show", id: messageOfTheDayInstance.id)
            }
            else {
                render(view: "edit", model: [messageOfTheDayInstance: messageOfTheDayInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'messageOfTheDay.label', default: 'MessageOfTheDay'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def messageOfTheDayInstance = MessageOfTheDay.get(params.id)
        if (messageOfTheDayInstance) {
            try {
                messageOfTheDayInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'messageOfTheDay.label', default: 'MessageOfTheDay'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'messageOfTheDay.label', default: 'MessageOfTheDay'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'messageOfTheDay.label', default: 'MessageOfTheDay'), params.id])}"
            redirect(action: "list")
        }
    }
}
