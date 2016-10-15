<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<style>
#form1 {
 display : none;
}
button {
 margin-bottom: 10px;
}
</style>
<script type="text/javascript">
$( document ).ready( function() {
	  $( "#signup" ).click( function() {
	    $( "#form1" ).toggle("slow");
	  });
	});
</script>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
<!--  
<form class="register-container" method="POST">
    <div class="Note"> Име:</div>
    <p><input type="text" name ="name" id="name" placeholder="Вашето име" required></p>
    
    <div class="Note"> Password:</div>
    <p class="user"><input type="password" name="password" id="password" placeholder="Потребителско име" required></p>
    
    <div class="Note"> Потребителско име:</div>
    <p><input type="text" name="username" id="username" placeholder="********" required></p>
    
     <div class="Note"> Адрес:</div>
	<p><input type="text" name="address" id="address" placeholder="София, бул. Витоша 66" required></p>
	
	 <div class="Note"> City :</div>
	<p><input type="text" name="city" id="city" placeholder="" required></p>
	
	<div class="Note"> Country :</div>
	<p><input type="text" name="country" id="country" placeholder="" required></p>
	
	<div class="Note"> Телефон:</div>
	<p><input type="text" name="telephone" id= "telephone" placeholder="+359/0 898 555 666 " required></p>
	
	 <div class="Note"> Имейл:</div>
	<p><input type="text" name="email" id="email" placeholder="user@sofiiskidupki.bg" required></p>
	
	<div class="Note"> EGN:</div>
	<p><input type="text" name="egn" id= "egn" placeholder="+ " required></p>
	
	<div class="Note"> Grajdanstvo:</div>
	<p><input type="text" name="citizen" id= "citizen" placeholder="" required></p>
	<a href="register.html"> <input class="reg" type="submit" value="Регистрирай се"></a>
	
 </form>
-->	


<button id="signup">Click here to sign-up!</button>
    <form  method="POST"  id="form1">
       <div class="Note"> Име:</div>
    <p><input type="text" name ="name" id="name" placeholder="Вашето име" required></p>
    
    <div class="Note"> Password:</div>
    <p class="user"><input type="password" name="password" id="password" placeholder="Потребителско име" required></p>
    
    <div class="Note"> Потребителско име:</div>
    <p><input type="text" name="username" id="username" placeholder="********" required></p>
    
     <div class="Note"> Адрес:</div>
	<p><input type="text" name="address" id="address" placeholder="София, бул. Витоша 66" required></p>
	
	 <div class="Note"> City :</div>
	<p><input type="text" name="city" id="city" placeholder="" required></p>
	
	<div class="Note"> Country :</div>
	<p><input type="text" name="country" id="country" placeholder="" required></p>
	
	<div class="Note"> Телефон:</div>
	<p><input type="text" name="telephone" id= "telephone" placeholder="+359/0 898 555 666 " required></p>
	
	 <div class="Note"> Имейл:</div>
	<p><input type="text" name="email" id="email" placeholder="user@sofiiskidupki.bg" required></p>
	
	<div class="Note"> EGN:</div>
	<p><input type="text" name="egn" id= "egn" placeholder="+ " required></p>
	
	<div class="Note"> Grajdanstvo:</div>
	<p><input type="text" name="citizen" id= "citizen" placeholder="" required></p>
        
        <input class="btn" type="submit" value="sign up" name="signup">
    </form>
    
</body>
</html>


