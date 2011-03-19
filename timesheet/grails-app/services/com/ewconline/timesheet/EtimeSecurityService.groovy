package com.ewconline.timesheet
import java.util.List
class EtimeSecurityService {
    static String SELF_ROLE = 'self_role'
    static String ACCOUNTANT_ROLE = 'accountant_role'
    static String APPROVER_ROLE = 'approver_role'
    static String ADMIN_ROLE = 'administrator_role'

    def boolean isUserInRole(username,  rolesText) {
        def user = User.findByUsername(username)
        if (user) {
            def userRoles = []
            for (role in user.authorities) {
                userRoles.add(role.authority)
            }

            for (rt in rolesText) {
                if (userRoles.contains(rt)) {
                    return true
                }
            }
            return false
        }
        return false
    }
}
