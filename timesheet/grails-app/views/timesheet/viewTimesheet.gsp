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
                                    <g:formatDate format="MMM-dd-yyyy" date="${timesheetInstance?.startDate}"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endDate"><g:message code="timesheet.endDate.label" default="End Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timesheetInstance, field: 'endDate', 'errors')}">
                                    <g:formatDate format="MMM-dd-yyyy" date="${timesheetInstance?.endDate}"/>
                                </td>
                            </tr>
                            <tr class="prop">
                                <div class="dialog">
							        <table>
							          <tr>
							            <th>Task</th>
							            <th>Labor Category</th>
							            <th>Charge #</th>
							            <th>Sat <br/> <g:formatDate format="dd" date="${timesheetInstance.startDate}"/></th>
							            <th>Sun <br/> <g:formatDate format="dd" date="${timesheetInstance.startDate + 1}"/></th>
							            <th>Mon <br/> <g:formatDate format="dd" date="${timesheetInstance.startDate + 2}"/></th>
							            <th>Tue <br/> <g:formatDate format="dd" date="${timesheetInstance.startDate + 3}"/></th>
							            <th>Wed <br/> <g:formatDate format="dd" date="${timesheetInstance.startDate + 4}"/></th>
							            <th>Thu <br/> <g:formatDate format="dd" date="${timesheetInstance.startDate + 5}"/></th>
							            <th>Fri <br/> <g:formatDate format="dd" date="${timesheetInstance.startDate + 6}"/></th>
							            <th>Total</th>
							          </tr>
							          <g:hiddenField name="totalEntries" value="${timesheetInstance?.timesheetEntries.size() }" />
							    <g:each status="i" in="${timesheetInstance?.timesheetEntries }" var="timesheetEntry">
							          <tr>
							            <td><g:hiddenField name="timesheetEntries" value="${timesheetEntry?.id}" />${timesheetEntry?.taskAssignment?.task.name} </td>
							            <td>${timesheetEntry?.taskAssignment?.laborCategory.name}</td>
							            <td><g:hiddenField name="chargeCode${i }" value="${timesheetEntry?.taskAssignment?.chargeCode.chargeNumber}" />${timesheetEntry?.taskAssignment?.chargeCode.chargeNumber}</td>
							            <g:each status="j" in="${timesheetEntry?.workdays}" var="wd">
							             <td>${wd.hoursWorked}</td>
							            </g:each>
							            <td><p>${timesheetEntry?.sumHours()}</p></td>
							          </tr>						           
							    </g:each>
							          <tr>
							            <td></td>
							            <td></td>
							            <th>Total: </th>
							            <td><div>${timesheetInstance.sumHoursByDay(1)}</div></td>
							            <td><div>${timesheetInstance.sumHoursByDay(2)}</div></td>
							            <td><div>${timesheetInstance.sumHoursByDay(3)}</div></td>
							            <td><div>${timesheetInstance.sumHoursByDay(4)}</div></td>
							            <td><div>${timesheetInstance.sumHoursByDay(5)}</div></td>
							            <td><div>${timesheetInstance.sumHoursByDay(6)}</div></td>
							            <td><div>${timesheetInstance.sumHoursByDay(7)}</div></td>
							            <td><div>${timesheetInstance.sumAllHours()}</div></td>
							          </tr>
							         
							        </table>
							        <g:if test="${timesheetInstance.hasModifications()}">    
							        <table>
							         <tr>
							            <th colspan="6">Modifications:</th>
							          </tr>	
							         <tr>
							            <th>Day</th>
							            <th>Labor Category</th>
							            <th>Last Modified</th>
							            <th>Previous Value</th>
							            <th>New Value</th>
							            <th width="40%">Reason</th>
							          </tr>	
							        <g:each status="i" in="${timesheetInstance?.timesheetEntries }" var="timesheetEntry"> 
							          <g:each status="j" in="${timesheetEntry?.workdays}" var="wd">
							              <g:each status="k" in="${wd?.notes}" var="note">
								           <tr>
								             <td><g:formatDate format="EEE" date="${wd.dateWorked}"/><br/>
								             <g:formatDate format="dd" date="${wd.dateWorked}"/>
								             </td>
								             <td>${timesheetEntry?.taskAssignment?.laborCategory.name}</td>
								             <td><g:formatDate format="MMM-dd-yyyy hh:mm:ss" date="${note?.dateCreated}"/></td>
								             <td>${note?.oldValue}</td>
								             <td>${note?.newValue}</td>
								             <td>${note.comment }</td>
								           </tr>
								            </g:each>
							            </g:each>	
							     </g:each>	
							        </table>
							        </g:if>
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
