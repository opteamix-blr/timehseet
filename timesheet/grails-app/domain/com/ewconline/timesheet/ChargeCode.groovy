package com.ewconline.timesheet.domain

class ChargeCode {

    String chargeNumber
    String descirption
    
    static constraints = {
        chargeNumber(blank:false, maxSize:100)
        description(nullable:true, maxSize:1000)
    }
}
