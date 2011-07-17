

<%@ page import="com.ewconline.timesheet.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <g:javascript src="TS_select_control_utils.js" />
        <g:javascript library="prototype"/>
        <g:javascript library="taskAssignment"/>
        
        <script language="JavaScript" type="text/javascript">

        function selectAllListBoxes() {
            var targetListBoxes = ['authorities'];
            selectAllListBoxesByIds(targetListBoxes);
        }
        </script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'user/list')}"><g:message code="default.home.label"/></a></span>
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
                                    <select name="authorities" id="authorities" multiple="multiple"/>
                                </td>
                            </tr>
                            <tr class="prop">
                              <td colspan="2">
                                <table>
                                  <thead>
                                    <th>Task</th>
                                    <th>Charge Code</th>
                                    <th>Labor Category</th>
                                    <th>Enabled</th>
                                  </thead>
                                  <tbody id="taTable" class="taTable">
                                    <tr id="taSourceRow" class="taSourceRow">
                                      <td>
                                        <g:select optionValue="name"
                                              name="taskAssignment.task.id"
                                              from="${com.ewconline.timesheet.Task.list()}"
                                              optionKey="id"
                                              noSelection="${['null':'Select One...']}"/>
                                      </td>
                                      <td>
                                        <g:select optionValue="chargeNumber"
                                              name="taskAssignment.chargeCode.id"
                                              from="${com.ewconline.timesheet.ChargeCode.list()}"
                                              optionKey="id"
                                              noSelection="${['null':'Select One...']}"/>
                                      </td>
                                      <td>
                                        <g:select optionValue="name"
                                              name="taskAssignment.laborCategory.id"
                                              from="${com.ewconline.timesheet.LaborCategory.list()}"
                                              optionKey="id"
                                              noSelection="${['null':'Select One...']}"/>
                                      </td>
                                      <td>
                                        <g:checkBox name="enabled" value="true" />
                                      </td>
                                    </tr>
                                  </tbody>
                                  <tbody>
                                    <tr>
                                      <td><a href="#" onClick="addRow()">Add Row</a></td>
                                    </tr>
                                  </tbody>
                                </table>
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
