<html>
	<head>
	   <title><g:layoutTitle default="BCT - E-time system" /></title>
	   
	   <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
	   <g:layoutHead />
	   <g:javascript library="application" />
	   <script language="JavaScript" type="text/javascript">
	   var month = new Array();
	   month[0]="January";	
	   month[1]="February";
	   month[2]="March";
	   month[3]="April";
	   month[4]="May";
	   month[5]="June";
	   month[6]="July";
	   month[7]="August";
	   month[8]="September";
	   month[9]="October";
	   month[10]="November";
	   month[11]="December";

	   var day = new Array();
	   day[0]="Sunday";
	   day[1]="Monday";
	   day[2]="Tuesday";
	   day[3]="Wednesday";
	   day[4]="Thursday";
	   day[5]="Friday";
	   day[6]="Saturday";

	   today = new Date();
	   date = today.getDate();
	   day = (day[today.getDay()]);
	   month = (month[today.getMonth()]);
	   year = today.getFullYear();

	   suffix = (date==1 || date==21 || date==31) ? "st" : "th" && 
	    	    (date==2 || date==22) ? "nd" : "th" && 
	    	    (date==3 || date==23) ? "rd" : "th"

	   dateStr = day + ", " + date + suffix + " " + month + ", " + year;

	   function updateDateClock() {
	    	now=new Date();
	    	hour=now.getHours();
	    	min=now.getMinutes();
	    	sec=now.getSeconds();

	    	if (min<=9) { min="0"+min; }
	    	if (sec<=9) { sec="0"+sec; }
	    	if (hour>12) { hour=hour-12; add="pm"; }
	    	else { hour=hour; add="am"; }
	    	if (hour==12) { add="pm"; }

	    	document.getElementById("dateClock").innerText = dateStr + " " + ((hour<=9) ? "0"+hour : hour) + ":" + min + ":" + sec + " " + add;

	    	setTimeout("updateDateClock()", 1000);
	    	}
	   window.onload = updateDateClock;
// -->

</script>

			   
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
		      <td width="2" bgcolor="#000000">
			    <!-- mini divider -->
			    &nbsp; 
			  </td>
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
			  <td width="2" bgcolor="#000000">
			    <!-- mini divider -->
			    &nbsp; 
			  </td>
		      
		      <td style="padding-left: 5px;" bgcolor="#ffffff" width="768" valign="top">
		      <!-- layout body / main Content region -->
		        <font face="arial" color="#ffffff" size="4">
		        Content region here!!
		        </font>
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