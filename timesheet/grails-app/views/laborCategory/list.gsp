
<%@ page import="com.ewconline.timesheet.LaborCategory" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'laborCategory.label', default: 'LaborCategory')}" />
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
                        
                            <g:sortableColumn property="name" title="${message(code: 'laborCategory.name.label', default: 'Name')}" />
                            <g:sortableColumn property="description" title="${message(code: 'laborCategory.description.label', default: 'Description')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${laborCategoryInstanceList}" status="i" var="laborCategoryInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${laborCategoryInstance.id}">${fieldValue(bean: laborCategoryInstance, field: "name")}</g:link></td>
                            <td>${fieldValue(bean: laborCategoryInstance, field: "description")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${laborCategoryInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
