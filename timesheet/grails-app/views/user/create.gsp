

<%@ page import="com.ewconline.timesheet.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <script language="JavaScript" type="text/javascript">

        function selectAllListBoxes() {
        	selectItemsListBox('tasks');
        	selectItemsListBox('laborCategories');
        	selectItemsListBox('chargeCodes');
        	selectItemsListBox('authorities');
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
//        	if ((arguments.length<3) || (arguments[2]==true)) {
//	        	sortSelect(fromThis);
//	        	sortSelect(toThat);
//        	}
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
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" onSubmit="selectAllListBoxes()">
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
                                    <label for="pass"><g:message code="user.pass.label" default="Pass" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'pass', 'errors')}">
                                    <g:textField name="pass" value="${userInstance?.pass}" />
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
                                    <label for="user.authorities"><g:message code="user.authorities.label" default="Authorities" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'authorities', 'errors')}">
                                    <g:select name="role.authority"
                                                from="${allRoles}"
                                                optionValue="authority"
                                                optionKey="id"
                                                multiple="multiple"/>
                                    
                                </td>
                                <td>
                                    <input type="button" value="&#062;" onClick="moveSelectedOptions('role.authority', 'authorities');"/><br/>
                                    <input type="button" value="&#060;" onClick="moveSelectedOptions('authorities', 'role.authority');"/>
                                </td>
                                <td class="name">
                                    <select id="authorities" name="authorities" multiple="multiple">
                                    </select>
                                </td>
                            </tr>
                    </table>
                </div>
                <div class="dialog">
                	<table>
                		<tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="user.tasks"><g:message code="user.tasks.label" default="Tasks" /></label>
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
                                    <select id="tasks" name="tasks" multiple="multiple">
                                    </select>
                                </td>
                            </tr>
                	</table>
                </div>
                <div class="dialog">
                	<table>
                		<tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="user.laborCategories"><g:message code="user.laborCategories.label" default="Labor Categories" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'laborCategories', 'errors')}">
                                    <g:select name="laborCategory.name"
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
                                    <select id="laborCategories" name="laborCategories" multiple="multiple">
                                    </select>
                                </td>
                            </tr>
                	</table>
                </div>
                <div class="dialog">
                	<table>
                		<tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="user.chargeCodes"><g:message code="user.chargeCodes.label" default="Charge Codes" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'chargeCodes', 'errors')}">
                                    <g:select name="chargeCode.chargeNumber"
          										from="${allChargeCodes}"
          										optionValue="chargeNumber"
          										optionKey="id"
          										multiple="true"/>
                                </td>
                                <td>
                                	<input type="button" value="&#062;" onClick="moveSelectedOptions('chargeCode.chargeNumber', 'chargeCodes');"/><br/>
                                	<input type="button" value="&#060;" onClick="moveSelectedOptions('chargeCodes', 'chargeCode.chargeNumber');"/>
                                </td>
                                <td class="name">
                                    <select id="chargeCodes" name="chargeCodes" multiple="multiple">
                                    </select>
                                </td>
                            </tr>
                	</table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
