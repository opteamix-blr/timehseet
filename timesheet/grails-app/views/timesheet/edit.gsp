    <head>
		<meta name="layout" content="main" />
		<title>Edit a Timesheet</title>
		<g:set var="entityName" value="${message(code: 'timesheet.label', default: 'Timesheet')}" />
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
    <body onload="grandTotal()">
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/timesheet/listTimesheets')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
            <span class="menuButton">Current status: ${timesheetInstance.currentState}</span>
        </div>
        <div class="body">
            <h1>Edit Timesheet</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${timesheetInstance}">
            <div class="errors">
                <g:renderErrors bean="${timesheetInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="update" method="post">
            	<g:hiddenField name="id" value="${timesheetInstance?.id}" />
                <g:hiddenField name="version" value="${timesheetInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="startDate"><g:message code="timesheet.startDate.label" default="Start Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timesheetInstance, field: 'startDate', 'errors')}">
                                    ${timesheetInstance?.startDate}
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endDate"><g:message code="timesheet.endDate.label" default="End Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: timesheetInstance, field: 'endDate', 'errors')}">
                                    ${timesheetInstance?.endDate}
                                </td>
                            </tr>
                            <tr class="prop">
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
							          <g:hiddenField name="totalEntries" value="${timesheetInstance?.timesheetEntries.size()}" />
							    <g:each status="i" in="${timesheetInstance?.timesheetEntries}" var="timesheetEntry" >
							          <tr>
							            <td><g:hiddenField name="timesheetEntries" value="${timesheetEntry?.id}" />${timesheetEntry?.taskAssignment?.task.name} </td>
							            <td>${timesheetEntry?.taskAssignment?.laborCategory.name}</td>
							            <td></td>
							            <g:each status="j" in="${timesheetEntry?.workdays}" var="wd">
							             <td><g:textField size="5" name="day${j+1}_${timesheetEntry?.taskAssignment?.id}" value="${wd.hoursWorked}" id="day${j+1}_${timesheetEntry?.taskAssignment?.id}" onChange="updateTotals(${j+1},${timesheetEntry?.taskAssignment?.id});grandTotal();"></g:textField></td>
							            </g:each>
							            <!-- <td><gt:timesheetEntryTotal timesheetId="${timesheetInstance.id}" timesheetEntryId="${timesheetEntry.id}" totalAcross="true" ></gt:timesheetEntryTotal></td> -->
							            <td><div id="row_${timesheetEntry?.taskAssignment?.id}">${timesheetEntry?.sumHours()}</div></td>
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
							        <g:if test="${weekdaysModified?.size() > 0}">   
							        <table>
							        <tr>
							            <th colspan="6">Modifications:</th>
							          </tr>	
							         <tr>
							            <th>Day</th>
							            <th>Labor Category</th>
							            <th>Charge #</th>
							            <th>Previous Value</th>
							            <th>New Value</th>
							            <th width="40%">Reason</th>
							          </tr>	
							         <g:each status="i" in="${weekdaysModified}" var="weekDay" >
							          <tr>
							            <td>${weekDay.timesheetEntry?.taskAssignment?.task?.name}</td>
							            <td>${weekDay.timesheetEntry?.taskAssignment?.laborCategory?.name}</td>
							            <td><% 
										  
										  java.text.DateFormat df = new java.text.SimpleDateFormat("EEE");
							          	  out.print(df.format(weekDay.dateWorked))
											
											Calendar calendar = Calendar.getInstance() 
											calendar.setTime(weekDay.dateWorked)
											int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
											if (dayOfWeek == 7){
												dayOfWeek = 1
											} else {
												dayOfWeek++
											}
							          	%></td>
							          	<td></td>
										<td>Changed to ${weekDay.hoursWorked} hours <g:hiddenField name="modDay${dayOfWeek}_${weekDay?.timesheetEntry?.taskAssignment?.id}_hrs" value="${weekDay.hoursWorked}" /></td>
										<td><textarea name="modDay${dayOfWeek}_${weekDay?.timesheetEntry?.taskAssignment?.id}_note"></textarea></td>
							          </tr>
							          </g:each>
							        </table>
							        </g:if>
							    </div>

							    <div class="buttons">
				                    <span class="button"><g:actionSubmit class="save" action="update" value="Save" /></span>
									<span class="button"><g:actionSubmit class="sign" action="signform" value="Sign" ></g:actionSubmit></span>
				                    <span class="button"><g:link action="listTimesheets" id="${timesheetInstance.id}">Cancel</g:link></span>
				                </div>
                            </tr>
                        </tbody>
                    </table>
                </div>

            </g:form>
        </div>
    </body>
