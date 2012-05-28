
<%@ page import="com.ewconline.timesheet.Timesheet" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'timesheet.label', default: 'Timesheet')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${motd}">
            <div class="motd">${motd.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'timesheet.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="startDate" title="${message(code: 'timesheet.startDate.label', default: 'Start Date')}" />
                        
                            <g:sortableColumn property="endDate" title="${message(code: 'timesheet.endDate.label', default: 'End Date')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'timesheet.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'timesheet.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <th><g:message code="timesheet.user.label" default="User" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${timesheetInstanceList}" status="i" var="timesheetInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${timesheetInstance.id}">${fieldValue(bean: timesheetInstance, field: "id")}</g:link></td>
                        
                            <td><g:link action="show" id="${timesheetInstance.id}"><g:formatDate date="${timesheetInstance.startDate}" /></g:link></td>
                        
                            <td><g:formatDate date="${timesheetInstance.endDate}" /></td>
                        
                            <td><g:formatDate date="${timesheetInstance.dateCreated}" /></td>
                        
                            <td><g:formatDate date="${timesheetInstance.lastUpdated}" /></td>
                        
                            <td>${fieldValue(bean: timesheetInstance, field: "user")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${timesheetInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
