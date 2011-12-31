<html>
  <head>
    <title><g:layoutTitle default="BCT - E-time system" /></title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}">
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
  <g:layoutHead />
  <g:javascript library="application" />
  <g:javascript>
    var appName = "<g:resource />"
  </g:javascript>

</head><body onload="${pageProperty(name:'body.onload')}" bgcolor="#999999">
  <div align="center">
    <!-- Header area -->
    <table bgcolor="#477BB6" width="900" cellspacing="0" cellpadding="0" style="padding-top: 10px; padding-bottom: 15px;">
      <tr>
        <td align="center">
          <font face="arial" size="6">
            <g:meta name="company.name"/> <g:meta name="app.name"/>
          </font>
        </td>
      </tr>
    </table>
    <table bgcolor="#477BB6" width="900" cellspacing="0" cellpadding="0" style="padding-bottom: 5px;">
      <tr>
        <td align="left" style="padding-left: 5px;">
		       Hello, ${ session.user?.userRealName }
        </td>

      </tr>
      <tr>
        <td align="left" style="padding-left: 5px;">
          <div id="dateClock">${ new Date() }</div>
        </td>
        <td align="right" style="padding-right: 10px;">
          <font face="arial" color="#FFFF00" size="13">
            <a class="buttons" href="${createLinkTo(dir:'', file:'access/logout')}">Logout</a>
          </font>
        </td>
      </tr>
    </table>


    <table width="900" cellpadding="#000000" style="padding-top: 10px; padding-bottom: 10px">
      <tr>

        <td bgcolor="#FFFFFF" valign="top" width="168" style="padding-left: 5px;">
          <!-- Left Menu options -->
          <ul id="mainNav">
            <g:if test="${session?.employeeRole || session?.approverRole || session?.accountantRole || session?.adminRole}">
              <li><p>My Timesheet</p></li>
              <li><a href="${createLinkTo(dir:'', file:'timesheet/edit')}">Current Timesheet</a></li>
              <li><a href="${createLinkTo(dir:'', file:'timesheet/listTimesheets')}">Timesheets </a></li>
              <li><a href="${createLinkTo(dir:'', file:'timesheet/lookupSearchForm')}">Lookup Timesheets </a></li>
            </g:if>
            <g:if test="${session?.approverRole || session?.accountantRole}">
              <li><p>Approver</p></li>
              <li><a href="${createLinkTo(dir:'', file:'approver/approverListTimesheet')}">Recently Signed</a></li>
              <li><a href="${createLinkTo(dir:'', file:'approver/approvedTimesheets')}">Approved Timesheets</a></li>

            </g:if>
            <g:if test="${session?.accountantRole}">
              <li><p>Accountant/Admin</p></li>
              <li><a href="${createLinkTo(dir:'', file:'user/list')}">Employees</a></li>
              <li><a href="${createLinkTo(dir:'', file:'task/list')}">Tasks</a></li>
              <li><a href="${createLinkTo(dir:'', file:'chargeCode/list')}">Charge Codes</a></li>
              <li><a href="${createLinkTo(dir:'', file:'laborCategory/list')}">Labor Categories</a></li>
              <!-- createLinkTo(dir:'', file:'reports/list')-->
              <li><a href="${createLinkTo(dir:'report/view')}">Reporting</a></li>
            </g:if>
            <li><p></p>
            </li>
            <li><a href="${createLinkTo(dir:'', file:'access/logout')}">Logout</a></li>
          </ul>
        </td>

        <td style="padding-left: 5px;" bgcolor="#ffffff" width="768" valign="top">
          <!-- layout body / main Content region -->
      <g:layoutBody />
      </td>
      </tr>
    </table>
    <table bgcolor="#477BB6" width="900" cellspacing="0" cellpadding="0" style="padding-top: 10px; padding-bottom: 10px;">
      <tr>
        <td align="center">
      <g:meta name="app.name"/> Version <g:meta name="app.version"/>
      </td>
      </tr>
    </table>
  </div>
</body>
</html>
