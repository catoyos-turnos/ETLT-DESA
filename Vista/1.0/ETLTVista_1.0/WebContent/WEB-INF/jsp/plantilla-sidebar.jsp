<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-4" id="leftCol">
	<div id="panel-side" class="panel panel-default ">
		<!-- Default panel contents -->
		<div class="panel-heading"><strong><a href="${raiz}/app/horario">Mi Horario</a></strong></div>
<c:if test="not empty usuario.trabajador"> </c:if>
		<div id="cont-panel-side">
		<table class="table">
		<tr><th scope="col">dia</th><th scope="col">turno</th><th scope="col">pres.</th><th scope="col">ret.</th></tr>
<c:forEach var="ts" items="${servicios}" end="6">
<c:set var="fechaIn" value="${sdfIn.format(ts.fecha)}" /><c:set var="fechaEx" value="${sdfEx.format(ts.fecha)}" />
			<tr data-toggle="tooltip" title='${ts.servicio.descripcion}'>
				<th scope="row"<c:if test="${fechaIn eq fechaHoy}"> class="servicio_hoy"</c:if>>
					<div class="ctb_campo_fecha"><a href="${raiz}/app/res/${residencia.codigo}/dia?fecha=${fechaIn}">${fechaEx}</a></div>
				</th>
				<td><div class="ctb_campo_turno"><a href="${raiz}/app/turno?cod=${ts.turno.codTurno}">${ts.turno.codTurno}</a></div></td>
				<td>${ts.servicio.hora_pres}</td><td>${ts.servicio.hora_ret}</td>
			</tr>
</c:forEach>
		</table>
		<ul class="pager">
			<li class="previous disabled"><a href="#"><span aria-hidden="true">&larr;</span> Anterior</a></li>
			<li class="next"><a href="#">Siguiente <span aria-hidden="true">&rarr;</span></a></li>
		</ul>
	</div>
	</div>
</div>