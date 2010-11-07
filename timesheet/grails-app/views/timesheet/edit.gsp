

<%@ page import="com.ewconline.timesheet.Timesheet" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'timesheet.label', default: 'Timesheet')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/timesheet/listTimesheets')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            <g:if test="${timesheetInstance.currentState == ''}">
                <span class="menuButton"><g:link class="approver_open" controller="approver" action="userApprove" id="${timesheetInstance?.id}">Approve</g:link></span>
            </g:if>
            <g:if test="${timesheetInstance.currentState == 'pending'}">
                <span class="menuButton"><g:link class="approver_pending" >Pending</g:link></span>
            </g:if>
            <g:if test="${timesheetInstance.currentState == 'approved'}">
                <span class="menuButton"><g:link class="approver_approved" controller="approver" action="accountantApprove">Approved</g:link></span>
            </g:if>
            <span class="menuButton">Current status: ${timesheetInstance.currentState}</span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${timesheetInstance}">
            <div class="errors">
                <g:renderErrors bean="${timesheetInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="update" method="post">
                <g:hiddenField name="id" value="${timesheetInstance?.id}" />
                <g:hiddenField name="version" value="${timesheetInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="startDate"><g:message code="timesheet.startDate.label" default="Start Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timesheetInstance, field: 'startDate', 'errors')}">
                                    ${timesheetInstance?.startDate}
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endDate"><g:message code="timesheet.endDate.label" default="End Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timesheetInstance, field: 'endDate', 'errors')}">
                                    ${timesheetInstance?.endDate}
                                </td>
                            </tr>
                            <tr class="prop">
                                <div class="dialog">
							        <table>
							          <tr>
							            <th>Task</th>
							            <th>Labor Category</th>
							            <th>Charge #</th>
							            <th>Sat</th>
							            <th>Sun</th>
							            <th>Mon</th>
							            <th>Tue</th>
							            <th>Wed</th>
							            <th>Thu</th>
							            <th>Fri</th>
							            <th>Total</th>
							          </tr>
							    <g:each status="i" in="${timesheetInstance?.timesheetEntries}" var="timesheetEntry">
							          <tr>
							            <td><g:hiddenField name="timesheetEntries" value="${timesheetEntry?.id}" />${timesheetEntry?.taskAssignment?.task.name} </td>
							            <td>${timesheetEntry?.taskAssignment?.laborCategory.name}</td>
							            <td><g:hiddenField name="chargeCode${i}" value="${timesheetEntry?.taskAssignment?.chargeCode.chargeNumber}" />${timesheetEntry?.taskAssignment?.chargeCode.chargeNumber}</td>
							            <g:each status="j" in="${timesheetEntry?.workdays}" var="wd">
							             <td><g:textField size="5" name="day${j+1}_${timesheetEntry?.taskAssignment?.chargeCode.id}" value="${wd.hoursWorked}"></g:textField></td>
							            </g:each>
							            <td><gt:timesheetEntryTotal days="${timesheetEntry.workdays}"/></td>
							          </tr>
							            
							    </g:each>
							          <tr>
							            <td></td>
							            <td></td>
							            <th>Total: </th>
							            <td><div></div></td>
							            <td><div></div></td>
							            <td><div></div></td>
							            <td><div></div></td>
							            <td><div></div></td>
							            <td><div></div></td>
							            <td><div></div></td>
							            <td><div></div></td>
							          </tr>
							        </table>
							    </div>
							    <div class="buttons">
				                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
									<span class="button"><g:actionSubmit class="sign" action="signForm" value="${message(code: 'default.button.sign.label', default: 'Sign')}" /></span>
				                    <span class="button"><g:link action="listTimesheets" id="${timesheetInstance.id}">Cancel</g:link></span>
				                </div>
                            </tr>
                        </tbody>
                    </table>
                </div>

            </g:form>
        </div>
    </body>
</html>
