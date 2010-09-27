<head>
    
	<meta name="layout" content="main" />
	<!-- http://localhost:8080/Timesheet/timesheet/listTimesheets -->
	<title>Timesheet Listing</title>
</head>

<body>

	<div class="nav">
		<span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
		<span class="menuButton"><g:link class="create" action="create">Create New Timesheet</g:link></span>
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
				</tr>
			</thead>
			<tbody>
			<g:each in="${timesheetList}" status="i" var="timesheet">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
					<td>${timesheet.id}</td>
					<td>${timesheet.dateCreated?.encodeAsHTML()}</td>
					<td>${timesheet.lastUpdated?.encodeAsHTML()}</td>
					<td>${timesheet.startDate?.encodeAsHTML()}</td>
					<td>${timesheet.endDate?.encodeAsHTML()}</td>
					
					<td class="actionButtons">
						<span class="actionButton">
							<g:link action="show" id="${timesheet.id}">Show</g:link>
						</span>
					</td>
				</tr>
			</g:each>
			</tbody>
			</table>
		</div>

		<div class="paginateButtons">
			<g:paginate total="0" />
		</div>

	</div>
</body>
