
<%@ page import="com.ewconline.timesheet.TaskAssignment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'taskAssignment.label', default: 'TaskAssignment')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
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
                            <td valign="top" class="name"><g:message code="taskAssignment.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: taskAssignmentInstance, field: "id")}</td>
                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="taskAssignment.displayName.label" default="Display Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: taskAssignmentInstance, field: "displayName")}</td>
                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="taskAssignment.enabled.label" default="Enabled" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${taskAssignmentInstance?.enabled}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="taskAssignment.task.label" default="Task" /></td>
                            
                            <td valign="top" class="value"><g:link controller="task" action="show" id="${taskAssignmentInstance?.task?.id}">${taskAssignmentInstance?.task?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="taskAssignment.chargeCode.label" default="Charge Code" /></td>
                            
                            <td valign="top" class="value"><g:link controller="chargeCode" action="show" id="${taskAssignmentInstance?.chargeCode?.id}">${taskAssignmentInstance?.chargeCode?.chargeNumber?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="taskAssignment.laborCategory.label" default="Labor Category" /></td>
                            
                            <td valign="top" class="value"><g:link controller="laborCategory" action="show" id="${taskAssignmentInstance?.laborCategory?.id}">${taskAssignmentInstance?.laborCategory?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="taskAssignment.laborIdReference.label" default="Labor ID Reference" /></td>
                            
                            <td valign="top" class="value"><g:link controller="laborIdReference" action="show" id="${taskAssignmentInstance?.laborIdReference?.id}">${taskAssignmentInstance?.laborIdReference?.name?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                   
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="taskAssignment.notes.label" default="Notes" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: taskAssignmentInstance, field: "notes")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${taskAssignmentInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
