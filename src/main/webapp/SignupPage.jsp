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
	<form class="box2" action="login-servlet" method="post">
	<h1>Create Account</h1>
	<input type="text" name="" value="" placeholder="Name">
	<input type="text" name="" value="" placeholder="Surname">
	<input type="text" name="" value="" placeholder="Username"> 
	<input type="password" name="" value="" placeholder="Password">
	<input type="password" name="" value="" placeholder="Confirm Password"> 
	<!--<p>Oups... Something went wrong.</p>-->
	<input type="submit" name="" value="Sign Up">
		<p>Already have an account? <a href="LoginPage.jsp">Login</a>. </p>
	</form>
</body>
</html>