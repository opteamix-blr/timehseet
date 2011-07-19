
<%@ page import="com.ewconline.timesheet.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'user/list')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.username.label" default="Username" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "username")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.userRealName.label" default="User Real Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "userRealName")}</td>
                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.enabled.label" default="Enabled" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${userInstance?.enabled}" /></td>
                            
                        </tr>

                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.email.label" default="Email" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "email")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.emailShow.label" default="Email Show" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${userInstance?.emailShow}" /></td>
                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.authorities.label" default="Authorities" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${userInstance.authorities}" var="a">
                                    <li><g:link controller="role" action="show" id="${a.id}">${a?.authority?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.taskAssignments.label" default="TaskAssignments" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                              <table>
                                <thead>
                                  <tr>
                                    <th>Task</th>
                                    <th>Charge Code</th>
                                    <th>Labor Category</th>
                                    <th>Enabled</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  <g:each in="${userInstance.taskAssignments}" var="t">
                                    <tr>
                                      <td>
                                        <g:link controller="taskAssignment" action="show" id="${t.id}">${t?.task}</g:link>
                                      </td>
                                      <td>
                                        ${t.chargeCode}
                                      </td>
                                      <td>
                                        ${t.laborCategory.name}
                                      </td>
                                      <td>
                                        ${t.enabled ? "enabled" : "disabled"}
                                      </td>
                                    </tr>
                                  </g:each>
                                </tbody>
                              </table>
                            </td>
                            
                        </tr>
                      
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${userInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
