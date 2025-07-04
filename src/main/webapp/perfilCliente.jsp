<%-- 
    Document   : perfilCliente
    Created on : 23 jun. 2025, 15:51:40
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.hotel.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");

    boolean esAdmin = (usuarioSesion != null && usuarioSesion.getIdTipoPersona() == 1);
    boolean editaSuPropioPerfil = (usuarioSesion != null && usuario != null && usuarioSesion.getIdPersona() == usuario.getIdPersona());
%>
<html>
<head>
    <title><c:out value="${usuario != null ? 'Editar Perfil' : 'Nuevo Usuario'}"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">

<h2 class="mb-4 text-center">
    <c:choose>
        <c:when test="${editaSuPropioPerfil}">Mi Perfil</c:when>
        <c:otherwise>${usuario != null ? 'Editar Usuario' : 'Registrar Usuario'}</c:otherwise>
    </c:choose>
</h2>

<form action="UsuarioServlet?accion=${editaSuPropioPerfil ? 'guardarPerfil' : 'guardar'}" method="post" enctype="multipart/form-data">
    <input type="hidden" name="idPersona" value="${usuario != null ? usuario.idPersona : ''}"/>

    <div class="mb-3">
        <label class="form-label">Tipo Documento</label>
        <select name="tipoDocumento" class="form-select">
            <option value="DNI" ${usuario.tipoDocumento == 'DNI' ? 'selected' : ''}>DNI</option>
            <option value="PASAPORTE" ${usuario.tipoDocumento == 'PASAPORTE' ? 'selected' : ''}>Pasaporte</option>
        </select>
    </div>

    <div class="mb-3">
        <label class="form-label">Número de Documento</label>
        <input type="text" name="documento" class="form-control" value="${usuario.documento}" required />
    </div>

    <div class="mb-3">
        <label class="form-label">Nombre</label>
        <input type="text" name="nombre" class="form-control" value="${usuario.nombre}" required />
    </div>

    <div class="mb-3">
        <label class="form-label">Apellido</label>
        <input type="text" name="apellido" class="form-control" value="${usuario.apellido}" required />
    </div>

    <div class="mb-3">
        <label class="form-label">Correo</label>
        <input type="email" name="correo" class="form-control" value="${usuario.correo}" required />
    </div>

    <div class="mb-3">
        <label class="form-label">Contraseña</label>
        <input type="password" name="clave" class="form-control" value="${usuario.clave}" required />
    </div>

    <div class="mb-3">
        <label class="form-label">Foto de perfil</label>
        <input type="file" name="imagen" class="form-control" />
        <c:if test="${usuario.imagen != null}">
            <img src="${usuario.imagen}" class="mt-2 rounded" width="80" height="80" alt="Actual"/>
        </c:if>
    </div>

    <c:if test="${esAdmin}">
        <div class="mb-3">
            <label class="form-label">Tipo de Persona</label>
            <select name="tipoPersona" class="form-select">
                <option value="1" ${usuario.idTipoPersona == 1 ? 'selected' : ''}>Administrador</option>
                <option value="2" ${usuario.idTipoPersona == 2 ? 'selected' : ''}>Empleado</option>
                <option value="3" ${usuario.idTipoPersona == 3 ? 'selected' : ''}>Cliente</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Estado</label>
            <select name="estado" class="form-select">
                <option value="true" ${usuario.estado ? 'selected' : ''}>Activo</option>
                <option value="false" ${!usuario.estado ? 'selected' : ''}>Inactivo</option>
            </select>
        </div>
    </c:if>

    <c:if test="${!esAdmin}">
        <input type="hidden" name="tipoPersona" value="3"/>
        <input type="hidden" name="estado" value="true"/>
    </c:if>

    <div class="text-center">
        <input type="submit" class="btn btn-success" value="${usuario != null ? 'Actualizar' : 'Registrar'}" />
        <a href="UsuarioServlet" class="btn btn-secondary">Volver</a>
    </div>
</form>

</body>
</html>