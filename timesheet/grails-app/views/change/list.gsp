
<%@ page import="com.ewconline.timesheet.Change" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'change.label', default: 'Change')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'change.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="domainModelName" title="${message(code: 'change.domainModelName.label', default: 'Domain Model Name')}" />
                        
                            <g:sortableColumn property="fieldName" title="${message(code: 'change.fieldName.label', default: 'Field Name')}" />
                        
                            <g:sortableColumn property="previousValue" title="${message(code: 'change.previousValue.label', default: 'Previous Value')}" />
                        
                            <g:sortableColumn property="newValue" title="${message(code: 'change.newValue.label', default: 'New Value')}" />
                        
                            <g:sortableColumn property="userName" title="${message(code: 'change.userName.label', default: 'User Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${changeInstanceList}" status="i" var="changeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${changeInstance.id}">${fieldValue(bean: changeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: changeInstance, field: "domainModelName")}</td>
                        
                            <td>${fieldValue(bean: changeInstance, field: "fieldName")}</td>
                        
                            <td>${fieldValue(bean: changeInstance, field: "previousValue")}</td>
                        
                            <td>${fieldValue(bean: changeInstance, field: "newValue")}</td>
                        
                            <td>${fieldValue(bean: changeInstance, field: "userName")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${changeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
