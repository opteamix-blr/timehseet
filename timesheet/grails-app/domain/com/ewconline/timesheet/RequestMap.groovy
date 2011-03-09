package com.ewconline.timesheet

/**
 * Request Map domain class.
 */
class RequestMap extends AuditableObject{

	String url
	String configAttribute

	static constraints = {
		url(blank: false, unique: true)
		configAttribute(blank: false)
	}
}
