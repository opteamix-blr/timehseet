
<%@ page import="com.ewconline.timesheet.Timesheet" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'timesheet.label', default: 'Timesheet')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <resource:dateChooser />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Lookup Timesheets</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
            <g:form action="lookupMyTimesheets" method="post">
                <table>
                    <thead>
                        <tr>
                            <th>Start date</th>
                            <th>End date</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr>
                            <td>
								<richui:dateChooser name="startDate" id="startDate" format="MM/dd/yyyy"/>
                           	</td>
                            <td>
								<richui:dateChooser name="endDate" id="endDate" format="MM/dd/yyyy"/>
                           	</td>                        
                        	<td><span class="button"><g:actionSubmit class="" action="lookupMyTimesheets" value="Search" /></span></td>
                        </tr>
                    </tbody>
                </table>
            </g:form>
            </div>
            
        </div>
    </body>
</html>
