<head>
    
	<meta name="layout" content="main" />
	<!-- http://localhost:8080/Timesheet/timesheet/create -->
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
		<form action="">
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
		  </tr>
		  <tr>
			<td><g:textField name="task" value=""></g:textField></td>
			<td>read only labor cat</td>
			<td><g:select from="['11111', '22222', '333']" name="chargeNum"></g:select> </td>
			<td><g:textField name="sun" value=""></g:textField></td>
			<td><g:textField name="mon" value=""></g:textField></td>
			<td><g:textField name="tue" value=""></g:textField></td>
			<td><g:textField name="wed" value=""></g:textField></td>
			<td><g:textField name="thu" value=""></g:textField></td>
			<td><g:textField name="fri" value=""></g:textField></td>
			<td><g:textField name="sat" value=""></g:textField></td>
		  </tr>
		</table>

		<div class="button-primary">
			<g:actionSubmit value="Save" action="save"></g:actionSubmit>
			<g:actionSubmit value="Cancel" action="cancelCreate"></g:actionSubmit>
		</div>
		</form>
	</div>
</body>
