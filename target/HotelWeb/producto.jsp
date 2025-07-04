<%-- 
    Document   : producto
    Created on : 20 jun. 2025, 21:22:11
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Listado de Productos</title>
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="css/estilos-vintage.css">
</head>
<body style="padding-top: 80px;">

<jsp:include page="navbar-admin.jsp" />

<div class="container mt-4">
    <h2 class="text-center mb-4" style="color: #365B73;">Listado de Productos</h2>

    <div class="mb-3 text">
        <a href="ProductoServlet?accion=nuevo" class="btn btn-registrar">➕ Registrar Producto</a>
    </div>

    <div class="table-responsive">
        <table class="table table-hover shadow-sm border rounded">
            <thead class="table-light">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Detalle</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Estado</th>
                    <th>Fecha Creación</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${productos}">
                    <tr>
                        <td>${p.idProducto}</td>
                        <td>${p.nombre}</td>
                        <td>${p.detalle}</td>
                        <td>S/. ${p.precio}</td>
                        <td>${p.cantidad}</td>
                        <td>
                            <span class="${p.estado ? 'text-success fw-bold' : 'text-danger fw-bold'}">
                                <c:choose>
                                    <c:when test="${p.estado}">Activo</c:when>
                                    <c:otherwise>Inactivo</c:otherwise>
                                </c:choose>
                            </span>
                        </td>
                        <td>${p.fechaCreacion}</td>
                        <td>
                            <a href="ProductoServlet?accion=editar&id=${p.idProducto}" class="btn btn-sm btn-editar">✏️</a>
                            <a href="ProductoServlet?accion=eliminar&id=${p.idProducto}" class="btn btn-sm btn-eliminar"
                               onclick="return confirm('¿Seguro de eliminar este producto?')">🗑️</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
