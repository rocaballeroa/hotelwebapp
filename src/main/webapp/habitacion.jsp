<%-- 
    Document   : habitacion
    Created on : 19 jun. 2025, 21:46:42
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Mantenimiento de Habitaciones</title>
    <link rel="stylesheet" href="css/estilos-vintage.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .sidebar {
            background-color: #f8f9fa;
            border-radius: 1rem;
            padding: 1.5rem;
        }
        .card .card-footer .btn {
            font-size: 0.85rem;
        }
    </style>
</head>
<body class="pt-5 bg-light">

<jsp:include page="navbar-admin.jsp" />

<div class="container mt-4">
    <h2 class="text-center mb-4 text-primary-emphasis">
        🛏️ Mantenimiento de Habitaciones
    </h2>

    <div class="d-flex justify-content-end mb-4">
        <a href="HabitacionServlet?accion=nuevo" class="btn btn-success shadow-sm">
            ➕ Nueva Habitación
        </a>
    </div>

    <div class="row">
        <!-- FILTROS -->
        <div class="col-md-3 mb-4">
            <form method="get" action="HabitacionServlet" class="sidebar shadow-sm">
                <input type="hidden" name="accion" value="filtrar" />

                <div class="mb-4">
                    <h5 class="text-primary">📌 Estados</h5>
                    <c:forEach var="e" items="${estados}">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" 
                                   name="estadosSeleccionados" 
                                   value="${e.idEstadoHabitacion}" 
                                   id="estado-${e.idEstadoHabitacion}"
                                   <c:if test="${fn:contains(param.estadosSeleccionados, e.idEstadoHabitacion)}">checked</c:if>>
                            <label class="form-check-label" for="estado-${e.idEstadoHabitacion}">
                                ${e.descripcion}
                            </label>
                        </div>
                    </c:forEach>
                </div>

                <div class="mb-4">
                    <label class="form-label">Categoría:</label>
                    <select name="categoria" class="form-select">
                        <option value="">-- Todas las categorías --</option>
                        <c:forEach var="c" items="${categorias}">
                            <option value="${c.descripcion}" ${param.categoria == c.descripcion ? "selected" : ""}>
                                ${c.descripcion}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-4">
                    <label class="form-label">Precio máximo (S/.):</label>
                    <input type="number" step="0.01" name="precioMax" class="form-control" value="${param.precioMax}" />
                </div>

                <button type="submit" class="btn btn-primary w-100">🔍 Filtrar</button>
            </form>
        </div>

        <!-- TARJETAS DE HABITACIONES -->
<div class="col-md-9">
    <div class="row">
        <c:forEach var="h" items="${habitaciones}">
            <div class="col-lg-4 col-md-6 col-sm-12 mb-4">
                <div class="card h-100 d-flex flex-column shadow rounded-4 border-0">
                    <img src="${pageContext.request.contextPath}/imagenes/${h.imagen}" 
                         class="card-img-top rounded-top-4" 
                         alt="Imagen habitación" 
                         style="height: 200px; object-fit: cover;">

                    <div class="card-body d-flex flex-column flex-grow-1">
                        <h5 class="card-title text-primary fw-bold">Hab. ${h.numero}</h5>
                        <p class="mb-1"><strong>📝 Detalle:</strong> ${h.detalle}</p>
                        <p class="mb-1"><strong>💲 Precio:</strong> S/. ${h.precio}</p>
                        <p class="mb-1"><strong>🏢 Piso:</strong> ${h.idPiso}</p>
                        <p class="mb-1"><strong>🏷️ Categoría:</strong> ${h.categoriaDescripcion}</p>
                        <p class="mb-0 mt-auto"><strong>📌 Estado:</strong>
                            <span class="fw-semibold 
                                <c:choose>
                                    <c:when test="${h.estadoHabitacionDescripcion == 'DISPONIBLE'}">text-success</c:when>
                                    <c:when test="${h.estadoHabitacionDescripcion == 'OCUPADO'}">text-warning</c:when>
                                    <c:when test="${h.estadoHabitacionDescripcion == 'MANTENIMIENTO'}">text-danger</c:when>
                                    <c:when test="${h.estadoHabitacionDescripcion == 'LIMPIEZA'}">text-primary</c:when>
                                    <c:otherwise>text-secondary</c:otherwise>
                                </c:choose>
                            ">
                                ${h.estadoHabitacionDescripcion}
                            </span>
                        </p>
                    </div>

                    <div class="card-footer bg-light border-0 d-flex justify-content-between flex-wrap gap-2 mt-auto">
                        <a href="HabitacionServlet?accion=editar&id=${h.idHabitacion}" 
                           class="btn btn-sm btn-outline-primary flex-fill rounded-pill text-nowrap">
                            ✏️ Editar
                        </a>
                        <a href="HabitacionServlet?accion=eliminar&id=${h.idHabitacion}" 
                           class="btn btn-sm btn-outline-danger flex-fill rounded-pill text-nowrap"
                           onclick="return confirm('¿Seguro de eliminar?')">
                            🗑 Eliminar
                        </a>
                        <a href="ConsumoProductoServlet?accion=listar&idHabitacion=${h.idHabitacion}" 
                           class="btn btn-sm btn-outline-success flex-fill rounded-pill text-nowrap">
                            📦 Consumos
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

    </div>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>
