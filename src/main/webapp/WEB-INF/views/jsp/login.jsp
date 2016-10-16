<%@ page contentType="text/html; charset=UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- === BEGIN HEADER === -->
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org"
	xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<!--<![endif]-->
<head>
<!-- Title -->
<title>PiggyBank Online</title>
<!-- Meta -->
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<script>
	document.addEventListener('DOMContentLoaded', function() {

		var btnReg = document.getElementById('btn-reg');
		btnReg.addEventListener('click', function() {
			location.replace("register");
		});
	});
</script>
<!-- Favicon -->
<link href="favicon.ico" rel="shortcut icon">
<link rel="icon" href="img/prase.png">
<!-- Bootstrap Core CSS -->
<link rel="stylesheet" href="css/bootstrap.css">
<!-- Template CSS -->
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/nexus.css">
<link rel="stylesheet" href="css/responsive.css">
<link rel="stylesheet" href="css/custom.css">
<!-- Login Form css-->
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/style.css">
<!-- Google Fonts-->
<link href="http://fonts.googleapis.com/css?family=Raleway:100,300,400"
	type="text/css" rel="stylesheet">
<link href="http://fonts.googleapis.com/css?family=Roboto:400,300"
	type="text/css" rel="stylesheet">
</head>
<body>
	<%
		if (request.getSession(false) != null) {
			response.sendRedirect("index");
		}
	%>
	<!-- Header -->
	<div id="header" style="background-position: 50% 0%; height: 100%;"
		data-stellar-background-ratio="0.5">
		<div class="container">
			<div class="row">
				<!-- Logo -->
				<div class="logo col-lg-4">
					<a href="index.html" title=""> <img src="img/logo_bank.png"
						alt="Logo" />
					</a> <a href="index.html" title="">
						<div class="logoPig">
							<img src="img/prase.png" alt="Prase" width="180" height="180" />
						</div>
					</a>
				</div>
				<!-- End Logo -->
				<!-- Login form -->
				<div class="login">
					<h1 class="login-heading col-lg-11">Влезте в профила си.</h1>
					<div class="error-message">
						<c:if test="${not empty errorMessage}">
							<c:out value="${errorMessage}" />
						</c:if>
					</div>
					<div class="success-message">
						<c:if test="${not empty successMessage}">
							<c:out value="${successMessage}" />
						</c:if>
					</div>
					<form method="POST" class="col-lg-11"
						onsubmit="return validateForm()">
						<input type="text" name="username" placeholder="Потребителско име"
							class="input-txt" /> <input type="password" name="password"
							placeholder="Парола" class="input-txt" />
						<div class="login-footer">
							<a href="#" class="lnk"> </a>
							<button type="button" id="btn-reg" class="btn btn--reg">Регистрирай
								се</button>
							<button type="submit" class="btn btn--right">Вход</button>
							<br> <br> <br> <span class="icon icon--min">ಠ╭╮ಠ
							</span> <a href="passwordForgotten.html"> Забравена парола?</a>
						</div>
					</form>
				</div>
				<!-- End login  -->
			</div>
			<!-- Top Menu -->
			<div id="hornav" class="row text-light">
				<div class="col-md-12">
					<div class="text-center visible-lg">
						<ul id="hornavmenu" class="nav navbar-nav">
							<li><a href="index.html" class="fa-home active">Начало</a></li>
							<li><a href="contact.html" class="fa-comment ">Контакти</a></li>
						</ul>
						<div style="font-size:20px;">Регистрирали се: <div class="success-message"><c:out value="${count}" /></div></div>
					</div>
				</div>
			</div>
			<!-- End Top Menu -->
		</div>
	</div>
	<!-- End Header -->

	<!-- JS -->
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/scripts.js"></script>
	<!-- Isotope - Portfolio Sorting -->
	<script type="text/javascript" src="js/jquery.isotope.js"></script>
	<!-- Mobile Menu - Slicknav -->
	<script type="text/javascript" src="js/jquery.slicknav.js"></script>
	<!-- Animate on Scroll-->
	<script type="text/javascript" src="js/jquery.visible.js"
		charset="utf-8"></script>
	<!-- Stellar Parallax -->
	<script type="text/javascript" src="js/jquery.stellar.js"
		charset="utf-8"></script>
	<!-- Sticky Div -->
	<script type="text/javascript" src="js/jquery.sticky.js"
		charset="utf-8"></script>
	<!-- Slimbox2-->
	<script type="text/javascript" src="js/slimbox2.js" charset="utf-8"></script>
	<!-- Modernizr -->
	<script src="js/modernizr.custom.js" type="text/javascript"></script>
	<script src="js/index.js" type="text/javascript"></script>
	<!-- End JS -->
</body>
</html>
<!-- === END FOOTER === -->