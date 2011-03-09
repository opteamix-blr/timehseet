package com.ewconline.timesheet

class LaborCategory extends AuditableObject{
	Task task
	String name
    String description
    
    static constraints = {
        name(blank:false, maxSize:50)
        description(nullable:true, maxSize:1000)
    }
	static belongsTo = Task
}
