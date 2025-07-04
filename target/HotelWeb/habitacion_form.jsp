<%-- 
    Document   : habitacion_form
    Created on : 19 jun. 2025, 22:08:50
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Formulario de Habitación</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Tu CSS personalizado -->
    <link rel="stylesheet" href="css/estilos-vintage.css">
</head>
<body class="pt-5 bg-light">

<jsp:include page="navbar-admin.jsp" />

<div class="container mt-5">
    <h2 class="text-center mb-4">
        <c:choose>
            <c:when test="${habitacion != null}">✏️ Editar Habitación</c:when>
            <c:otherwise>➕ Registrar Nueva Habitación</c:otherwise>
        </c:choose>
    </h2>

    <form action="HabitacionServlet" method="post" class="bg-white p-4 rounded shadow-sm">
        <input type="hidden" name="idHabitacion" value="${habitacion != null ? habitacion.idHabitacion : ''}" />

        <div class="mb-3">
            <label class="form-label">Número</label>
            <input type="text" class="form-control" name="numero"
                   value="${habitacion != null ? habitacion.numero : ''}" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Detalle</label>
            <input type="text" class="form-control" name="detalle"
                   value="${habitacion != null ? habitacion.detalle : ''}" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Precio</label>
            <input type="number" step="0.01" class="form-control" name="precio"
                   value="${habitacion != null ? habitacion.precio : ''}" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Estado Habitación</label>
            <select class="form-select" name="estadoHabitacion">
                <c:forEach var="e" items="${estados}">
                    <option value="${e.idEstadoHabitacion}"
                        <c:if test="${habitacion != null && habitacion.idEstadoHabitacion == e.idEstadoHabitacion}">selected</c:if>>
                        ${e.descripcion}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Piso</label>
            <select class="form-select" name="piso">
                <option value="1" ${habitacion != null && habitacion.idPiso == 1 ? 'selected' : ''}>PRIMERO</option>
                <option value="2" ${habitacion != null && habitacion.idPiso == 2 ? 'selected' : ''}>SEGUNDO</option>
                <option value="3" ${habitacion != null && habitacion.idPiso == 3 ? 'selected' : ''}>TERCERO</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Categoría</label>
            <select class="form-select" name="categoria">
                <c:forEach var="cat" items="${categorias}">
                    <option value="${cat.idCategoria}"
                        <c:if test="${habitacion != null && habitacion.idCategoria == cat.idCategoria}">selected</c:if>>
                        ${cat.descripcion}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Imagen</label>
            <input type="text" class="form-control" name="imagen"
                   value="${habitacion != null ? habitacion.imagen : ''}"
                   placeholder="Ej: habitacion1.jpg" />
        </div>

        <div class="mb-4">
            <label class="form-label">Activo</label>
            <select class="form-select" name="estado">
                <option value="true" ${habitacion != null && habitacion.estado ? 'selected' : ''}>Sí</option>
                <option value="false" ${habitacion != null && !habitacion.estado ? 'selected' : ''}>No</option>
            </select>
        </div>

        <div class="d-flex justify-content-between">
            <input type="submit" class="btn btn-info px-4"
                   value="<c:out value='${habitacion != null ? "Actualizar" : "Registrar"}'/>" />
            <a href="HabitacionServlet" class="btn btn-danger px-4">Cancelar</a>
        </div>
    </form>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="footer.jsp" />
</body>
</html>
