<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demo page</title>
</head>

<body>
<% String user= (String)session.getAttribute("username");
	String name= (String)session.getAttribute("name"); %>
	<h1> ${text}, <%= user%> </h1> 
	<h1><%= name %></h1>
	
</body>
</html>