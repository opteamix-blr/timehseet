<% 
// TODO based on authentication or not.
// if already authenticated just go to main page
//
//<g:if test="${name == 'fred'}">
//	 Hello ${name}!
//</g:if>

 %>
${response.sendRedirect("access/login")}
