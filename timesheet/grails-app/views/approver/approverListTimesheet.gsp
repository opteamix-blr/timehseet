<head>
    
	<meta name="layout" content="main" />
	<!-- http://localhost:8080/Timesheet/timesheet/listTimesheets -->
	<title>Approver Timesheet Listing</title>
</head>

<body>

	<div class="nav">
		<span class="menuButton"><a class="home" href="${createLinkTo(dir:'timesheet/listTimesheets')}">Home</a></span>
	</div>

	<div class="body">
		<h1>Approver Timesheet Listing</h1>
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
					<td>${timesheet?.id}</td>
					<td>${timesheet?.dateCreated?.encodeAsHTML()}</td>
					<td>${timesheet?.lastUpdated?.encodeAsHTML()}</td>
					<td>${timesheet?.startDate?.encodeAsHTML()}</td>
					<td>${timesheet?.endDate?.encodeAsHTML()}</td>
					
					<td class="actionButtons">
						<g:if test="${timesheet?.currentState == 'SIGNED'}">
                            <span class="menuButton"><g:link class="approver_open" controller="approver" action="accountantApprove" id="${timesheet?.id}">Approve</g:link></span>
                        </g:if>
                        <g:if test="${timesheet?.currentState == 'SIGNED'}">
                            <span class="menuButton"><g:link class="approver_disapprove" controller="approver" action="accountantDisapprove" id="${timesheet?.id}">Disapprove</g:link></span>
                        </g:if>
					</td>
					<td>${timesheet?.currentState}</td>
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
