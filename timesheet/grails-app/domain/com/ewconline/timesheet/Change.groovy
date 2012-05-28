package com.ewconline.timesheet

class Change {

    String domainModelName
    String previousValue
    String newValue
    String userName
    int referenceId
    String fieldName

    Date dateCreated

    static mapping = {
        table 'ETChange'
    }

    static constraints = {
        domainModelName(blank:false)
        fieldName(blank:false)
        previousValue(nullable:true, maxSize:2500)
        newValue(blank:false, maxSize:2500)
        userName(nullable:true)
        referenceId(blank:false)
    }
}
