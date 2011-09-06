<%@ page import="com.ewconline.timesheet.Task" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'task.label', default: 'Task')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'approver/approverListTimesheet')}"><g:message code="default.home.label"/></a></span>
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
                            <g:sortableColumn property="name" title="${message(code: 'task.name.label', default: 'Name')}" />
                        	<g:sortableColumn property="contractInfo1" title="${message(code: 'task.contractInfo1.label', default: 'Contract Info1')}" />
                        	<g:sortableColumn property="contractInfo2" title="${message(code: 'task.contractInfo2.label', default: 'Contract Info2')}" />
                            <g:sortableColumn property="description" title="${message(code: 'task.description.label', default: 'Description')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${taskInstanceList}" status="i" var="taskInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${taskInstance.id}">${fieldValue(bean: taskInstance, field: "name")}</g:link></td>
                            <td>${fieldValue(bean: taskInstance, field: "contractInfo1")}</td>
                            <td>${fieldValue(bean: taskInstance, field: "contractInfo2")}</td>
                            <td>${fieldValue(bean: taskInstance, field: "description")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${taskInstanceTotal}" />
            </div>
        </div>
    </body>
</html>