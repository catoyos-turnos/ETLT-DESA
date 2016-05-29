<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${raiz}/app/front">ETLT</a>
		</div>
		<c:if test="${not empty usuario}">
		<div id="navbar" class="navbar-collapse navbar-right collapse">
			<ul class="nav navbar-nav">
				<li class="${(id_pag eq 'MENSAJES')?'active':''}">
					<a href="${raiz}/app/mensajes">MENSAJES<span class="badge badge-num-msg">1</span></a>
				</li>
				<li class="${(id_pag eq 'CAMBIOS')?'active':''}">
					<a href="${raiz}/app/cambios">CAMBIOS
						<span class="badge badge-num-cambios-in">2</span>
						<span class="badge badge-num-cambios-out">3</span>
					</a>
				</li>
				<li class="dropdown ${(id_pag eq 'PERFIL')?'active':''}">
					<a href="${raiz}/app/u/${usuario.idUsuario}" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
						PERFIL<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li class="${(id_pag eq 'CONFIG')?'active':''}"><a href="${raiz}/app/config">CONFIG</a></li>
						<c:if test="${usuario.nivel eq 'ADMIN'||usuario.nivel=='SUPERADMIN'}">
						<li role="separator" class="divider"></li>
						<li class="${(id_pag eq 'ADMIN')?'active':''}"><a href="${raiz}/app/admin">ADMIN</a></li>
						</c:if>
						<li role="separator" class="divider"></li>
						<li><a href="${raiz}/logout">SALIR</a></li>
					</ul>
				</li>
			</ul>
		</div>
		</c:if>
	</div>
</nav>