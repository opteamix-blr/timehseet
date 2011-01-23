package com.ewconline.timesheet

class ChargeCode {
	Task task
    String chargeNumber
	String displayName
    String description
    
    static constraints = {
        chargeNumber(blank:false, maxSize:100)
		displayName(nullable:true, maxSize:50)
        description(nullable:true, maxSize:1000)
    }
	static belongsTo = Task

}
