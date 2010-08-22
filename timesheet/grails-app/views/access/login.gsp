<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US">
<head>
	<title>Timesheet &rsaquo; Log In</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel='stylesheet' id='login-css'  href="${createLinkTo(dir:'css', file:'login.css')}" type='text/css' media='all' />
<link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css', file:'custom-login.css')}" />

</head>
<body class="login">
<% 

println "environment = " + grails.util.Environment.current
%>
<div id="login"><h1><a href="" title="Timesheet">Timesheet</a></h1>
${flash.message}
<form name="loginform" id="loginform" action="${createLinkTo(dir:'', file:'access/authenticate')}" method="post">
	<p>
		<label>Username<br />
		
<g:if test="${grails.util.Environment.current == grails.util.Environment.DEVELOPMENT || grails.util.Environment.current == grails.util.Environment.TEST}">
	<g:textField id="user_login" name="username" value="admin" class="input" size="20" tabindex="10" /></label>
</g:if>
<g:else>
    <input type="text" name="username" id="user_login" class="input" value="" size="20" tabindex="10" /></label>
</g:else>		
	</p>
	<p>
		<label>Password<br />
<g:if test="${grails.util.Environment.current == grails.util.Environment.DEVELOPMENT || grails.util.Environment.current == grails.util.Environment.TEST}">
	<g:passwordField  id="user_pass" name="passwd" value="p@ssw0rd1" class="input" size="20" tabindex="10" /></label>
</g:if>
<g:else>
    <input type="password" name="passwd" id="user_pass" class="input" value="" size="20" tabindex="20" /></label>
</g:else>
		
	</p>
	<p class="forgetmenot"><label><input name="rememberme" type="checkbox" id="rememberme" value="forever" tabindex="90" /> Remember Me</label></p>
	<p class="submit">
		<input type="submit" name="wp-submit" id="wp-submit" class="button-primary" value="Log In" tabindex="100" />

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

//wp_attempt_focus();
//if(typeof wpOnload=='function')wpOnload();
</script>
</body>
</html>
