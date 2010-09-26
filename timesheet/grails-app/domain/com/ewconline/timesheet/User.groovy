package com.ewconline.timesheet

import com.ewconline.timesheet.Role
import com.ewconline.timesheet.TaskAssignment

/**
 * User domain class.
 */
class User {
	static transients = ['pass']
	static hasMany = [authorities: Role, timesheets: Timesheet, taskAssignments: TaskAssignment ]
	static belongsTo = [Role]

	/** Username */
	String username
	/** User Real Name*/
	String userRealName
	/** MD5 Password */
	String passwd
	/** enabled */
	boolean enabled

	String email = ''
	boolean emailShow

	/** description */
	String description = ''

	/** plain password to create a MD5 password */
	String pass = '[secret]'

	static constraints = {
		username(blank: false, unique: true)
		userRealName(nullable: true)
		passwd(nullable:true)
		enabled()
	}
}
