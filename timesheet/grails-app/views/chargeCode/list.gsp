
<%@ page import="com.ewconline.timesheet.ChargeCode" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'chargeCode.label', default: 'ChargeCode')}" />
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
                        
                            <g:sortableColumn property="chargeNumber" title="${message(code: 'chargeCode.chargeNumber.label', default: 'Charge Number')}" />
                            <g:sortableColumn property="displayName" title="${message(code: 'chargeCode.displayName.label', default: 'Display Name')}" />
                            <g:sortableColumn property="description" title="${message(code: 'chargeCode.description.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${chargeCodeInstanceList}" status="i" var="chargeCodeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${chargeCodeInstance.id}">${fieldValue(bean: chargeCodeInstance, field: "chargeNumber")}</g:link></td>
                            <td><g:link action="show" id="${chargeCodeInstance.id}">${fieldValue(bean: chargeCodeInstance, field: "displayName")}</g:link></td>
                            <td>${fieldValue(bean: chargeCodeInstance, field: "description")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${chargeCodeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
