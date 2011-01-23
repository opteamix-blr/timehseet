
<%@ page import="com.ewconline.timesheet.TaskAssignment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'taskAssignment.label', default: 'TaskAssignment')}" />
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
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="id" title="${message(code: 'taskAssignment.id.label', default: 'Id')}" />
                            <g:sortableColumn property="displayName" title="${message(code: 'taskAssignment.displayName.label', default: 'Display Name')}" />
                            <g:sortableColumn property="enabled" title="${message(code: 'taskAssignment.enabled.label', default: 'Enabled')}" />
                            <g:sortableColumn property="task" titlekey="taskAssignment.task" title="${message(code: 'taskAssignment.task.name.label', default: 'Task')}" />
                            <g:sortableColumn property="chargeCode" titlekey="taskAssignment.chargeCode" title="${message(code: 'taskAssignment.chargeCode.name.label', default: 'Charge Code')}" />
                            <g:sortableColumn property="laborCategory" titlekey="taskAssignment.laborCategory" title="${message(code: 'taskAssignment.laborCategory.name.label', default: 'Labor Category')}" />
                            <g:sortableColumn property="laborIdReference" title="${message(code: 'taskAssignment.laborIdReference.label', default: 'Labor ID Reference')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${taskAssignmentInstanceList}" status="i" var="taskAssignmentInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${taskAssignmentInstance.id}">${fieldValue(bean: taskAssignmentInstance, field: "id")}</g:link></td>
                            <td>${fieldValue(bean: taskAssignmentInstance, field: "displayName")}</td>
                            <td><g:formatBoolean boolean="${taskAssignmentInstance.enabled}" /></td>
                            <td>${fieldValue(bean: taskAssignmentInstance, field: "task.name")}</td>
                            <td>${fieldValue(bean: taskAssignmentInstance, field: "chargeCode.chargeNumber")}</td>
                            <td>${fieldValue(bean: taskAssignmentInstance, field: "laborCategory.name")}</td>
                            <td>${fieldValue(bean: taskAssignmentInstance, field: "laborIdReference")}</td>
                                               
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${taskAssignmentInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
