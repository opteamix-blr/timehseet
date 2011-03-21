package com.ewconline.timesheet

class TaskAssignmentApproval {
    User user
    TaskAssignment taskAssignment
    
    static constraints = {
        user()
        taskAssignment()
    }
    static belongsTo = [user:User, taskAssignment:TaskAssignment]
}
