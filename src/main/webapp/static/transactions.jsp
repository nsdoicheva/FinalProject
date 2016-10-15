<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>    
   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transactions</title>
<style type="text/css">
	.error{
		color: #ff0000;
	}
	.errorblock{
		color: #000;
		background-color: #ffEEEE;
		border: 3px solid #ff0000;
		padding: 8px;
		margin: 16px;
	}

</style>
</head>
<body>

	<table>
		<tr>
			<th>Sum</th>
			<th>Sender</th>
			<th>Receiver</th>
			<th>Description</th>
		</tr>

		  	<c:forEach items="${transactions}" var="transaction">			
		   <tr>
				<td>${transaction.sum}</td>
				<td>${transaction.sender}</td>
				<td>${transaction.receiver}</td>
				<td>${transaction.description}</td>			
		</tr>
            </c:forEach>      
	</table>

</body>
</html>