package com.ewconline.timesheet

class TaskAssignment extends AuditableObject{
    boolean enabled = true
    Task task
    LaborCategory laborCategory
    ChargeCode chargeCode
    
    static constraints = {
        enabled()
        task()
        laborCategory()
        chargeCode()
    }
    static belongsTo = [user:User]
    static hasMany = [taskAssignmentApprovals:TaskAssignmentApproval]
}
