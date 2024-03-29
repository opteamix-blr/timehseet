<head>
    
	<meta name="layout" content="main" />
	
	<title>Approved Time sheet Listing</title>
</head>

<body>

	<div class="nav">
		<span class="menuButton"><a class="home" href="${createLinkTo(dir:'approver/approverListTimesheet')}">Home</a></span>
	</div>

	<div class="body">
		<h1>Approved Timesheet Listing</h1>
		<g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
        </g:if>
		<div class="list">
			<table>
			<thead>
				<tr>
					<g:sortableColumn property="id" title="Id" />
					<g:sortableColumn property="user.lastName" title="Employee"/>
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
					<td><g:link action="viewTimesheet" id="${timesheet.id}">${fieldValue(bean: timesheet, field: "user.userRealName")}</g:link></td>
					<td><g:formatDate format="MMM-dd-yyyy hh:mm:ss a" date="${timesheet.lastUpdated}"/></td>
					<td><g:formatDate format="MMM-dd-yyyy" date="${timesheet.startDate}"/></td>
					<td><g:formatDate format="MMM-dd-yyyy" date="${timesheet.endDate}"/></td>
					
					<td class="actionButtons">
                        <g:if test="${timesheet?.currentState == 'APPROVED'}">
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
			<g:paginate total="${timesheetInstanceTotal}" />
		</div>
                <g:form action="approvedTimesheets" method="get">
                Records Displayed:
                <g:select from="${['10', '50', '100']}"
                          name="max"
                          value="${max}"
                          />
                <g:submitButton name="submit"/>
                </g:form>
	</div>
</body>
