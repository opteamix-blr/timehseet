package com.ewconline.timesheet

import com.ewconline.timesheet.Role
import com.ewconline.timesheet.TaskAssignment

/**
 * User domain class.
 */
class User {
    static transients = ['pass']
    static hasMany = [authorities: Role,
        timesheets: Timesheet,
        taskAssignments: TaskAssignment,
        taskAssignmentsApprovals:TaskAssignmentApproval ]
    static belongsTo = [Role]

    static mapping = {
        table 'ETUser'
    }


    /** Username */
    String username
    /** User Real Name*/
    String userRealName
    /** MD5 Password */
    String passwd
    /** enabled */
    boolean enabled = true

    String employeeId = "-1"

    String email = ''
    boolean emailShow

    /** description */
    String description = ''

    /** plain password to create a MD5 password */
    String pass = '[secret]'

    String guid

    String toString(){
        return username
    }

    static constraints = {
        username(blank: false, unique: true)
        userRealName(nullable: true)
        passwd(nullable:true)
        enabled()
        guid(blank: false, unique: true)
        employeeId(blank:false)
    }
}
