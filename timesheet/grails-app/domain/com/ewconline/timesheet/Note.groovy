package com.ewconline.timesheet

class Note {

	Date dateCreated
	String noteType
	String comment
	
    static constraints = {
		comment(blank: false)
    }
}
