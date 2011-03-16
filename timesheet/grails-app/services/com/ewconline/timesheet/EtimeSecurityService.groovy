package com.ewconline.timesheet
import java.util.List
class EtimeSecurityService {
    static String SELF_ROLE = 'self_role'
    static String ACCOUNTANT_ROLE = 'accountant_role'
    static String APPROVER_ROLE = 'approver_role'
    static String ADMIN_ROLE = 'administrator_role'

    def boolean isUserInRole(String username, String[] rolesText) {
        def user = User.findByUsername(username)
        if (user) {
            Role role = null
            for (rt in rolesText) {
                role = Role.findByAuthority(rt)
                if (user.authorities.contains(role)) {
                    return true
                }
            }
            return false
        }
        return false
    }
}
