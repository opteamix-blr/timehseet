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
		def allTasks = Task.list([sort: 'name', order: 'asc'])
		def allLaborCategories = LaborCategory.list([sort: 'name', order: 'asc'])
		def allChargeCodes = ChargeCode.list([sort: 'chargeNumber', order: 'asc'])
		def allRoles = Role.list([sort: 'authority', order: 'asc'])
        return [userInstance: userInstance, allRoles:allRoles, allTasks:allTasks, allLaborCategories:allLaborCategories, allChargeCodes:allChargeCodes]
    }

    def save = {
        def userInstance = new User(params)
		
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
		def allTasks = Task.list([sort: 'name', order: 'asc'])
		def availTasks = DefaultGroovyMethods.minus(allTasks, userInstance.tasks)
		
		def allLaborCategories = LaborCategory.list([sort: 'name', order: 'asc'])
		def availLaborCategories = DefaultGroovyMethods.minus(allLaborCategories, userInstance.laborCategories)
		
		def allChargeCodes = ChargeCode.list([sort: 'chargeNumber', order: 'asc'])
		def availChargeCodes = DefaultGroovyMethods.minus(allChargeCodes, userInstance.chargeCodes)
		
		
		def allRoles = Role.list([sort: 'authority', order: 'asc'])
		def availRoles = DefaultGroovyMethods.minus(allRoles, userInstance.authorities)
//		println "avail roles:"
//		for (role in availRoles) {
//			println role.authority
//		}
		if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [userInstance: userInstance, 
					allTasks:availTasks, 
					allLaborCategories:availLaborCategories, 
					allChargeCodes:availChargeCodes, 
					allRoles:availRoles,
					tasks:userInstance.tasks,
					laborCategories:userInstance.laborCategories,
					chargeCodes:userInstance.chargeCodes,
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

			// Tasks
			def transferUserTasks = params.tasks.collect { targetT ->
				Task.get(targetT)
			}
			def userCurTasks = userInstance.tasks
			def taskRemoveList = DefaultGroovyMethods.minus(userCurTasks, transferUserTasks)
			def taskAddList = DefaultGroovyMethods.minus(transferUserTasks, userCurTasks)
			
			for (task in taskRemoveList) {
				userInstance.removeFromTasks(task)
			}
			for (task in taskAddList) {
				userInstance.addToTasks(task)
			}
			
			// Labor Categories
			def transferUserLabCats = params.laborCategories.collect { lc ->
				LaborCategory.get(lc)
			}
			def userCurLabCats = userInstance.laborCategories
			def labCatsRemoveList = DefaultGroovyMethods.minus(userCurLabCats, transferUserLabCats)
			def labCatsAddList = DefaultGroovyMethods.minus(transferUserLabCats, userCurLabCats)
			
			for (lc in labCatsRemoveList) {
				userInstance.removeFromLaborCategories(lc)
			}
			for (lc in labCatsAddList) {
				userInstance.addToLaborCategories(lc)
			}
			
			// Charge codes
			def transferUserChCodes = params.chargeCodes.collect { cc ->
				ChargeCode.get(cc)
			}
			def userCurChCodes = userInstance.chargeCodes
			def chCodeRemoveList = DefaultGroovyMethods.minus(userCurChCodes, transferUserChCodes)
			def chCodeAddList = DefaultGroovyMethods.minus(transferUserChCodes, userCurChCodes)
			
			for (cc in chCodeRemoveList) {
				userInstance.removeFromChargeCodes(cc)
			}
			for (cd in chCodeAddList) {
				userInstance.addToLaborChargeCodes(cc)
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
class UserCommand extends User {
	
}