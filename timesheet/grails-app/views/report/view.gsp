

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Reports</title>
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
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Report 1</td>
                            
                            <td valign="top" class="value"><g:link action="exportTimesheets">Export All Approved Timesheets</g:link></td>
                            
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name">Report 3</td>

                            <td valign="top" class="value"><a href="">link 3</a></td>

                        </tr>
                    
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
