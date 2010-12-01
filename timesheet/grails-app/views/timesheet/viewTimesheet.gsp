<%@ page import="com.ewconline.timesheet.Timesheet" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'timesheet.label', default: 'Timesheet')}" />
        <title><g:message code="View" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/timesheet/listTimesheets')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton">Current status: ${timesheetInstance.currentState}</span>
        </div>
        <div class="body">
            <h1><g:message code="View" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${timesheetInstance}">
            <div class="errors">
                <g:renderErrors bean="${timesheetInstance}" as="list" />
            </div>
            </g:hasErrors>
            
                <g:hiddenField name="id" value="${timesheetInstance?.id}" />
                <g:hiddenField name="version" value="${timesheetInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                           <tr class="prop">
                                <td valign="top" class="name">
                                  <label for=userRealName><g:message code="timesheet.userRealName.label" default="Employee" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timesheetInstance, field: 'user.userRealName', 'errors')}">
                                    ${timesheetInstance?.user?.userRealName}
                                </td>
                            </tr>
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
							          <g:hiddenField name="totalEntries" value="${timesheetInstance?.timesheetEntries.size()}" />
							    <g:each status="i" in="${timesheetInstance?.timesheetEntries}" var="timesheetEntry" >
							          <tr>
							            <td><g:hiddenField name="timesheetEntries" value="${timesheetEntry?.id}" />${timesheetEntry?.taskAssignment?.task.name} </td>
							            <td>${timesheetEntry?.taskAssignment?.laborCategory.name}</td>
							            <td><g:hiddenField name="chargeCode${i}" value="${timesheetEntry?.taskAssignment?.chargeCode.chargeNumber}" />${timesheetEntry?.taskAssignment?.chargeCode.chargeNumber}</td>
							            <g:each status="j" in="${timesheetEntry?.workdays}" var="wd">
							             <td>${wd.hoursWorked}</td>
							            </g:each>
							            <td><p></p></td>
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
									<div class="buttons">
									<g:form>
										<g:hiddenField name="id" value="${timesheetInstance?.id}" />
										<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
										<span class="button"><g:actionSubmit class="sign" action="signform" value="${message(code: 'default.button.sign.label', default: 'Sign')}" /></span>
									</g:form>
								</div>
							    </div>
                            </tr>
                        </tbody>
                    </table>
					
                </div>
				
        </div>
		
    </body>
</html>