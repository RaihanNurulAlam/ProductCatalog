<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>.: Login :.</h2>
<form action="${pageContext.request.contextPath}/login.do" method="POST">
	User name <br>
	<input type="text" name="user_name"/> <br/>
	Password <br/>
	<input type="password" name="user_password"/> <br/>
	<input type="submit" value="Login"/>
	
<form>

</body>
</html>