

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Reports</title>
        <resource:dateChooser />
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'report/view')}"></a></span>
        </div>
        <div class="body">
            <h1>Reports</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
              <g:form name="report" action="exportTimesheets">
                <table>
                    <tbody>
                        <tr>
                          <th>Date within week that you wish to report on (no selection means all timesheets)</th>
                          <th>Charge Code</th>
                        </tr>
                        <tr>
                          <td>
                            <richui:dateChooser firstDayOfWeek="Sa" name="dateOnWeek" id="dateOnWeek" format="MM/dd/yyyy"/>
                          </td>
                          <td>
                            <g:select optionValue="chargeNumber"
                                      name="chargeNumber"
                                      from="${com.ewconline.timesheet.ChargeCode.listOrderByChargeNumber()}"
                                      noSelection="${['null':'Select One...']}"/>
                          </td>

                        </tr>
                    </tbody>
                </table>
                <g:submitButton name="submit" value="Submit"/>
              </g:form>
            </div>
        </div>
    </body>
</html>
