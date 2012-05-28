package com.ewconline.timesheet

class MessageOfTheDay {

    String message
    Date expirationDate

    static constraints = {
        message(maxSize:2500, blank:false)
        expirationDate()
    }
}
