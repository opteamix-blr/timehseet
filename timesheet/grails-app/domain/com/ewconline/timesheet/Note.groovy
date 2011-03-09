package com.ewconline.timesheet

class Note extends AuditableObject{

	Date dateCreated
	String noteType
	String comment
	Float oldValue
	Float newValue

    String toString(){
        return comment
    }
    static constraints = {
		comment(nullable:false, blank: false)
    }
}
