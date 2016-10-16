<%@page import="bg.piggybank.model.user.UserDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" session="false" %>
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
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
        <!-- Favicon -->
        <link href="favicon.ico" rel="shortcut icon">
        <link rel="icon" href="img/prase.png">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
      
      
   
        <script> function alert() {
        	     	confirm("Сигурни ли сте, че искате да изпратите информацията?")
        }</script>

<script>
function validateForm()
{
  var fields = ["name", "password", "username", "email", "telephone", "address", "city", "country","citizen"];

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
        <link href="http://fonts.googleapis.com/css?family=Raleway:100,300,400" type="text/css" rel="stylesheet">
        <link href="http://fonts.googleapis.com/css?family=Roboto:400,300" type="text/css" rel="stylesheet">
    </head>
    <body>
        <!-- Header -->
        <div id="header" style="background-position: 50% 0%; <br />
<b>Notice</b>:  Undefined variable: full_page in <b>C:\xampp\htdocs\bootstrap\html\php\header.php</b> on line <b>46</b><br />
" data-stellar-background-ratio="0.5">
            <div class="container">
                <div class="row">
                    <!-- Logo -->
                    <div class="logo">
                        <a href="index.html" title="">
                            <img src="img/logo_bank.png" alt="Logo" />
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
                                    <a href="index.html" class="fa-home ">НАЧАЛО</a>
                                </li>
                                <li>
                                    <a href="contact.jsp" class="fa-comment">КОНТАКТИ</a>
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
                            <h2 class="margin-bottom-20">Регистрация</h2>
                        </div>
                        <p>Използвайте формата за регистрация за да създадете свой профил.</p>
                        <br>
                        <!-- Contact Form -->
                        <form class="register" method = "POST">
                            <label>Име</label> 
                            <div class="row margin-bottom-20">
                                <div class="col-md-6 col-md-offset-0">
                                    <input name="name" class="form-control" type="text" required="required" placeholder="Име и фамилия"  data-validation="custom"
                                       data-validation-regexp="^([a-zA-Zа-яА-Я\s]*)$" data-validation-error-msg="Your name can contains only letters">
                                </div>
                            </div> 
                            <label>ЕГН </label>
                            <div class="row margin-bottom-20">
                              <div class="col-md-6 col-md-offset-0">
                                    <input name="egn" class="form-control" type="text" required="required" placeholder="Вашето ЕГН"  data-validation="number">
                                </div>
                            </div>
                            <label>Потребителско име</label>
                            <div class="row margin-bottom-20">
                              <div class="col-md-6 col-md-offset-0">
                                    <input name="username"class="form-control" type="text" required="required" placeholder="Потребителско име" data-validation="length" data-validation-length="min3">
                                </div>
                            </div>
                            <label>Парола</label>
                            <div class="row margin-bottom-20">
                              <div class="col-md-6 col-md-offset-0">
                                    <input name="password" class="form-control" type="password" required="required" placeholder="Парола"  data-validation="strength" 
		 data-validation-strength="3">
                                </div>
                                <p style="color:#000000; font-size:12px">Трябва да съдържа поне 6 символа, една главна буква и една цифра.</p>
                            </div>
                            <label>Телефон</label>
                            <div class="row margin-bottom-20">
                              <div class="col-md-6 col-md-offset-0">
                                    <input name="telephone" class="form-control" type="number" required="required" placeholder="08- -  - - -  - - -" data-validation="number" >
                                </div>
                            </div>
                            <label>Email</label>
                            <div class="row margin-bottom-20">
                              <div class="col-md-6 col-md-offset-0">
                                    <input name="email" class="form-control" type="text" required="required" placeholder="info@piggybank.bg" data-validation="email" >
                                </div>
                            </div>
                            <label>Адрес</label>
                            <div class="row margin-bottom-20">
                              <div class="col-md-6 col-md-offset-0">
                                    <input name="address" class="form-control" type="text" required="required" placeholder="Настоящ адрес: улица, номер" data-validation="length" data-validation-length="min5">
                                </div>
                            </div>
                            <label>Град</label>
                            <div class="row margin-bottom-20">
                              <div class="col-md-6 col-md-offset-0">
                            
                            	   <input name="city" class="form-control" type="text" required="required" placeholder="Настоящ град" data-validation="custom"
                                       data-validation-regexp="^([a-zA-Zа-яА-Я\s]*)$" >
                           
                                </div>
                            </div>
                            <label>Държава</label>
                            <div class="row margin-bottom-20">
                              <div class="col-md-6 col-md-offset-0">
                              
                                    <input name="country" class="form-control" type="text" required="required" placeholder="Държава">
                                   
                                </div>
                            </div>
                            <label for="grajdanstvo">Гражданство</label>
                            <div class="row margin-bottom-20">
                              <div class="col-md-6 col-md-offset-0">
                                    <input name="citizen" class="form-control" id = "grajdanstvo" type="text" required="required" placeholder="Гражданство" data-validation="custom"
                                       data-validation-regexp="^([a-zA-Zа-яА-Я\s]*)$" >
                                </div>
                            </div>
                            
                            <p>
                                <button type="submit" class="btn btn-primary" onsubmit = "return validateForm()">Регистрирай се</button>
                            </p>
                        </form>
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
                                    <li>
                                        <i class="fa-phone color-primary"></i>+359-894-312-388</li>
                                    <li>
                                        <i class="fa-envelope color-primary"></i>info@piggybank.bg</li>
                                    <li>
                                        <i class="fa-home color-primary"></i>http://www.piggybank.bg</li>
                                </ul>
                                <ul class="list-unstyled">
                                    <li>
                                        <strong class="color-primary">Понеделник-Петък:</strong>от 9 до 18 часа</li>
                                    <li>
                                        <strong class="color-primary">Събота:</strong>от 10 до 15 часа</li>
                                    <li>
                                        <strong class="color-primary">Неделя:</strong>почивен ден</li>
                                </ul>
                            </div>
                        </div>
                        <!-- End recent Posts -->
                        <!-- About -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">За нас</h3>
                            </div>
                            <div class="panel-body">
                            PiggyBank - банкирай както и когато ти е удобно! Онлайн или чрез твоя смартфон - управлавай парите си по начина, който желаеш. Имаш бърз и сигурен достъп до твоите сметки и дори много повече! Превеждай парите между сметките си за секунди!
                            </div>
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
            <script type="text/javascript" src="js/redirect.js"></script>
            <!-- Mobile Menu - Slicknav -->
            <script type="text/javascript" src="js/jquery.slicknav.js" ></script>
            <!-- Animate on Scroll-->
            <script type="text/javascript" src="js/jquery.visible.js" charset="utf-8"></script>
            <!-- Stellar Parallax -->
            <script type="text/javascript" src="js/jquery.stellar.js" charset="utf-8"></script>
            <!-- Sticky Div -->
            <script type="text/javascript" src="js/jquery.sticky.js" charset="utf-8"></script>
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