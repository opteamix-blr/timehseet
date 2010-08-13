package com.ewconline.timesheet.domain

import com.ewconline.timesheet.domain.User

/**
 * Authority domain class.
 */
class Role {

	static hasMany = [people: User]

	/** description */
	String description
	/** ROLE String */
	String authority

	static constraints = {
		authority(blank: false, unique: true)
		description()
	}
}
