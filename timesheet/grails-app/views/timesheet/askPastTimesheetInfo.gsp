
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        
        <resource:dateChooser />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'timesheet/listTimesheets')}"><g:message code="default.home.label"/></a></span>
        </div>
        <div class="body">
            <h1>Create a Past Time sheet</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
            <g:form action="createPastTimesheet" method="post">
                <table>
                    <tr>
                      <th colspan="5">Date within week</th>
                    </tr>
                    <tr>
                      <td colspan="5">
                        <richui:dateChooser firstDayOfWeek="Sa" name="dateOnWeek" id="dateOnWeek" format="MM/dd/yyyy"/>
                      </td>
                    </tr>
                    <tr>
                      <th>Select </th>
                      <th>Task</th>
                      <th>Labor Category</th>
                      <th>Charge Code#</th>
                      <th>Enabled</th>
                    </tr>
                    <!-- Loop through employee's task assignments-->
                    <g:each in="${userInstance.findActiveTaskAssignments()}" status="i" var="taskAssignment">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                  <td><g:checkBox name="taskAssignmentIds" value="${taskAssignment.id}" /></td>
					<td>${taskAssignment?.task?.name}</td>
					<td>${taskAssignment?.laborCategory?.name}</td>
					<td>${taskAssignment?.chargeCode?.chargeNumber}</td>
					<td>${taskAssignment?.enabled}</td>
				</tr>
			</g:each>
                    <tr>
                      <td colspan="5"><span class="button"><g:actionSubmit class="create" action="createPastTimesheet" value="Create" /></span></td>
                    </tr>
                </table>
            </g:form>
            </div>
            
        </div>
    </body>
</html>
