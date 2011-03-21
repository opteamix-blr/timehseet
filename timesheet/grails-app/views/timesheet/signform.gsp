<%@ page import="com.ewconline.timesheet.Timesheet" %>
<html>
<head>
	<title>Timesheet &rsaquo; Sign Timesheet</title>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'timesheet.label', default: 'Timesheet')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
</head>
<body>
        
<div class="nav">Signing timesheet</div>

<div class="body">
<h1></h1>
${flash.message}
<form name="signform" id="signform" action="${createLinkTo(dir:'', file:'timesheet/sign')}" method="post">
  <g:hiddenField name="timesheetId" value="${timesheetId}" />
  <p>
    <label>Password<br />
    <input type="password" name="passwd" id="user_pass" class="input" value="" size="20" tabindex="20" /></label>
  </p>
  <p class="submit">
    <input type="submit" name="wp-submit" id="wp-submit" class="button-primary" value="Sign" tabindex="100" />
  </p>
</form>
</div>

<script type="text/javascript">
function wp_attempt_focus(){
setTimeout( function(){ try{
d = document.getElementById('user_login');
d.value = '';
d.focus();
} catch(e){}
}, 200);
}


</script>
</body>
</html>
