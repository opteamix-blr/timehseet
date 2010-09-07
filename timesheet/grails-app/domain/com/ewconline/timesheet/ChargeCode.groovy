package com.ewconline.timesheet

class ChargeCode {

    String chargeNumber
    String description
    
    static constraints = {
        chargeNumber(blank:false, maxSize:100)
        description(nullable:true, maxSize:1000)
    }
}
