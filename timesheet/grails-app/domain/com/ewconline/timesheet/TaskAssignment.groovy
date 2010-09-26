package com.ewconline.timesheet

class TaskAssignment {
	String displayName
	String notes
	boolean enabled = true
	Task task
    LaborCategory laborCategory
	ChargeCode chargeCode
    
    static constraints = {
		displayName(blank:false, maxSize:50)
		notes(nullable:true, maxSize:1000)
		enabled()
        task()
		laborCategory()
		chargeCode()
    }

}
