

<%@ page import="com.ewconline.timesheet.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
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
                                    <label for="employeeId"><g:message code="user.employeeId.label" default="Employee Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'employeeId', 'errors')}">
                                    <g:textField name="employeeId" value="${userInstance?.employeeId ?: -1}" />
                                </td>
                            </tr>
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
                                    <label for="userRealName"><g:message code="user.userRealName.label" default="First Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'firstName', 'errors')}">
                                    <g:textField name="firstName" value="${userInstance?.firstName}" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userRealName"><g:message code="user.userRealName.label" default="Last Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'firstName', 'errors')}">
                                    <g:textField name="lastName" value="${userInstance?.lastName}" />
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
                                    <g:select id="authorities" name="authorities"
                                                from="${authorities}"
                                                optionValue="authority"
                                                optionKey="id"
                                                multiple="multiple"/>
                                </td>
                            </tr>
                    </table>
                </div>
                
                
                <div class="dialog">
                    <table>
                      <thead>
                        <th>Task</th>
                        <th>Charge Code</th>
                        <th>Labor Category</th>
                        <th>Approver</th>
                        <th>Enabled</th>
                      </thead>
                      <tbody>
                        <g:each in="${userInstance.taskAssignments}" var="t">
                          <tr>
                            <td>
                              <g:link controller="taskAssignment" action="show" id="${t.id}">${t?.task}</g:link>
                            </td>
                            <td>
                              ${t.chargeCode}
                            </td>
                            <td>
                              ${t.laborCategory.name}
                            </td>
                            <td>
                              <g:hiddenField name="existingTaskAssignment.id" value="${t.id}"/>
                              <g:each in="${t?.taskAssignmentApprovals.size() > 0 ? t?.taskAssignmentApprovals : ['null', 'null']}" var="ap" status="c">
                              <g:select optionValue="userRealName"
                                        name="existingTaskAssignment.approver${c+1}.id"
                                        optionKey="id"
                                        from="${com.ewconline.timesheet.Role.findByAuthority('approver_role').people}"
                                        value="${ap == 'null' ? 'null' : ap?.user?.id}"
                                        noSelection="${['null':'Select One...']}"/>
                              </g:each>
                              <g:if test="${t?.taskAssignmentApprovals.size() == 1}">
                                <g:select optionValue="userRealName"
                                        name="existingTaskAssignment.approver2.id"
                                        optionKey="id"
                                        from="${com.ewconline.timesheet.Role.findByAuthority('approver_role').people}"
                                        noSelection="${['null':'Select One...']}"/>
                              </g:if>
                            </td>
                            <td>
                              <g:select name="existingTaskAssignment.enabled"
                                                  from="['enabled', 'disabled']"
                                                  value="${t.enabled ? 'enabled' : 'disabled'}"
                                                  />
                            </td>
                          </tr>
                        </g:each>
                      </tbody>
                      <tbody id="taTable" class="taTable">
                        <tr id="taSourceRow" class="taSourceRow">
                          <td>
                            <g:select optionValue="name"
                                  name="taskAssignment.task.id"
                                  from="${com.ewconline.timesheet.Task.listOrderByName()}"
                                  optionKey="id"
                                  noSelection="${['null':'Select One...']}"/>
                          </td>
                          <td>
                            <g:select optionValue="chargeNumber"
                                  name="taskAssignment.chargeCode.id"
                                  from="${com.ewconline.timesheet.ChargeCode.listOrderByChargeNumber()}"
                                  optionKey="id"
                                  noSelection="${['null':'Select One...']}"/>
                          </td>
                          <td>
                            <g:select optionValue="name"
                                  name="taskAssignment.laborCategory.id"
                                  from="${com.ewconline.timesheet.LaborCategory.listOrderByName()}"
                                  optionKey="id"
                                  noSelection="${['null':'Select One...']}"/>
                          </td>
                          <td>
                            <g:select optionValue="userRealName"
                                      name="taskAssignment.approver1.id"
                                      from="${com.ewconline.timesheet.Role.findByAuthority('approver_role').people}"
                                      optionkey="id"
                                      noSelection="${['null':'Select One...']}"/>
                            <g:select optionValue="userRealName"
                                      name="taskAssignment.approver2.id"
                                      from="${com.ewconline.timesheet.Role.findByAuthority('approver_role').people}"
                                      optionkey="id"
                                      noSelection="${['null':'Select One...']}"/>
                          </td>
                          <td>
                            <g:select name="taskAssignment.enabled"
                                      from="['enabled', 'disabled']"
                                      value="enabled"
                                      />
                          </td>
                        </tr>
                      </tbody>
                      <tbody>
                        <tr>
                          <td><a href="#" onClick="addRow()">Add Row</a></td>
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
