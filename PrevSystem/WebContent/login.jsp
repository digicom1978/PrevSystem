<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login Page</title>

		<script type="text/javascript">
			function fnCheckValues() {
				var form = document.getElementById("loginForm");
				if (form.username.value == '' ) {
					alert("Please input Username.");
					return;
				}
				if (form.password.value == '' ) {
					alert("Please input password.");
					return;
				}
				form.submit();
			}
		</script>
	</head>
<body>
	TCI HR System<br>
	<h1></h1>
	<form id="loginForm" action="query1?param1=login" method="post">
		<table>
			<tr>
				<td>
					Username :
				</td>
				<td>
					<input type="text" name="username"><br>
				</td>
			</tr>
			<tr>
				<td>
					Password :
				</td>
				<td>
					<input type="password" name="password"><br>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="login" onclick="fnCheckValues()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>