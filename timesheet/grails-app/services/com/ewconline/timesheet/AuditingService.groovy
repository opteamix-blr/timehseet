package com.ewconline.timesheet

class AuditingService {

    static transactional = false
    static scope = "session"

    def userName

    def currentUserName() {
        return userName
    }

    def setCurrentUser(userName){
        userName = userName
    }
}
