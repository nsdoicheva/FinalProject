<%@ page contentType="text/html; charset=UTF-8" session="false"%>

<!DOCTYPE html>

<html lang="en">

<head>
<!-- Title -->
<title>PiggyBank Контакти</title>
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
<style>
/* Full-width input fields */
input[type=text] {
	width: 426px;
	height: 10px;
	padding: 20px 25px;
	margin: 20px 0;
	display: inline-block;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

input[type=password] {
	width: 430px;
	height: 100px;
	padding: 30px 25px;
	margin: 15px 0;
	display: inline-block;
	border: 1px solid #ccc;
	box-sizing: border-box;
}
/* Set a style for all buttons */
button {
	background-color: #4CAF50;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 60%;
	margin-left: 50xp;
}

/* Extra styles for the cancel button */
.cancelbtn {
	width: auto;
	padding: 10px 18px;
	background-color: #f44336;
}

/* Center the image and position the close button */
.imgcontainer {
	text-align: center;
	margin: 24px 0 12px 0;
	position: relative;
}

img.avatar {
	width: 15%;
	border-radius: 50%;
}

.container {
	padding: 16px;
}

span.psw {
	float: right;
	padding-top: 16px;
}

/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
	padding-top: 60px;
}

/* Modal Content/Box */
.modal-content {
	background-color: #fefefe;
	margin: 5% auto 15% auto;
	/* 5% from the top, 15% from the bottom and centered */
	border: 1px solid #888;
	width: 77%; /* Could be more or less, depending on screen size */
}

/* The Close Button (x) */
.close {
	position: absolute;
	right: 25px;
	top: 0;
	color: #000;
	font-size: 35px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: red;
	cursor: pointer;
}

/* Add Zoom Animation */
.animate {
	-webkit-animation: animatezoom 0.6s;
	animation: animatezoom 0.6s
}

@
-webkit-keyframes animatezoom {
	from {-webkit-transform: scale(0)
}

to {
	-webkit-transform: scale(1)
}

}
@
keyframes animatezoom {
	from {transform: scale(0)
}

to {
	transform: scale(1)
}

}

/* Change styles for span and cancel button on extra small screens */
@media screen and (max-width: 300px) {
	span.psw {
		display: block;
		float: none;
	}
	.cancelbtn {
		width: 100%;
	}
}
</style>
<script>
// Get the modal
var modal = document.getElementById('id01');

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
</script>
<!-- Google Fonts-->
<link href="http://fonts.googleapis.com/css?family=Raleway:100,300,400"
	type="text/css" rel="stylesheet">
<link href="http://fonts.googleapis.com/css?family=Roboto:400,300"
	type="text/css" rel="stylesheet">
</head>
<body>
<%
		if (request.getSession(false)== null) {
			response.sendRedirect("contact.jsp");
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
                                <li>
                                    <a href="index.html" class="fa-home active">НАЧАО</a>
                                </li>
                                <li>
                                    <span class="fa-gears ">ПРОФИЛ</span>
                                    <ul>
                                        <li>
                                            <a href="personalInfo.html">Лична информация</a>
                                        </li>
                                        <li>
                                            <a href="changePassword.html">Смяна на паролата</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <span class="fa-copy ">ТРАНСАКЦИИ</span>
                                    <ul>
                                        <li>
                                            <a href="transactions.html">Моите трансакции</a>
                                        </li>
                                        <li>
                                            <a href="makeTransaction.html">Нова трансакция</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <span class="fa-th ">КАРТИ</span>
                                    <ul>
                                        <li>
                                            <a href="myCards.html">Моите карти</a>
                                        </li>
                                        <li>
                                            <a href="createCard.html">Направи нова карта</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <span class="fa-font ">СМЕТКИ</span>
                                    <ul>
                                        <li>
                                            <a href="myAccounts.html">Моите сметки</a>
                                        </li>
                                        <li>
                                            <a href="makeAccount.html">Направи нова сметка</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="contactLogged.html" class="fa-comment ">КОНТАКТИ</a>
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
				<!-- Main Column -->
				<div class="col-md-9">
					<!-- Main Content -->
					<div class="headline">
						<h2 class="margin-bottom-20">Форма за контакт</h2>
					</div>
					<p>Използвайте формата за контакт за да изпратите вашето
						съобщение.</p>
					<br>
					<!-- Contact Form -->

					<h2>Пишете ни!</h2>

					<button
						onclick="document.getElementById('id01').style.display='block'"
						style="width: auto;">Николина</button>

					<div id="id01" class="modal">

						<form class="modal-content animate">
							<div class="imgcontainer">
								<span
									onclick="document.getElementById('id01').style.display='none'"
									class="close" title="Close Modal">&times;</span> <img
									src="img/nikolina.jpg" alt="Avatar" class="avatar">
							</div>

							<div class="container">
								<label style="margin-right: 8px;"><b>Име</b></label> <input
									type="text" placeholder="" name="name" required> <label
									style="margin-left: 25px;" ><b>E-mail</b></label>
								<input type="text" placeholder="" name="email" required>

								<label style="margin-left: 4px; margin-right: 10px;"><b>Съобщение</b></label>
								<textarea rows="3" cols="50"> </textarea>

								<button type="submit" style="margin-left: 200px;">Изпрати</button>

							</div>

							<div class="container" style="background-color: #f1f1f1">
								<button type="button"
									onclick="document.getElementById('id01').style.display='none'"
									class="cancelbtn">Cancel</button>
							</div>
						</form>
					</div>
					<!-- End Contact Form -->
					<!-- End Main Content -->
				</div>
				<!-- End Main Column -->
				<!-- Side Column -->
				<div class="col-md-3">
					<!-- Recent Posts -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">Контакти</h3>
						</div>
						<div class="panel-body">
							<ul class="list-unstyled">
								<li><i class="fa-phone color-primary"></i> +359-894-312-388</li>
								<li><i class="fa-envelope color-primary"></i>
									info@piggybank.bg</li>
								<li><i class="fa-home color-primary"></i>
									http://www.piggybank.bg</li>
							</ul>
							<ul class="list-unstyled">
								<li><strong class="color-primary">
										Понеделник-Петък:</strong> от 9 до 18 часа</li>
								<li><strong class="color-primary">Събота:</strong> от 10 до
									15 часа</li>
								<li><strong class="color-primary">Неделя:</strong> почивен
									ден</li>
							</ul>
						</div>
					</div>
					<!-- End recent Posts -->
					<!-- About -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">За нас</h3>
						</div>
						<div class="panel-body">PiggyBank - банкирай както и когато
							ти е удобно! Онлайн или чрез твоя смартфон - управлавай парите си
							по начина, който желаеш. Имаш бърз и сигурен достъп до твоите
							сметки и дори много повече! Превеждай парите между сметките си за
							секунди!</div>
					</div>
					<!-- End About -->
				</div>
				<!-- End Side Column -->
			</div>
		</div>
	</div>
	<!-- === END CONTENT === -->
	<!-- === BEGIN FOOTER === -->
	<!-- Footer -->
	<!-- End Footer -->
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
	<!-- End JS -->
</body>
</html>