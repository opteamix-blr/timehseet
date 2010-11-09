package com.ewconline.timesheet

class SignatureService {

    static transactional = true
	static hasher = new RabinHashFunction()
	
    def signTimesheet(ts, user) {
		ts.signature = hasher.hash(user.email)
		log.debug String.valueOf(ts.signature)
    }
	
	
}
