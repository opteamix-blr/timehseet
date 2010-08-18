<html>
	<head>
	    <title><g:layoutTitle default="BCT - E-time system" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}">
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
	     
	</head>
	<body bgcolor="#999999">
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
		       Hello, User
		      </td>
		      
		    </tr>
		    <tr>
		      <td align="left" style="padding-left: 5px;">
		        <div id="dateClock"></div>
		      </td>
		      <td align="right" style="padding-right: 10px;">
		      	<font face="arial" color="#FFFFFF" size="2"> 
				  <a href="${createLinkTo(dir:'', file:'access/logout')}">Logout</a>	       
				</font>
		      </td>
		    </tr>
		  </table>
		  
		
		  <table width="900" cellpadding="#000000" style="padding-top: 10px; padding-bottom: 10px">
		    <tr>

		      <td bgcolor="#FFFFFF" valign="top" width="168" style="padding-left: 5px;">
		        <!-- Left Menu options -->
		        <table>
		          <tr>
			          <td style="padding-left: 5px; padding-top: 10px; padding-bottom: 10px;">
			          <font face="arial" color="#000000" size="3"> 
			          	<a href="${createLinkTo(dir:'', file:'timesheet/listTimesheets')}">List Timesheets </a>
			          </font>
			          </td>
			      </tr>
			      <tr>
			          <td style="padding-left: 5px; padding-top: 10px; padding-bottom: 10px;">
			          	<font face="arial" color="#000000" size="3">
			          		<a href="index.gsp">Lookup Timesheets </a>
			          	</font>
			          </td>
			      </tr>
			      <tr>
			          <td style="padding-left: 5px; padding-top: 10px; padding-bottom: 10px;">
			          	<font face="arial" color="#000000" size="3">
			          	  <a href="index.gsp">View Current Timesheet </a>
			          	</font>
			          </td>
			      </tr>
			      <tr>
			          <td style="padding-left: 5px; padding-top: 10px; padding-bottom: 10px;">
			          	<font face="arial" color="#000000" size="3">
			          	  <a href="index.gsp">Create a Timesheet </a>
			          	</font>
			          </td>
			      </tr>
			      <tr>
			          <td style="padding-left: 5px; padding-top: 10px; padding-bottom: 10px;">
			          	<font face="arial" color="#000000" size="3">
			          	<a href="${createLinkTo(dir:'', file:'access/logout')}">Logout</a>
			          	</font>
			          </td>
		          </tr>
		        </table>
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
