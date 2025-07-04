<%-- 
    Document   : usuario_form
    Created on : 19 jun. 2025, 23:52:10
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Formulario Usuario</title>


    <!-- Estilos personalizados -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos-vintage.css">
</head>
<body class="pt-5">

<jsp:include page="navbar-admin.jsp" />

<div class="container mt-5">
    <div class="card shadow p-4 rounded-4">
        <h2 class="text-center mb-4 text-primary-emphasis">
            <c:choose>
                <c:when test="${modo == 'registro'}">Registrar Nuevo Usuario</c:when>
                <c:otherwise>Editar Usuario</c:otherwise>
            </c:choose>
        </h2>

        <c:set var="esCliente" value="${esCliente != null && esCliente}" />

        <form action="UsuarioServlet" method="post" enctype="multipart/form-data" class="row g-3">

            <!-- ID oculto -->
            <input type="hidden" name="idPersona" value="${modo == 'edicion' ? usuario.idPersona : ''}" />

            <!-- Tipo Documento -->
            <div class="col-md-6">
                <label class="form-label">Tipo Documento:</label>
                <select name="tipoDocumento" class="form-select" required>
                    <option value="DNI" ${modo == 'edicion' && usuario.tipoDocumento == 'DNI' ? 'selected' : ''}>DNI</option>
                    <option value="PASAPORTE" ${modo == 'edicion' && usuario.tipoDocumento == 'PASAPORTE' ? 'selected' : ''}>Pasaporte</option>
                </select>
            </div>

            <!-- Documento -->
            <div class="col-md-6">
                <label class="form-label">Documento:</label>
                <input type="text" name="documento" class="form-control" value="${modo == 'edicion' ? usuario.documento : ''}" required />
            </div>

            <!-- Nombre -->
            <div class="col-md-6">
                <label class="form-label">Nombre:</label>
                <input type="text" name="nombre" class="form-control" value="${modo == 'edicion' ? usuario.nombre : ''}" required />
            </div>

            <!-- Apellido -->
            <div class="col-md-6">
                <label class="form-label">Apellido:</label>
                <input type="text" name="apellido" class="form-control" value="${modo == 'edicion' ? usuario.apellido : ''}" required />
            </div>

            <!-- Correo -->
            <div class="col-md-6">
                <label class="form-label">Correo:</label>
                <input type="email" name="correo" class="form-control" value="${modo == 'edicion' ? usuario.correo : ''}" required />
            </div>

            <!-- Clave -->
            <div class="col-md-6">
                <label class="form-label">Clave:</label>
                <input type="text" name="clave" class="form-control" value="${modo == 'edicion' ? usuario.clave : ''}" required />
            </div>

            <!-- Imagen -->
            <div class="col-md-12">
                <label class="form-label">Foto de perfil:</label>
                <input type="file" name="imagen" class="form-control" accept="image/*" />
            </div>

            <!-- Tipo Persona -->
 
<c:choose>
    <c:when test="${esCliente}">
        <input type="hidden" name="tipoPersona" value="3" />
    </c:when>
    <c:when test="${esEmpleado}">
        <!-- Empleado: no puede cambiar su tipo -->
        <input type="hidden" name="tipoPersona" value="2" />
        <div class="col-md-6">
            <label class="form-label">Tipo Persona:</label>
            <input type="text" class="form-control" value="Empleado" readonly />
        </div>
    </c:when>
    <c:otherwise>
        <!-- Solo admins pueden ver el combo -->
        <div class="col-md-6">
            <label class="form-label">Tipo Persona:</label>
            <select name="tipoPersona" class="form-select">
                <option value="1" ${modo == 'edicion' && usuario.idTipoPersona == 1 ? 'selected' : ''}>Administrador</option>
                <option value="2" ${modo == 'edicion' && usuario.idTipoPersona == 2 ? 'selected' : ''}>Empleado</option>
            </select>
        </div>
    </c:otherwise>
</c:choose>



            <!-- Estado -->
            <c:choose>
                <c:when test="${esCliente}">
                    <input type="hidden" name="estado" value="true" />
                </c:when>
                <c:otherwise>
                    <div class="col-md-6">
                        <label class="form-label">Estado:</label>
                        <select name="estado" class="form-select">
                            <option value="true" ${modo == 'edicion' && usuario.estado ? 'selected' : ''}>Activo</option>
                            <option value="false" ${modo == 'edicion' && !usuario.estado ? 'selected' : ''}>Inactivo</option>
                        </select>
                    </div>
                </c:otherwise>
            </c:choose>

<!-- Botones -->
<div class="col-12 d-flex justify-content-between mt-3">
    <button type="submit" class="btn btn-registrar px-4">
        <c:out value="${modo == 'edicion' ? 'Actualizar' : 'Registrar'}" />
    </button>
    <c:choose>
        <c:when test="${esCliente and sessionScope.usuario != null and sessionScope.usuario.idTipoPersona == 2}">
            <a href="ClienteServlet" class="btn btn-secondary px-4">← Volver</a>
        </c:when>
        <c:when test="${esCliente and sessionScope.usuario != null and sessionScope.usuario.idTipoPersona == 3}">
            <a href="cliente.jsp" class="btn btn-secondary px-4">← Volver</a>
        </c:when>
        <c:when test="${sessionScope.usuario != null and sessionScope.usuario.idTipoPersona == 1}">
            <a href="UsuarioServlet?accion=listar" class="btn btn-secondary px-4">← Volver</a>
        </c:when>
        <c:when test="${sessionScope.usuario != null and sessionScope.usuario.idTipoPersona == 2 and not esCliente}">
            <a href="empleado.jsp" class="btn btn-secondary px-4">← Volver</a>
        </c:when>
        <c:otherwise>
            <a href="login.jsp" class="btn btn-secondary px-4">← Volver</a>
        </c:otherwise>
    </c:choose>
</div>



        </form>
    </div>
</div>


<jsp:include page="footer.jsp" />
</body>
</html>
