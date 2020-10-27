<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Form</title>
</head>
<body>
<h1>Welcome to the Travel Experts Website</h1>
<p>Log in to access Rewards section:
	<%
		if ((session.getAttribute("message") != null) 
				&& (!session.getAttribute("message").equals("")))
		{
			out.print("<h1 style='color:red'>" + session.getAttribute("message") + "</h1>");
			session.setAttribute("message", "");
			//session.removeAttribute("message");
		}
	%>
	<form action="Login">
		User ID: <input type="text" name="userid" /><br />
		Password: <input type="password" name="password" /><br /><br />
		<input type="submit" value="Log In" />
	</form>
</body>
</html>