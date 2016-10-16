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
	background-color: #394046;
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

p.greetings {
font-size: 16px;

}

#sendbutton {
/*basic styles*/
	width: 250px;  height: 50px;  color: #fff; background-color: #125182;
	text-align: center;  font-size: 30px;  line-height: 50px;
	
	
	/*gradient styles*/
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0, #125182), color-stop(.5, #1269ab), color-stop(.51, #004375), to(#00345b));
	background: -moz-linear-gradient(top, #125182, #1269ab 50%, #004375 51%, #00345b);  
	
	/*border styles*/
	-moz-border-radius: 30px;
	-webkit-border-radius: 30px;
	border-radius: 30px;
	
	-moz-box-shadow:inset 0 0 10px #000000;
	-webkit-box-shadow:inset 0 0 10px #000000;
	box-shadow:inset 0 0 10px #000000;

}


#sendbutton:hover {
	-moz-box-shadow:inset 0 0 50px #000000;
	-webkit-box-shadow:inset 0 0 50px #000000;
	box-shadow:inset 0 0 50px #000000;
}

#pbtn {
font-size: 20px;
	line-height: 50px;
	font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
	font-weight: 300;
	text-shadow: 0px 0px 3px #555;
	margin-top: -13px;
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
			response.sendRedirect("contact");
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
							<li><a href="index.html" class="fa-home active">НАЧАО</a></li>
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
				<!-- Main Column -->
				<div class="col-md-9">
					<!-- Main Content -->
					<div class="headline">
						<h2 class="margin-bottom-20">Форма за контакт</h2>
					</div>
					<p class="greetings">Използвайте формата ни за контакт, за да изпратите бързо и лесно своето съобщение.</p>
					<p class="greetings">Обратната връзка е от голямо значение за нас, за да подобрим услугите, които предлагаме!</p>
					<br>
					<!-- Contact Form -->

					<button id="sendbutton"
						onclick="document.getElementById('id01').style.display='block'"
						style="width: auto;"> <p id="pbtn"> Изпрати съобщение <p> </button>

					<div id="id01" class="modal">

						<form class="modal-content animate" method = "POST">
							<div class="imgcontainer">
								<span
									onclick="document.getElementById('id01').style.display='none'"
									class="close" title="Close Modal">&times;</span> <img
									src="img/admins.png" alt="Avatar" class="avatar">
							</div>

							<div class="container">
								<label style="margin-right: 8px;"><b>Име</b></label> 
								<input	type="text" placeholder="" name="name" required data-validation="length" data-validation-length="3-20"> 
									
								<label style="margin-left: 25px;" ><b>E-mail</b></label>
								<input type="text" placeholder="" name="email" data-validation="email" required>

								<label style="margin-left: 4px; margin-right: 10px;"><b>Съобщение</b></label>
								<textarea rows="3" cols="50" id="the-textarea" name="text"> </textarea>
                                <span id="max-length-element">151</span> chars left
                                 
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
        <div id="base" class="background-dark text-light">
            <div class="container padding-vert-30">
                <div class="row">
                    
                </div>
            </div>
        </div>
          <!-- Footer -->
        <div id="footer" class="background-dark text-light">
            <div class="container no-padding">
                <div class="row">
                    <!-- Footer Menu -->
                    <div id="footermenu" class="col-md-8">
                        <ul class="list-unstyled list-inline">
                            <li>
                                <a href="index.html">Начало</a>
                            </li>
                            <li>
                                <a href="contactLogged.html">Контакти</a>
                            </li>
                           
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