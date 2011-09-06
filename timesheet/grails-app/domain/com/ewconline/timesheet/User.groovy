package com.ewconline.timesheet

import com.ewconline.timesheet.Role
import com.ewconline.timesheet.TaskAssignment

/**
 * User domain class.
 */
class User {
    static transients = ['pass', 'userRealName']
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
    String firstName
    String lastName
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
    public String getUserRealName(){
        return lastName + ", " + firstName
    }
    static constraints = {
        username(blank: false, unique: true)
        userRealName(nullable: true)
        firstName(nullable: true)
        lastName(nullable: true)
        passwd(nullable:true)
        enabled()
        guid(blank: false, unique: true)
        employeeId(blank:false)
    }
}
