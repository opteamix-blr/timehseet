package com.ewconline.timesheet

class Task {

    String name 
    String description
	
	static hasMany = [chargeCodes: ChargeCode, laborCategories: LaborCategory ]
	
	//static belongsTo = [user:User]
	
    static constraints = {
        name(blank:false, maxSize:50)
        description(nullable:true, maxSize:1000)
	}

}
