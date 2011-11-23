<head>
    
	<meta name="layout" content="main" />
	<title>Create a Timesheet</title>
	<g:javascript library="prototype"/>
	<g:javascript library="timesheet"/>
	<script type="text/javascript">

function grandTotal() {
	var arr=new Array();
<%
  int x=0;
  for (te in timesheetInstance?.timesheetEntries){
     out.println("   arr[" + x + "]=" + te.taskAssignment.id + ";\n")
	 x++
  }	
%>
  var grandTotal = 0;
  var currentValue = 0;
  for (var j=1; j<=7; j++) {
    var subTotal = 0;
    for(var i=0; i<arr.length; i++) {
       var divName= "day" + j +"_" + arr[i];
       currentValue = parseFloat( document.getElementById(divName).value);
       if(isNaN(currentValue)) {
          currentValue = 0.0;
       }
       subTotal=subTotal+currentValue;
    }
    document.getElementById("day" + j).innerHTML = "<b>" + subTotal + "</b>"
    grandTotal = grandTotal + subTotal
  }
  document.getElementById("grandTotal").innerHTML = "<b>" + grandTotal + "</b>"
} // grandTotal()
</script>
	
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
			<tr class="prop">
			    <td valign="top" class="name">
			      <label for="startDate"><g:message code="timesheet.startDate.label" default="Start Date" /></label>
                              <g:hiddenField name="isCurrentWeek" value="${isCurrentWeek}" />
                              <g:hiddenField name="dateOnWeek" value="${timesheetInstance.startDate.format('yyyy-MM-dd').toString()}" />

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
	<g:each status="i" in="${timesheetInstance?.getSortedTimesheetEntries()}" var="timesheetEntry">
	      <tr>
            <td><g:hiddenField name="taskAssignmentIds" value="${timesheetEntry?.taskAssignment?.id}" /><g:hiddenField name="timesheetEntries" value="${timesheetEntry?.id}" />${timesheetEntry?.taskAssignment?.task.name} </td>
            <td>${timesheetEntry?.taskAssignment?.laborCategory.name}</td>
            <td>${timesheetEntry?.taskAssignment?.chargeCode.chargeNumber}</td>
            <g:each status="j" in="${timesheetEntry?.workdays}" var="wd">
             <td><g:textField size="5" name="day${j+1}_${timesheetEntry?.taskAssignment?.id}" id="day${j+1}_${timesheetEntry?.taskAssignment?.id}" value="${wd.hoursWorked}" onChange="updateTotals(${j+1},${timesheetEntry?.taskAssignment?.id});grandTotal();"></g:textField></td>
            </g:each>
            <td><div id="row_${timesheetEntry?.taskAssignment?.id}"></div></td>
          </tr>
    </g:each>
		  <tr>
			<td></td>
			<td></td>
			<th>Total: </th>
			<td><div id="day1"></div></td>
			<td><div id="day2"></div></td>
			<td><div id="day3"></div></td>
			<td><div id="day4"></div></td>
			<td><div id="day5"></div></td>
			<td><div id="day6"></div></td>
			<td><div id="day7"></div></td>
			<td><div id="grandTotal"></div></td>
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