package com.ewconline.timesheet

class Note {

	Date dateCreated
	String noteType
	String comment
	Float oldValue
	Float newValue
	
    static constraints = {
		comment(nullable:false, blank: false)
    }
}
