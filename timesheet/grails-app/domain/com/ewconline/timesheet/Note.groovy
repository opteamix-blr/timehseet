package com.ewconline.timesheet

class Note {

	Date dateCreated
	String comment
	
    static constraints = {
		comment(blank: false)
    }
}
