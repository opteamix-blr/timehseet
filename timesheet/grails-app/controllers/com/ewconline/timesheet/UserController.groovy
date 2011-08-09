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
        def approvers = com.ewconline.timesheet.Role.findByAuthority('approver_role').people

        def allTaskAssignments = TaskAssignment.createCriteria().list {
            eq('enabled', true)
        }
		
        return [
            userInstance: userInstance,
            allRoles:allRoles,
            allTaskAssignments:allTaskAssignments,
            approvers: approvers
        ]
    }

    def save = {
        def userInstance = new User(params)
		
        def tasks = [params["taskAssignment.task.id"]].flatten()
        def chargeCodes = [params["taskAssignment.chargeCode.id"]].flatten()
        def laborCategories = [params["taskAssignment.laborCategory.id"]].flatten()
        def enabled = [params["taskAssignment.enabled"]].flatten()
        def approver1 = [params["taskAssignment.approver1.id"]].flatten()
        def approver2 = [params["taskAssignment.approver2.id"]].flatten()

        tasks.eachWithIndex() { task, i ->
            if(task != 'null'){
                def ta = new TaskAssignment()
                ta.task = Task.get(task)
                ta.chargeCode = ChargeCode.get(chargeCodes[i])
                ta.laborCategory = LaborCategory.get(laborCategories[i])
                ta.enabled = (enabled[i] == 'enabled') ? true : false
                userInstance.addToTaskAssignments(ta)
                if(approver1[i] != 'null'){
                    def taa = new TaskAssignmentApproval()
                    taa.user = User.get(approver1[i] as Long)
                    ta.addToTaskAssignmentApprovals(taa)
                }
                if(approver2[i] != 'null'){
                    def taa = new TaskAssignmentApproval()
                    taa.user = User.get(approver2[i] as Long)
                    ta.addToTaskAssignments(taa)
                }
            }
        }


	userInstance.guid = java.util.UUID.randomUUID().toString()
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
        def allTaskAssignments = TaskAssignment.createCriteria().list {
            eq('enabled', true)
        }
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
            userInstance.taskAssignments.each{
                log.debug it.taskAssignmentApprovals.size()
            }
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

            //existing task assignments
            def taIds = [params?.existingTaskAssignment?.id].flatten()
            def enableds = [params?.existingTaskAssignment?.enabled].flatten()
            def approver1 = [params?.existingTaskAssignment?.approver1?.id].flatten()
            def approver2 = [params?.existingTaskAssignment?.approver2?.id].flatten()
            params.each {k, v -> println k + "--" + v}
            taIds.eachWithIndex {taId, i ->
                def tempta = TaskAssignment.get(taId)
                if(tempta){
                    tempta?.enabled = (enableds[i] == 'enabled') ? true : false
                    if(approver1[i] != 'null'){
                        if(!tempta?.taskAssignmentApprovals.find{
                                it.user.id as String == approver1[i]
                            }){
                            def taa = new TaskAssignmentApproval()
                            taa.user = User.get(approver1[i])
                            tempta.addToTaskAssignmentApprovals(taa)
                        }
                    }
                    if(approver2[i] != 'null'){
                        if(!tempta?.taskAssignmentApprovals.find{
                                it.user.id as String == approver2[i]
                            }){
                            def taa = new TaskAssignmentApproval()
                            taa.user = User.get(approver2[i])
                            tempta.addToTaskAssignmentApprovals(taa)
                        }
                    }
                    tempta.save()
                }
            }

            //new task assignemnts
            def tasks = [params["taskAssignment.task.id"]].flatten()
            def chargeCodes = [params["taskAssignment.chargeCode.id"]].flatten()
            def laborCategories = [params["taskAssignment.laborCategory.id"]].flatten()
            def enabled = [params["taskAssignment.enabled"]].flatten()

            tasks.eachWithIndex() { task, i ->
                if(task != 'null'){
                    def ta = new TaskAssignment()
                    ta.task = Task.get(task)
                    ta.chargeCode = ChargeCode.get(chargeCodes[i])
                    ta.laborCategory = LaborCategory.get(laborCategories[i])
                    ta.enabled = (enabled[i] == 'enabled') ? true : false
                    userInstance.addToTaskAssignments(ta)
                }
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
    def searchAJAX = {
        def users = User.findAllByUserRealNameLike("%${params.query}%")

        //Create XML response
        render(contentType: "text/xml") {
            results() {
                users.each { user ->
                    result() {
                        name(user.userRealName)
                        //Optional id which will be available in onItemSelect id(person.id)
                        id(user.id.toLong())
                    }
                }
            }
        }
    }
}
