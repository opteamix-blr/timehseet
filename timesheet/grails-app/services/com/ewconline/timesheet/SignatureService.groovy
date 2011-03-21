package com.ewconline.timesheet

class SignatureService {

    static transactional = true
    static Hash hasher = new RabinHashFunction()

    def signTimesheet(ts, user) {
        if(user.guid){
            ts.signature = hasher.hash(user.guid)
        }else{
            ts.signature = hasher.hash(user.email)
        }
        log.debug "The hash for user " + user.email + " is " + String.valueOf(ts.signature)
    }


}
