<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="LoginPage.css">
<meta charset="ISO-8859-1">
<title>Login Page</title>
</head>
<body class="body1" >
	<br><br><br><br><br><br><br><br>
	<form class="box" action="login-servlet" method="post">
	<h1>Login</h1>
	<input type="text" name="username" value="" placeholder="Username">
	<input type="password" name="password" value="" placeholder="Password">
	<input type="submit" name="" value="Login">
		<p>Ιf you don't have an account sign up <a href="SignupPage.jsp">here</a>. </p>
	</form>
</body>
</html>