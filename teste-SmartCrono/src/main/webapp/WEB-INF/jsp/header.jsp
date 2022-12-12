<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Estante Virtual</title>
<link href="<c:url value='/styles/styles.css'/>" rel="stylesheet" />
</head>
<body>
	<nav class="box-navbar">
		<ul class="list-nav">

			<li><a href="/teste/"><img alt="Logo" src="<c:url value="/assets/logo.jpg"/>"></a></li>
			<li><a href="/teste/">Início</a></li>
			<li><a href="/teste/book/">Ver Livros</a></li>
			<li><a href="/teste/book/createBook">Criar Novo</a></li>
		</ul>
	</nav>
	<header class="header-wrapper">