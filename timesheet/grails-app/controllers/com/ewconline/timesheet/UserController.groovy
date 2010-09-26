package com.ewconline.timesheet

import org.codehaus.groovy.runtime.DefaultGroovyMethods;


class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create = {
        def userInstance = new User()
        userInstance.properties = params
		def allRoles = Role.list([sort: 'authority', order: 'asc'])
		def allTaskAssignments = TaskAssignment.list([sort: 'displayName', order: 'asc'])
		
		return [userInstance: userInstance, allRoles:allRoles, allTaskAssignments:allTaskAssignments]
    }

    def save = {
        def userInstance = new User(params)
		
		println "avail taskassignments:"
//		for (ta in params.taskAssignments) {
//			println ">>>> " + ta
//		}
		
		
        if (userInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
            redirect(action: "show", id: userInstance.id)
        }
        else {
            render(view: "create", model: [userInstance: userInstance])
        }
    }

    def show = {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userInstance: userInstance]
        }
    }

    def edit = {
        def userInstance = User.get(params.id)
		def allTaskAssignments = TaskAssignment.list([sort: 'displayName', order: 'asc'])
		def availTaskAssignments = DefaultGroovyMethods.minus(allTaskAssignments, userInstance.taskAssignments)
		
		def allRoles = Role.list([sort: 'authority', order: 'asc'])
		def availRoles = DefaultGroovyMethods.minus(allRoles, userInstance.authorities)
//		println "avail roles:"
//		for (role in availRoles) {
//			println role.authority
//		}
		if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        } else {
            return [userInstance: userInstance, 
					allRoles:availRoles,
					allTaskAssignments:availTaskAssignments,
					taskAssignments:userInstance.taskAssignments,
					authorities:userInstance.authorities
			]
        }
    }

    def update = {
        def userInstance = User.get(params.id)
		//def transferUser = new User(params)
        if (userInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userInstance.version > version) {
                    userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
                    render(view: "edit", model: [userInstance: userInstance])
                    return
                }
            }
			
			// Roles information
			def transferUserRoles = params.authorities.collect { targetA ->
				def role = Role.get(targetA)
				role
			}
			def userCurRoles = userInstance.authorities
			def roleRemoveList = DefaultGroovyMethods.minus(userCurRoles, transferUserRoles)
			def roleAddList = DefaultGroovyMethods.minus(transferUserRoles, userCurRoles)
			
            userInstance.properties = params
			for (role in roleRemoveList) {
				userInstance.removeFromAuthorities(role)
			}
			for (role in roleAddList) {
				userInstance.addToAuthorities(role)
			}

			// TaskAssignments
			def transferUserTaskAssignments = params.taskAssignments.collect { targetT ->
				TaskAssignment.get(targetT)
			}
			def userCurTaskAssignments = userInstance.taskAssignments
			def taskAssignmentRemoveList = DefaultGroovyMethods.minus(userCurTaskAssignments, transferUserTaskAssignments)
			def taskAssignmentAddList = DefaultGroovyMethods.minus(transferUserTaskAssignments, userCurTaskAssignments)
			
			for (taskAssignment in taskAssignmentRemoveList) {
				userInstance.removeFromTaskAssignments(taskAssignment)
			}
			for (taskAssignment in taskAssignmentAddList) {
				userInstance.addToTaskAssignments(taskAssignment)
			}
			
			
			if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
                redirect(action: "show", id: userInstance.id)
            }
            else {
                render(view: "edit", model: [userInstance: userInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def userInstance = User.get(params.id)
        if (userInstance) {
            try {
                userInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
    }
}
