<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="campos-hidden.jsp" %>
<header class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${_raiz}/app/front">ETLT</a>
		</div>
		<c:if test="${not empty usuario}">
		<div id="navbar" class="navbar-collapse navbar-right collapse">
			<ul class="nav navbar-nav">
				<li class="${(_id_pag eq 'MENSAJES')?'active':''}">
					<a href="${_raiz}/app/mensajes"> MENSAJES
						<span class="label label-num-msg"></span>
					</a>
				</li>
				
			<c:choose>
  			<c:when test="${not empty trabajador}">
				<li class="${(id_pag eq 'CAMBIOS')?'active':''}">
					<a href="${_raiz}/app/cambios"> CAMBIOS
						<span class="label label-pill label-num-cambios-in"></span>
						<span class="label label-pill label-num-cambios-out"></span>
					</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="disabled"> <a href="#">CAMBIOS</a></li>
			</c:otherwise>
			</c:choose>
				<li class="dropdown ${(id_pag eq 'PERFIL')?'active':''}">
					<a href="${_raiz}/app/u/${usuario.idUsuario}" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
						PERFIL<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li class="${(_id_pag eq 'CONFIG')?'active':''}"><a href="${_raiz}/app/config">CONFIG</a></li>
						<c:if test="${usuario.nivel eq 'ADMIN'||usuario.nivel=='SUPERADMIN'}">
						<li role="separator" class="divider"></li>
						<li class="${(_id_pag eq 'ADMIN')?'active':''}"><a href="${_raiz}/app/admin">ADMIN</a></li>
						</c:if>
						<li role="separator" class="divider"></li>
						<li><a href="${_raiz}/logout">SALIR</a></li>
					</ul>
				</li>
			</ul>
		</div>
		</c:if>
	</div>
</header>