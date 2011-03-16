package com.ewconline.timesheet

class AuditingService {

    static transactional = false
    static scope = "session"

    def currentUserName

    def getCurrentUserName() {
        return currentUserName
    }

    def setCurrentUserName(userName){
        this.currentUserName = userName
    }
}
