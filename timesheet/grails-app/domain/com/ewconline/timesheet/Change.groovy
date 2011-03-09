package com.ewconline.timesheet

class Change {

    String domainModelName
    String previousValue
    String newValue
    String userName
    int referenceId
    String fieldName

    Date dateCreated


    static constraints = {
        domainModelName(blank:false)
        fieldName(blank:false)
        previousValue(nullable:true)
        newValue(blank:false)
        userName(nullable:true)
        referenceId(blank:false)
    }
}
