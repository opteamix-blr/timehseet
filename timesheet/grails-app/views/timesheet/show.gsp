
<%@ page import="com.ewconline.timesheet.Timesheet" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'timesheet.label', default: 'Timesheet')}" />
        <export:resource/>
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
        	<span class="menuButton"><a class="home" href="${createLink(uri: '/timesheet/listTimesheets')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            <span class="menuButton">Current status: ${timesheetInstance.currentState}</span>            
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
                            <td valign="top" class="name"><g:message code="timesheet.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value"><g:link action="viewTimesheet" id="${timesheetInstance.id}">${fieldValue(bean: timesheetInstance, field: "id")} (VIEW)</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="timesheet.startDate.label" default="Start Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="MMM-dd-yyyy" date="${timesheetInstance?.startDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="timesheet.endDate.label" default="End Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="MMM-dd-yyyy" date="${timesheetInstance?.endDate}"/></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="timesheet.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="MMM-dd-yyyy hh:mm:ss a" date="${timesheetInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="timesheet.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="MMM-dd-yyyy hh:mm:ss a" date="${timesheetInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="timesheet.timesheetEntries.label" default="Timesheet Entries" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <table>
                                    <tr>
                                        <th>Task</th>
                                        <th>Charge Number</th>
                                        <th>Labor Category</th>
                                        
                                    </tr>
                                    <g:each in="${timesheetInstance.getSortedTimesheetEntries()}" var="t">
                                    <tr>
                                        <td>${t?.taskAssignment?.task?.name.encodeAsHTML()}</td>
                                        <td>${t?.taskAssignment?.chargeCode?.chargeNumber?.encodeAsHTML()}</td>
                                        <td>${t?.taskAssignment?.laborCategory?.name?.encodeAsHTML()}</td>
                                        
                                    </tr>
                                    </g:each>
                                </table>
                        
                            </td>
                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Total Hours</td>
                            
                            <td valign="top" class="value">${timesheetInstance?.sumAllHours()}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${timesheetInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="sign" action="signform" value="${message(code: 'default.button.sign.label', default: 'Sign')}" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
