package com.ewconline.timesheet

class Task {

    String name
    String description
	LaborCategory laborCategory
	ChargeCode chargeCode
    
	static belongsTo = [user:User]
	
    static constraints = {
        name(blank:false, maxSize:50)
        description(nullable:true, maxSize:1000)
		laborCategory()
		chargeCode()
    }

}
