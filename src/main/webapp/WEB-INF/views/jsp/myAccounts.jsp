<%@ page contentType="text/html; charset=UTF-8" session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page errorPage="error.jsp" %>

<!-- === BEGIN HEADER === -->
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<!-- Title -->
<title>PiggyBank Сметки</title>
<!-- Meta -->
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
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
<!-- Google Fonts-->
<link href="http://fonts.googleapis.com/css?family=Raleway:100,300,400"
	type="text/css" rel="stylesheet">
<link href="http://fonts.googleapis.com/css?family=Roboto:400,300"
	type="text/css" rel="stylesheet">
</head>
<body>
	<%
		if (request.getSession(false) == null) {
			response.sendRedirect("login");
		}
	%>
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
					<a href="index.html" title=""><img src="img/logo_bank.png"
						alt="Logo" /></a>
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
			<div class="row margin-vert-40">
				<!-- Begin Sidebar Menu -->
				<div class="col-md-3">
						<ul class="list-group sidebar-nav" id="sidebar-nav">
						<li class="list-group-item"><a href="myAccounts.html">Моите
								сметки</a></li>
						<li class="list-group-item"><a href="makeAccount.html">Нова
								сметка</a></li>
						<li class="list-group-item"><a href="amounts.html">Наличности</a></li>
						<li class="list-group-item"><a href="monthAmounts.html">Наличности
								по месец</a></li>
					</ul>
				</div>
				<!-- Main Text -->
				<div class="col-md-9">
					<h2 class="margin-bottom-30">Сметки</h2>
					<div class="success-message">
						<c:if test="${not empty successMessage}">
							<c:out value="${successMessage}" />
						</c:if>
					</div>
					<table>
						<tr>
							<th>Име</th>
							<th>IBAN</th>
							<th>Сума</th>
							<th>Валута</th>
							<th>Тип</th>
						</tr>

						<c:forEach items="${accounts}" var="account">
							<tr>
								<td>${account.name}</td>
								<td>${account.IBAN}</td>
								<td>${account.sum}</td>
								<td>${account.currency}</td>
								<td>${account.type}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<!-- End Main Text -->
			</div>
		</div>
	</div>
	<!-- End Content -->
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
		<!-- End JS -->
</body>
</html>
<!-- === END FOOTER === -->