package com.ewconline.timesheet
import org.codehaus.groovy.runtime.DefaultGroovyMethods
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
        

		
        // validate user exists
        def user = null
        def userRealName = null
        if (params?.user?.id) {
            user = User.get(params?.user.id)
            userRealName = user.userRealName
        }
        if (!user) {
            flash.message = "Employee '${params?.user.userRealName}' does not exist. Check spelling"
            render(view: "create", model: [taskAssignmentInstance: taskAssignmentInstance])
            return
        }
        taskAssignmentInstance.properties = params
        taskAssignmentInstance.user = user
        taskAssignmentInstance.user.userRealName = userRealName
	
        def chargeCode = ChargeCode.get(params?.chargeCode.id)
        def laborCategory = LaborCategory.get(params?.laborCategory.id)
        def task = Task.get(params?.task.id)
		
        taskAssignmentInstance.task = task
        taskAssignmentInstance.chargeCode = chargeCode
        taskAssignmentInstance.laborCategory = laborCategory
		
        if (taskAssignmentInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'taskAssignment.label', default: 'TaskAssignment'), taskAssignmentInstance.id])}"
            redirect(action: "show", id: taskAssignmentInstance.id)
        } else {
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
        } else {
            def approvers = taskAssignmentInstance.taskAssignmentApprovals.collect {
                taa ->
                taa.user
            }
            return [taskAssignmentInstance: taskAssignmentInstance, approvers: approvers]
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


            // validate user exists
            def user = null
            def userRealName = null
            if (params?.user?.id) {
                user = User.get(params?.user.id)
                userRealName = user.userRealName
            } else {

            }

            if (!user) {
                def copyTaskAssignmentInstance = new TaskAssignment()
                copyTaskAssignmentInstance.properties = params
                copyTaskAssignmentInstance.id = taskAssignmentInstance.id
                flash.message = "Employee '${params?.user.userRealName}' does not exist. Check spelling"
                render(view: "edit", model: [taskAssignmentInstance: copyTaskAssignmentInstance])
                return
            }
            // This takes params and will update nested objects like user.
            taskAssignmentInstance.properties = params
            taskAssignmentInstance.user = user
            user.userRealName = userRealName
            
            // handle all approvers
            def oldApprovers = []
            taskAssignmentInstance.taskAssignmentApprovals.each {
                taa ->
                oldApprovers.add(taa.user.id)
            }

            def newApprovers = []
            params.approvers.each { approverId ->
                println(" approvers user id = ${approverId}")
                def approverUser = User.get(approverId)
                if (approverUser) {
                    if (!newApprovers.contains(approverUser.id)) {
                        newApprovers.add(approverUser.id)
                    }
                }
            }
            def userIdsToAdd = newApprovers - oldApprovers
            def userIdsToDel = oldApprovers - newApprovers

            // delete
            def removeList = []
            taskAssignmentInstance.taskAssignmentApprovals.each {
                taa ->
                userIdsToDel.each {
                    userId ->
                    if (taa.user.id == userId) {
                        removeList.add(taa)
                        taa.delete()
                    }
                }
            }
            taskAssignmentInstance.taskAssignmentApprovals.removeAll(removeList)
            // add
            def addList = []
            userIdsToAdd.each {
                userId ->
                def addUser = User.get(userId)
                def taskAssignApp = new TaskAssignmentApproval(user:addUser, taskAssignment: taskAssignmentInstance)
                taskAssignmentInstance.taskAssignmentApprovals.add(taskAssignApp)
            }

            //taskAssignmentInstance.taskAssignmentApprovals.addAll(addList)
            taskAssignmentInstance.taskAssignmentApprovals.removeAll(removeList)
            taskAssignmentInstance.taskAssignmentApprovals.each {
                taa ->
                println( "Setting each taa = ${taa.user.id} ${taa.user.userRealName}")
            }

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
