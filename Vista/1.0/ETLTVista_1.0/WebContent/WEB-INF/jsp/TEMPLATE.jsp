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
<%@ include file="plantilla-navbar.jsp" %>

<!-- CONTENIDO -->

<%@ include file="plantilla-pie.jsp" %>
<%@ include file="default-footerscripts.jsp" %>
</body>
</html>