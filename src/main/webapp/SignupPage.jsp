<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="LoginPage.css">
<meta charset="ISO-8859-1">
<title>SignUp Page</title>
</head>
<body class="body1">
	<br><br><br><br><br><br><br><br>
	<form class="box2" action="signup-servlet" method="post">
	<h1>Create Account</h1>
	<input type="text" name="full_name" value="" placeholder="Full Name">
	<input type="text" name="username" value="" placeholder="Username">
	<input type="password" name="password" value="" placeholder="Password">
	<input type="password" name="confirm_password" value="" placeholder="Confirm Password">
	${requestScope.passwordsNotMatchingWarning} <!-- Displays warning when the user enters two different passwords -->
	<input type="submit" name="" value="Sign Up">
		<p>Already have an account? <a href="LoginPage.jsp">Login Here</a>. </p>
	</form>
</body>
</html>