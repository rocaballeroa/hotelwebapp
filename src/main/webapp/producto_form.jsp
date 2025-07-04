<%-- 
    Document   : producto_form
    Created on : 20 jun. 2025, 21:22:36
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Formulario de Producto</title>

    
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="css/estilos-vintage.css">
</head>
<body class="pt-5">

<jsp:include page="navbar-admin.jsp" />

<div class="container mt-5">
    <div class="card shadow p-4 rounded-4">
        <h2 class="text-center mb-4 text-primary-emphasis">
            <c:choose>
                <c:when test="${producto != null}">Editar Producto</c:when>
                <c:otherwise>Registrar Nuevo Producto</c:otherwise>
            </c:choose>
        </h2>

        <form action="ProductoServlet" method="post" class="row g-3">

            <!-- ID oculto -->
            <input type="hidden" name="idProducto" value="${producto != null ? producto.idProducto : ''}" />

            <div class="col-md-6">
                <label class="form-label">Nombre:</label>
                <input type="text" name="nombre" class="form-control" value="${producto != null ? producto.nombre : ''}" required />
            </div>

            <div class="col-md-6">
                <label class="form-label">Detalle:</label>
                <input type="text" name="detalle" class="form-control" value="${producto != null ? producto.detalle : ''}" required />
            </div>

            <div class="col-md-4">
                <label class="form-label">Precio (S/.):</label>
                <input type="number" step="0.01" name="precio" class="form-control" value="${producto != null ? producto.precio : ''}" required />
            </div>

            <div class="col-md-4">
                <label class="form-label">Cantidad:</label>
                <input type="number" name="cantidad" class="form-control" value="${producto != null ? producto.cantidad : ''}" required />
            </div>

            <div class="col-md-4">
                <label class="form-label">Estado:</label>
                <select name="estado" class="form-select">
                    <option value="true" ${producto != null && producto.estado ? 'selected' : ''}>Activo</option>
                    <option value="false" ${producto != null && !producto.estado ? 'selected' : ''}>Inactivo</option>
                </select>
            </div>

            <div class="col-12 d-flex justify-content-between mt-3">
                <button type="submit" class="btn btn-registrar px-4">
                    <c:out value="${producto != null ? 'Actualizar' : 'Registrar'}" />
                </button>
                <a href="ProductoServlet?accion=listar" class="btn btn-secondary px-4">← Volver</a>
            </div>
        </form>
    </div>
</div>


<jsp:include page="footer.jsp" />
</body>
</html>
