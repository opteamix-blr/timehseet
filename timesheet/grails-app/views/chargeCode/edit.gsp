

<%@ page import="com.ewconline.timesheet.ChargeCode" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'chargeCode.label', default: 'ChargeCode')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${chargeCodeInstance}">
            <div class="errors">
                <g:renderErrors bean="${chargeCodeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${chargeCodeInstance?.id}" />
                <g:hiddenField name="version" value="${chargeCodeInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="task"><g:message code="chargeCode.task.name.label" default="Task" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chargeCodeInstance, field: 'task', 'errors')}">
                                    <g:select optionValue="name" name="task.id" from="${com.ewconline.timesheet.Task.list()}" optionKey="id" value="${chargeCodeInstance?.task?.id}"  />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="chargeNumber"><g:message code="chargeCode.chargeNumber.label" default="Charge Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chargeCodeInstance, field: 'chargeNumber', 'errors')}">
                                    <g:textField name="chargeNumber" maxlength="100" value="${chargeCodeInstance?.chargeNumber}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="displayName"><g:message code="chargeCode.displayName.label" default="Display Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chargeCodeInstance, field: 'displayName', 'errors')}">
                                    <g:textField name="displayName" maxlength="100" value="${chargeCodeInstance?.displayName}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="chargeCode.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: chargeCodeInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" cols="40" rows="5" value="${chargeCodeInstance?.description}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
