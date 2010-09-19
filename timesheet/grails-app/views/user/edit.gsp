

<%@ page import="com.ewconline.timesheet.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
                <script language="JavaScript" type="text/javascript">

        function selectAllListBoxes() {
        	selectItemsListBox('tasks');
        	selectItemsListBox('laborCategories');
        	selectItemsListBox('chargeCodes');
        }
        
        function selectItemsListBox(selectedListBoxName) {
            
            selectedList = document.getElementById(selectedListBoxName);
        	for (var i=(selectedList.options.length-1); i>=0; i--) {
	        	var o = selectedList.options[i];
	        	if (!o.selected) {
	        		o.selected = true;
	        	}
        	}
        }
        
        function moveSelectedOptions(from,to) {
        	fromThis = document.getElementById(from)
        	toThat = document.getElementById(to)
        	// Unselect matching options, if required
        	if (arguments.length>3) {
	        	var regex = arguments[3];
	        	if (regex != "") {
	        		unSelectMatchingOptions(fromThis,regex);
	        	}
        	}
        	// Move them over
        	for (var i=0; i<fromThis.options.length; i++) {
	        	var o = fromThis.options[i];
	        	if (o.selected) {
	        		toThat.options[toThat.options.length] = new Option( o.text, o.value, false, false);
	        	}
        	}
        	// Delete them fromThis original
        	for (var i=(fromThis.options.length-1); i>=0; i--) {
	        	var o = fromThis.options[i];
	        	if (o.selected) {
	        		fromThis.options[i] = null;
	        	}
        	}

        	fromThis.selectedIndex = -1;
        	toThat.selectedIndex = -1;
        }
        
		function moveToRight( leftListName, rightListName ) {
			var leftList = document.getElementById(leftListName).options
			var rightList = document.getElementById(rightListName).options
			for (var i = leftList.length; i >=0; i--) {
				if (leftList[i].selected) {
					
					rightList.add(leftList[i])
				}
			}
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
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="update" method="post" onSubmit="selectAllListBoxes()">
                <g:hiddenField name="id" value="${userInstance?.id}" />
                <g:hiddenField name="version" value="${userInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="username"><g:message code="user.username.label" default="Username" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'username', 'errors')}">
                                    <g:textField name="username" value="${userInstance?.username}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="userRealName"><g:message code="user.userRealName.label" default="User Real Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'userRealName', 'errors')}">
                                    <g:textField name="userRealName" value="${userInstance?.userRealName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="passwd"><g:message code="user.passwd.label" default="Passwd" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'passwd', 'errors')}">
                                    <g:passwordField name="passwd" value="${userInstance?.passwd}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="enabled"><g:message code="user.enabled.label" default="Enabled" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'enabled', 'errors')}">
                                    <g:checkBox name="enabled" value="${userInstance?.enabled}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="authorities"><g:message code="user.authorities.label" default="Authorities" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'authorities', 'errors')}">
                                    
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="chargeCodes"><g:message code="user.chargeCodes.label" default="Charge Codes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'chargeCodes', 'errors')}">
                                    <g:select name="chargeCodes" from="${com.ewconline.timesheet.ChargeCode.list()}" multiple="yes" optionKey="id" size="5" value="${userInstance?.chargeCodes*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="user.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${userInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="email"><g:message code="user.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${userInstance?.email}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="emailShow"><g:message code="user.emailShow.label" default="Email Show" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'emailShow', 'errors')}">
                                    <g:checkBox name="emailShow" value="${userInstance?.emailShow}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="laborCategories"><g:message code="user.laborCategories.label" default="Labor Categories" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'laborCategories', 'errors')}">
                                    <g:select name="laborCategories" from="${com.ewconline.timesheet.LaborCategory.list()}" multiple="yes" optionKey="id" size="5" value="${userInstance?.laborCategories*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tasks"><g:message code="user.tasks.label" default="Tasks" /></label>
                                </td>
                                
                                
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'tasks', 'errors')}">
                                    <g:select name="task.name"
          										from="${allTasks}"
          										optionValue="name"
          										optionKey="id"
          										multiple="true"/>
                                </td>
                                <td>
                                	<input type="button" value="&#062;" onClick="moveSelectedOptions('task.name', 'tasks');"/><br/>
                                	<input type="button" value="&#060;" onClick="moveSelectedOptions('tasks', 'task.name');"/>
                                </td>
                                <td class="name">
                                	<g:select name="tasks"
          										from="${userInstance.tasks}"
          										optionValue="name"
          										optionKey="id"
          										multiple="true"/>
                                </td>
                                
                                
                                
                                
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="timesheets"><g:message code="user.timesheets.label" default="Timesheets" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'timesheets', 'errors')}">
                                    
<ul>
<g:each in="${userInstance?.timesheets?}" var="t">
    <li><g:link controller="timesheet" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="timesheet" action="create" params="['user.id': userInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'timesheet.label', default: 'Timesheet')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pass"><g:message code="user.pass.label" default="Pass" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'pass', 'errors')}">
                                    <g:textField name="pass" value="${userInstance?.pass}" />
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
