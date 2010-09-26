

<%@ page import="com.ewconline.timesheet.Task" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'task.label', default: 'Task')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <g:javascript src="TS_select_control_utils.js" />
        <script language="JavaScript" type="text/javascript">
            function selectAllListBoxes() {
                var targetListBoxes = ['chargeCodes','laborCategories'];
                selectAllListBoxesByIds(targetListBoxes);
            }
        </script>
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
            <g:hasErrors bean="${taskInstance}">
            <div class="errors">
                <g:renderErrors bean="${taskInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="update" method="post" onSubmit="selectAllListBoxes()">
                <g:hiddenField name="id" value="${taskInstance?.id}" />
                <g:hiddenField name="version" value="${taskInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="task.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: taskInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" maxlength="50" value="${taskInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="task.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: taskInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" cols="40" rows="5" value="${taskInstance?.description}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="task.chargeCodes"><g:message code="task.chargeCodes.label" default="Charge Codes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'chargeCodes', 'errors')}">
                                    <g:select name="chargeCode.chargeNumber"
                                                from="${allChargeCodes}"
                                                optionValue="${{it.chargeNumber}}"
                                                optionKey="id"
                                                multiple="true" 
                                                />
                                </td>
                                <td>
                                    <input type="button" value="&#062;" onClick="moveSelectedOptions('chargeCode.chargeNumber', 'chargeCodes');"/><br/>
                                    <input type="button" value="&#060;" onClick="moveSelectedOptions('chargeCodes', 'chargeCode.chargeNumber');"/>
                                </td>
                                <td class="name">
                                    <g:select id="chargeCodes" name="chargeCodes" 
                                                from="${chargeCodes}"
                                                optionValue="chargeNumber"
                                                optionKey="id"
                                                multiple="true"/>
                                </td>
                            </tr>
                    </table>
                </div>
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="laborCategories"><g:message code="laborCategories.label" default="Labor Categories" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'laborCategories', 'errors')}">
                                    <g:select name="laborCategory.name"
                                    ${{it.title?.toUpperCase()}}
                                                from="${allLaborCategories}"
                                                optionValue="name"
                                                optionKey="id"
                                                multiple="multiple"/>
                                    
                                </td>
                                <td>
                                    <input type="button" value="&#062;" onClick="moveSelectedOptions('laborCategory.name', 'laborCategories');"/><br/>
                                    <input type="button" value="&#060;" onClick="moveSelectedOptions('laborCategories', 'laborCategory.name');"/>
                                </td>
                                <td class="name">
                                    <g:select id="laborCategories" name="laborCategories" 
                                                from="${laborCategories}"
                                                optionValue="name"
                                                optionKey="id"
                                                multiple="multiple"/>
                                </td>
                            </tr>
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
