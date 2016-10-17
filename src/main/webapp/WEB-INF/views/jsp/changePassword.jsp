<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" session="false"%>
<%@page errorPage="error.jsp" %>

<!DOCTYPE html>

<html lang="en">
<!--<![endif]-->
<head>
<!-- Title -->
<title>PiggyBank Регистрация</title>
<!-- Meta -->
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<!-- Favicon -->
<link href="favicon.ico" rel="shortcut icon">
<link rel="icon" href="img/prase.png">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script>
	function alert() {
		confirm("Сигурни ли сте, че искате да изпратите информацията?")
	}
</script>

<script>
	function validateForm() {
		var fields = [ "name", "password", "username", "email", "telephone",
				"address", "city", "country", "citizen" ];

		var i, l = fields.length;
		var fieldname;
		for (i = 0; i < l; i++) {
			fieldname = fields[i];
			if (document.forms["register"][fieldname].value === "") {
				return false;
			}
		}
		return true;
	}
</script>
<!-- Bootstrap Core CSS -->
<link rel="stylesheet" href="css/bootstrap.css">
<!-- Template CSS -->
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/nexus.css">
<link rel="stylesheet" href="css/responsive.css">
<link rel="stylesheet" href="css/custom.css">
<!-- Google Fonts-->
<link href="http://fonts.googleapis.com/css?family=Raleway:100,300,400"
	type="text/css" rel="stylesheet">
<link href="http://fonts.googleapis.com/css?family=Roboto:400,300"
	type="text/css" rel="stylesheet">
</head>
<body>
	<!-- Header -->
	<div id="header"
		style="background-position: 50% 0%; <br />
<b>Notice</b>:  Undefined variable: full_page in <b>C:\xampp\htdocs\bootstrap\html\php\header.php</b> on line <b>46</b><br />
"
		data-stellar-background-ratio="0.5">
		<div class="container">
			<div class="row">
				<!-- Logo -->
				<div class="logo">
					<a href="index.html" title=""> <img src="img/logo_bank.png"
						alt="Logo" />
					</a>
				</div>
				<!-- End Logo -->
			</div>
			<!-- Top Menu -->
			<div id="hornav" class="row text-light">
				<div class="col-md-12">
					<div class="text-center visible-lg">
						<ul id="hornavmenu" class="nav navbar-nav">
							<li><a href="index.html" class="fa-home active">НАЧАЛО</a></li>
							<li><span class="fa-gears ">ПРОФИЛ</span>
								<ul>
									<li><a href="personalInfo.html">Лична информация</a></li>
									<li><a href="changePassword.html">Смяна на паролата</a></li>
									<li><a href="toDoList.html">ToDo лист</a></li>
									<li><a href="logout.html">Изход</a></li>
								</ul></li>
							<li><span class="fa-copy ">ТРАНСАКЦИИ</span>
								<ul>
									<li><a href="transactions.html">Моите трансакции</a></li>
									<li><a href="makeTransaction.html">Нова трансакция</a></li>
								</ul></li>
							<li><span class="fa-th ">КАРТИ</span>
								<ul>
									<li><a href="myCards.html">Моите карти</a></li>
									<li><a href="makeCard.html">Направи нова карта</a></li>
								</ul></li>
							<li><span class="fa-font ">СМЕТКИ</span>
								<ul>
									<li><a href="myAccounts.html">Моите сметки</a></li>
									<li><a href="makeAccount.html">Направи нова сметка</a></li>
									<li><a href="amounts.html">Наличности</a></li>
									<li><a href="monthAmounts.html">Наличности по месец</a></li>
								</ul></li>
							<li><a href="contactLogged.html" class="fa-comment ">КОНТАКТИ</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- End Top Menu -->
		</div>
	</div>
	<!-- End Header -->
	<!-- === END HEADER === -->
	<!-- === BEGIN CONTENT === -->
	<div id="content">
		<div class="container background-white">
			<div class="row margin-vert-30">
				<div class="col-md-3">
					<ul class="list-group sidebar-nav" id="sidebar-nav">
						<li class="list-group-item"><a href="personalInfo.html">Лична
								информация</a></li>
						<li class="list-group-item"><a href="changePassword.html">Смяна
								на паролата</a></li>
						<li class="list-group-item"><a href="toDoList.html">ToDo
								лист</a></li>
						<li class="list-group-item"><a href="logout.html">Изход</a></li>
					</ul>
				</div>
				<!-- Main Column -->
				<div class="col-md-9">
					<!-- Main Content -->
					<div class="headline">
						<h2 class="margin-bottom-20">Смяна на паролата</h2>
					</div>
					<p>Използвайте формата, за да промените своята парола.</p>
					<br>
					<!-- Contact Form -->
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
					<form class="register" method="POST">
						<label>Стара парола</label>
						<div class="row margin-bottom-20">
							<div class="col-md-6 col-md-offset-0">
								<input name="oldPassword" class="form-control" type="password"
									required="required" placeholder="Стара парола"> <label>Нова
									парола</label>
								<div class="row margin-bottom-20">
									<div class="col-md-6 col-md-offset-0">
										<input name="password" class="form-control" type="password"
											required="required" placeholder="Нова парола"
											data-validation="strength" data-validation-strength="3">
									</div>
									<p style="color: #000000; font-size: 12px">Трябва да
										съдържа поне 6 знака, една главна буква, цифра и символ.</p>
								</div>

								<p>
									<button type="submit" class="btn btn-primary"
										onsubmit="return validateForm()">Смени</button>
								</p>
					</form>
					<!-- End Contact Form -->
					<!-- End Main Content -->
				</div>
				<!-- End Side Column -->
			</div>
		</div>
	</div>
	<!-- === END CONTENT === -->
	<!-- === BEGIN FOOTER === -->
	<div id="base" class="background-dark text-light">
		<div class="container padding-vert-30">
			<div class="row"></div>
		</div>
	</div>
	<!-- Footer -->
	<div id="footer" class="background-dark text-light">
		<div class="container no-padding">
			<div class="row">
				<!-- Footer Menu -->
				<div id="footermenu" class="col-md-8">
					<ul class="list-unstyled list-inline">
						<li><a href="index.html">Начало</a></li>
						<li><a href="contactLogged.html">Контакти</a></li>

					</ul>
				</div>
				<!-- End Footer Menu -->
				<!-- Copyright -->
				<div id="copyright" class="col-md-4">
					<p class="pull-right">(c) 2016 PiggyBank Online</p>
				</div>
				<!-- End Copyright -->
			</div>
		</div>
		<!-- End Footer -->
		<!-- JS -->
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/scripts.js"></script>
		<!-- Isotope - Portfolio Sorting -->
		<script type="text/javascript" src="js/jquery.isotope.js"></script>
		<script type="text/javascript" src="js/redirect.js"></script>
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
		<script type="text/javascript" src="js/index.js" charset="utf-8"></script>
		<!-- Slimbox2-->
		<script type="text/javascript" src="js/slimbox2.js" charset="utf-8"></script>
		<!-- Modernizr -->
		<script src="assets/js/modernizr.custom.js" type="text/javascript"></script>
		<!-- End JS -->
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
		<script>
			$.validate();
			$.validate({
				modules : 'security',
				onModulesLoaded : function() {
					$('input[name="password"]').displayPasswordStrength();
				}
			});
		</script>
</body>
</html>
<!-- === END FOOTER === -->