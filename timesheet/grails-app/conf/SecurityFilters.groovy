import com.ewconline.timesheet.User;
class SecurityFilters {
    def etimeSecurityService
    static accountantControllers = ['reports', 'user', 'role', 'taskAssignment', 'task', 'chargeCode', 'laborCategory']
    def filters = {
        loginCheck(controller:'*', action:'*') {
            before = {
                //println "controller = ${params.controller} action=${params.action}"
                if (params.controller == null) {
                    redirect(controller:'access', action:'login')
					
                    /* When using Tomcat and the controller is null you must return true.
                     * Otherwise an exception is thrown.
                     */
                    return true
                }

                if(params.controller=='access' && params.action=='authenticate') {
                    return true
                }

                if(!session.user && !actionName.equals('login')) {
                    redirect(controller:'access', action:'login')
                    return false
                }
                                

                // secure approver area
                if (params.controller=='approver') {
                    def approverRoles = [etimeSecurityService.ACCOUNTANT_ROLE, etimeSecurityService.APPROVER_ROLE]
                    def username = session?.user?.username
                    def hasAccess = etimeSecurityService.isUserInRole(username , approverRoles )
                    //println "${username} has approver access : ${hasAccess}"
                    if (!hasAccess) {
                        redirect(controller:'access', action:'accessDenied')
                        return hasAccess
                    }
                    return hasAccess
                }

                // accountants area only
                if (accountantControllers.contains(params.controller)) {
                    def acctRoles = [etimeSecurityService.ACCOUNTANT_ROLE]
                    def hasAccess = etimeSecurityService.isUserInRole(session?.user?.username , acctRoles )
                    if (!hasAccess) {
                        redirect(controller:'access', action:'accessDenied')
                        return hasAccess
                    }
                    return hasAccess
                }


            }
        }
    }
}
