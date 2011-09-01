<head>
    
	<meta name="layout" content="main" />
        <export:resource/>
	<!-- http://localhost:8080/Timesheet/timesheet/listTimesheets -->
	<title>Timesheet Listing</title>
</head>

<body>

	<div class="nav">
		<span class="menuButton"><a class="home" href="${createLinkTo(dir:'timesheet/listTimesheets')}">Home</a></span>
		<span class="menuButton"><g:link class="create" action="create">Create New Timesheet</g:link></span>
                <span class="menuButton"><a class="create"href="${createLinkTo(dir:'timesheet/askPastTimesheetInfo')}">Create a Past Timesheet</a></span>
	</div>

	<div class="body">
		<h1>Timesheet Listing</h1>
		<g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
        </g:if>
		<div class="list">
			<table>
			<thead>
				<tr>
					<g:sortableColumn property="id" title="Id" />
					<g:sortableColumn property="dateCreated" title="Date Created" />
					<g:sortableColumn property="lastUpdated" title="Last Modified" />
					<g:sortableColumn property="startDate" title="Start Date" />
					<g:sortableColumn property="endDate" title="End Date" />
					<th>Action</th>
					<th>Status</th>
				</tr>
			</thead>
			<tbody>
			<g:each in="${timesheetList}" status="i" var="timesheet">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
					<td><g:link action="viewTimesheet" id="${timesheet.id}">${fieldValue(bean: timesheet, field: "id")}</g:link></td>
					<td><g:link action="viewTimesheet" id="${timesheet.id}"><g:formatDate format="MMM-dd-yyyy hh:mm:ss a" date="${timesheet.dateCreated}"/></g:link></td>
					<td><g:formatDate format="MMM-dd-yyyy hh:mm:ss a" date="${timesheet.lastUpdated}"/></td>
					<td><g:formatDate format="MMM-dd-yyyy" date="${timesheet.startDate}"/></td>
					<td><g:formatDate format="MMM-dd-yyyy" date="${timesheet.endDate}"/></td>
					
					<td class="actionButtons">
						<span class="actionButton">
							<g:link action="show" id="${timesheet.id}">Show</g:link>
						</span>
					</td>
					<td>${timesheet?.currentState}</td>
				</tr>
			</g:each>
			</tbody>
			</table>
		</div>

		<div class="paginateButtons">
			<g:paginate total="${timesheetInstanceTotal}" />
		</div>
<export:formats formats="['csv', 'excel']" />
	</div>
</body>
