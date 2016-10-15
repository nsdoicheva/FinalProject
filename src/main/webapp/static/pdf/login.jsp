<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"> 
function validateForm() {
    var username = document.forms["login-container"]["username"].value;
    var password = document.forms["login-container"]["password"].value;
    if (username === null || username === "" || password === null || password === "") {
        alert("Name must be filled out");
        return false;
    } else {
    	 confirm('Искаш ли да изпратиш информацията?')
    }
}

</script>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Login</title>
</head>
<body>

	<form class="login-container" method="POST" onclick="return validateForm()" >
		<p>
			<input type="text" name="username" placeholder="Потребителско име" >
		</p>
		<p>
			<input type="password" name="password" placeholder="Парола" >
		</p>
		<span class="password">Забравена <a href="forgotPassword.html">парола?</a></span>
		<br>
		<p>
			<input type="submit" value="Вход"> <a href="register.html"><input
				type="button" value="Регистрирай се"></a> 
		</p>
	</form>


</body>
</html>