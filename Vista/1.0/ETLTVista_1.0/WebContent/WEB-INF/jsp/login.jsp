<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="titulo" scope="page" value="ENTRAR"/>
<c:set var="descripcion" scope="page" value=""/>
<!DOCTYPE html>
<html>
<%@ include file="default-head.jsp"%>
<body>
	<%@ include file="plantilla-cabecera.jsp"%>
	<%@ include file="plantilla-navbar.jsp"%>
	<div class="jumbotron">
		<div class="container">

			<form class="form-signin">
				<h2 class="form-signin-heading">Please sign in</h2>
				<label for="inputUsuario" class="sr-only">Usuario:</label>
				<input
					type="text" id="inputUsuario" class="form-control"
					placeholder="Usuario" required autofocus>
				<label for="inputPassword" class="sr-only">Password:</label>
				<input
					type="password" id="inputPassword" class="form-control"
					placeholder="Password" required>
				<div class="checkbox">
					<label><input type="checkbox" value="remember-me">Recuerdame</label>
				</div>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
			</form>

			<p>
				<a class="btn btn-primary btn-lg" href="#" role="button">EXPLICAME ESTO &raquo;</a>
			</p>
		</div>
	</div>
	<%@ include file="plantilla-pie.jsp"%>
	<%@ include file="default-footerscripts.jsp" %>
</body>
</html>