package com.ewconline.timesheet

class TaskAssignment extends AuditableObject{
//	String displayName
//	String notes
	boolean enabled = true
	Task task
        LaborCategory laborCategory
	ChargeCode chargeCode
//	String laborIdReference
//	User user
//
//    String toString(){
//        return displayName
//    }
	
    
    static constraints = {
//		displayName(blank:false, maxSize:50)
//		notes(nullable:true, maxSize:1000)
		enabled()
                task()
		laborCategory()
		chargeCode()
//		laborIdReference(nullable:true)
		user()
    }
    static belongsTo = [user:User]
    static hasMany = [taskAssignmentApprovals:TaskAssignmentApproval]
}
