

<%@ page import="com.ewconline.timesheet.Change" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'change.label', default: 'Change')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${changeInstance}">
            <div class="errors">
                <g:renderErrors bean="${changeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="domainModelName"><g:message code="change.domainModelName.label" default="Domain Model Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: changeInstance, field: 'domainModelName', 'errors')}">
                                    <g:textField name="domainModelName" value="${changeInstance?.domainModelName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fieldName"><g:message code="change.fieldName.label" default="Field Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: changeInstance, field: 'fieldName', 'errors')}">
                                    <g:textField name="fieldName" value="${changeInstance?.fieldName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="previousValue"><g:message code="change.previousValue.label" default="Previous Value" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: changeInstance, field: 'previousValue', 'errors')}">
                                    <g:textField name="previousValue" value="${changeInstance?.previousValue}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="newValue"><g:message code="change.newValue.label" default="New Value" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: changeInstance, field: 'newValue', 'errors')}">
                                    <g:textField name="newValue" value="${changeInstance?.newValue}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userName"><g:message code="change.userName.label" default="User Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: changeInstance, field: 'userName', 'errors')}">
                                    <g:textField name="userName" value="${changeInstance?.userName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="referenceId"><g:message code="change.referenceId.label" default="Reference Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: changeInstance, field: 'referenceId', 'errors')}">
                                    <g:textField name="referenceId" value="${fieldValue(bean: changeInstance, field: 'referenceId')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
