

<%@ page import="com.ewconline.timesheet.TaskAssignment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <resource:autoComplete skin="default" />
        <g:set var="entityName" value="${message(code: 'taskAssignment.label', default: 'TaskAssignment')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <g:javascript library="prototype"/>
        <g:javascript library="taskAssignment"/>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${taskAssignmentInstance}">
            <div class="errors">
                <g:renderErrors bean="${taskAssignmentInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                         
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="displayName"><g:message code="taskAssignment.displayName.label" default="Display Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: taskAssignmentInstance, field: 'displayName', 'errors')}">
                                    <g:textField name="displayName" value="${taskAssignmentInstance?.displayName}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled"><g:message code="taskAssignment.enabled.label" default="Enabled" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: taskAssignmentInstance, field: 'enabled', 'errors')}">
                                    <g:checkBox name="enabled" value="${taskAssignmentInstance?.enabled}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="task"><g:message code="taskAssignment.task.label" default="Task" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: taskAssignmentInstance, field: 'task.id', 'errors')}">
                                    <g:select optionValue="name"
                                              name="task.id"
                                              from="${com.ewconline.timesheet.Task.list()}"
                                              optionKey="id"
                                              value="${taskAssignmentInstance?.task?.id}"
                                              noSelection="${['null':'Select One...']}"/>
                                </td>
                            </tr>
                                                
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="chargeCode"><g:message code="taskAssignment.chargeCode.label" default="Charge Code" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: taskAssignmentInstance, field: 'chargeCode.id', 'errors')}">
                                    <div id="chargeCodeSelect"></div>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="laborCategory"><g:message code="taskAssignment.laborCategory.label" default="Labor Category" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: taskAssignmentInstance, field: 'laborCategory.id', 'errors')}">
                                  <div id="laborCategorySelect"></div>
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="laborIdReference"><g:message code="taskAssignment.laborIdReference.label" default="Labor ID Reference" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: taskAssignmentInstance, field: 'laborIdReference.id', 'errors')}">
                                    <g:textField name="laborIdReference" value="${taskAssignmentInstance?.laborIdReference}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="user"><g:message code="taskAssignment.user.label" default="Employee" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: taskAssignmentInstance, field: 'user.userRealName', 'errors')}">
                                    <richui:autoComplete name="user.userRealName" action="${createLinkTo('dir': 'user/searchAJAX')}" value="${taskAssignmentInstance?.user?.userRealName}" />                                    
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="notes"><g:message code="taskAssignment.notes.label" default="Notes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: taskAssignmentInstance, field: 'notes', 'errors')}">
                                    <g:textArea name="notes" value="${taskAssignmentInstance?.notes}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
