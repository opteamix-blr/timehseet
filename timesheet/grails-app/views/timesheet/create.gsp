<head>
    
	<meta name="layout" content="main" />
	<title>Create a Timesheet</title>
</head>
<body>

	<div class="nav">
		<span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
		<span class="menuButton"><g:link class="create" action="create">Add Timesheet Entry</g:link></span>
		<span class="menuButton"><g:link class="menuButton" action="listTimesheets">Cancel</g:link></span>
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
		<table>
		  <tr>
			<th>Task</th>
			<th>Labor Category</th>
			<th>Charge #</th>
			<th>Sun</th>
			<th>Mon</th>
			<th>Tue</th>
			<th>Wed</th>
			<th>Thu</th>
			<th>Fri</th>
			<th>Sat</th>
			<th>Total</th>
		  </tr>
	<g:each status="i" in="${timesheetInstance?.timesheetEntries}" var="timesheetEntry">
	      <tr>
            <td><g:hiddenField name="timesheetEntries" value="${timesheetEntry?.id}" />${timesheetEntry?.taskAssignment?.task.name} </td>
            <td>${timesheetEntry?.taskAssignment?.laborCategory.name}</td>
            <td><g:hiddenField name="chargeCode${i}" value="${timesheetEntry?.taskAssignment?.chargeCode.chargeNumber}" />${timesheetEntry?.taskAssignment?.chargeCode.chargeNumber}</td>
            <td><g:textField name="sun${i}" value=""></g:textField></td>
            <td><g:textField name="mon${i}" value=""></g:textField></td>
            <td><g:textField name="tue${i}" value=""></g:textField></td>
            <td><g:textField name="wed${i}" value=""></g:textField></td>
            <td><g:textField name="thu${i}" value=""></g:textField></td>
            <td><g:textField name="fri${i}" value=""></g:textField></td>
            <td><g:textField name="sat${i}" value=""></g:textField></td>
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

		<div class="button-primary">
			<g:actionSubmit value="Save" action="save"></g:actionSubmit>
			<g:actionSubmit value="Cancel" action="cancelCreate"></g:actionSubmit>
		</div>
		</g:form>
	</div>

</body>