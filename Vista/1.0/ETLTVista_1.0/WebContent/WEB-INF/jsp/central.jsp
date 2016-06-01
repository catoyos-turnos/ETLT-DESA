<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="_titulo" scope="page" value="ETLT"/>
<c:set var="_descripcion" scope="page" value=""/>
<c:set var="_id_pag" scope="page" value="CENTRAL"/>
<!DOCTYPE html>
<html>
<%@ include file="default-head.jsp" %>
<body>
<%@ include file="plantilla-cabecera.jsp" %>
<div class="container">
<div class="row">
<%@ include file="plantilla-sidebar.jsp" %>
<!-- ${mensaje} -->
<c:set var="fechaHoy" value="${sdfIn.format(hoy)}" />
	<div class="col-md-8" id="rightCol">
	
	<div id="panel-central" class="panel panel-default ">
	<h2>mi residencia</h2>
	<p><a href="${_raiz}/app/res/${residencia.codigo}">${residencia.nombre} (${residencia.ciudad})</a></p>
	<p>&nbsp;</p>
	
	<c:if test="not empty trabajador">
	<h2>mi horario</h2>
	<p>Horario de <a href="${_raiz}/app/res/${residencia.codigo}/trab/${trabajador.codigo}/horario">${trabajador.nombre} ${trabajador.apellidos}</a></p>
	<p>&nbsp;</p>
	</c:if>
	</div>
	
	</div>
</div>
<%@ include file="plantilla-pie.jsp" %>
</div>
<%@ include file="default-footerscripts.jsp" %>
</body>
</html>