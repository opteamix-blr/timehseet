package com.ewconline.timesheet

class Task {

    String name
	String contractInfo1
	String contractInfo2
    String description
	
	static hasMany = [chargeCodes: ChargeCode, laborCategories: LaborCategory ]
	
    static constraints = {
        name(blank:false, maxSize:50)
		contractInfo1(nullable:true,maxSize:50)
		contractInfo2(nullable:true,maxSize:50)
        description(nullable:true, maxSize:1000)
	}

}
