<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="titulo" scope="page" value="ENTRAR"/>
<c:set var="descripcion" scope="page" value=""/>
<!DOCTYPE html>
<html>
<%@ include file="default-head.jsp"%>
<c:set var="_id_pag" scope="page" value="LOGIN"/>
<body>
	<%@ include file="plantilla-cabecera.jsp"%>

	<div class="container">
		<form action="${_raiz}/login" method="POST" class="form-signin">
			<label for="inputUsuario" class="sr-only">Usuario:</label>
			<input type="text" id="inputUsuario" name="user" class="form-control" placeholder="Usuario" required autofocus>
			<label for="inputPassword" class="sr-only">Password:</label>
			<input type="password" id="inputPassword" name="pass" class="form-control" placeholder="Password" required>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
		</form>
		
		<%@ include file="plantilla-pie.jsp"%>
	</div>
	<%@ include file="default-footerscripts.jsp" %>
</body>
</html>