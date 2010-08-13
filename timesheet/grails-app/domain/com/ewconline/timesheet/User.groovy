package com.ewconline.timesheet

import com.ewconline.timesheet.Role

/**
 * User domain class.
 */
class User {
	static transients = ['pass']
	static hasMany = [authorities: Role]
	static belongsTo = [Role]

	/** Username */
	String username
	/** User Real Name*/
	String userRealName
	/** MD5 Password */
	String passwd
	/** enabled */
	boolean enabled

	String email
	boolean emailShow

	/** description */
	String description = ''

	/** plain password to create a MD5 password */
	String pass = '[secret]'

        Timesheet timesheets

	static constraints = {
		username(blank: false, unique: true)
		userRealName(blank: false)
		passwd(nullable:true)
		enabled()
	}
}