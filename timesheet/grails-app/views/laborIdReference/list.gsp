
<%@ page import="com.ewconline.timesheet.LaborIdReference" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'laborIdReference.label', default: 'LaborIdReference')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'laborIdReference.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'laborIdReference.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'laborIdReference.description.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${laborIdReferenceInstanceList}" status="i" var="laborIdReferenceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${laborIdReferenceInstance.id}">${fieldValue(bean: laborIdReferenceInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: laborIdReferenceInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: laborIdReferenceInstance, field: "description")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${laborIdReferenceInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
