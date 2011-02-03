
class SecurityFilters {
	def filters = {
		loginCheck(controller:'*', action:'*') {
			before = {
				println "controller = ${params.controller} action=${params.action}"
				if (params.controller == null) {
					redirect(controller:'access', action:'login')
					
					/* When using Tomcat and the controller is null you must return true.
					 * Otherwise an exception is thrown.
					 */
					return true
				} else if(params.controller=='access' && params.action=='authenticate') {
					return true
				} else if(!session.user && !actionName.equals('login')) {
					redirect(controller:'access', action:'login') 
					return false
				}
			}
		}
	}
}
