<head>
    
	<meta name="layout" content="main" />
	<title>Create a Timesheet</title>
</head>
<body>

	<div class="nav">
		<span class="menuButton"><a class="home" href="${createLinkTo(dir:'timesheet/listTimesheets')}">Home</a></span>
		<span class="menuButton"><g:link class="create" action="create">Add Timesheet Entry</g:link></span>
		<span class="menuButton"><g:link class="menuButton" action="listTimesheets">Cancel</g:link></span>
		<span class="menuButton">Current status: ${timesheetInstance.currentState}</span>
	</div>

	<div class="body">
		<h1>Create a Timesheet</h1>
		<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
		</g:if>
		<g:hasErrors bean="${timesheetInstance}">
            <div class="errors">
                <g:renderErrors bean="${timesheetInstance}" as="list" />
            </div>
        </g:hasErrors>
		<g:form action="save" method="post" >
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
             <td><g:textField size="5" name="day${j+1}_${timesheetEntry?.taskAssignment?.chargeCode.id}" value=""></g:textField></td>
            </g:each>
            <td></td>
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
          <span class="button"><g:actionSubmit class="save" action="save" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
          <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
          <span class="button"><g:actionSubmit class="cancel" action="listTimesheets" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" onclick="return confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Are you sure?')}');" /></span>
        </div>
		
		</g:form>
	</div>

</body>