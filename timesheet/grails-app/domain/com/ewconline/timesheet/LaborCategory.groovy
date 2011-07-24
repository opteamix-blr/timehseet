package com.ewconline.timesheet

class LaborCategory extends AuditableObject{
    String name
    String description
    
    static constraints = {
        name(blank:false, maxSize:50)
        description(nullable:true, maxSize:1000)
    }
}
