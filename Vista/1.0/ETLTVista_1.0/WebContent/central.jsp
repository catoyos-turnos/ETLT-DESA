<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@ include file="default-head.jsp" %>
<body>
<%@ include file="plantilla-cabecera.jsp" %>
<%@ include file="plantilla-navbar.jsp" %>
	<h2>mi residencia</h2>
	<p><a href="res/${residencia.codigo}">${residencia.nombre} (${residencia.ciudad})</a></p>
	<h2>mi semana</h2>
	<table border="1">
	<tbody>	
<c:set var="fechaHoy" value="${sdfIn.format(hoy)}" />
<c:forEach var="ts" items="${servicios}" >
<c:set var="fechaIn" value="${sdfIn.format(ts.fecha)}" /><c:set var="fechaEx" value="${sdfEx.format(ts.fecha)}" />
		<tr>
			<th scope="row"<c:if test="${fechaIn eq fechaHoy}"> class="servicio_hoy"</c:if>>
			<div class="ctb_campo_fecha"><a href="res/${residencia.codigo}/dia?fecha=${fechaIn}">${fechaEx}</a></div></th>
			<td><div class="ctb_campo_turno"><a href="turno?cod=${ts.turno.codTurno}">${ts.turno.codTurno}</a></div></td>
			<td>${ts.servicio.hora_pres}</td>
			<td>${ts.servicio.hora_ret}</td>
			<td><div class="ctb_campo_serv_desc"><a href="trab/${trabajador.codigo}/dia?fecha=${fechaIn}">${ts.servicio.descripcion}</a></div></td>
			<td><div class="ctb_bt_soli_cambio"><a href="solicita_cambio_turno?user=${trabajador.codigo}&fecha=${fechaIn}" >SOLICITAR CAMBIO</a></div></td>
		</tr>
</c:forEach>
	</tbody>
	</table>
	<p>&nbsp;</p>
	<p><a href="horario">HORARIO COMPLETO</a></p>

<%@ include file="plantilla-pie.jsp" %>
</body>
</html>