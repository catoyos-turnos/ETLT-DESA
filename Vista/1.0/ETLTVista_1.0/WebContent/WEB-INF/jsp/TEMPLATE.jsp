<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="titulo" scope="page" value="TEMPLATE"/>
<c:set var="descripcion" scope="page" value="TEMPLATE"/>
<!DOCTYPE html>
<html>
<%@ include file="default-head.jsp" %>
<c:set var="id_pag" scope="page" value="DEFAULT"/>
<body>
<%@ include file="plantilla-cabecera.jsp" %>
<div class="container">
<div class="row">
<%@ include file="plantilla-sidebar.jsp" %>
	<div class="col-md-8" id="rightCol">

	<!-- CONTENIDO -->

	</div>
</div>
<%@ include file="plantilla-pie.jsp" %>
</div>
<%@ include file="default-footerscripts.jsp" %>
</body>
</html>