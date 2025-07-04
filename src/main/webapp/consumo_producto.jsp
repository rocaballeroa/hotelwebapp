<%-- 
    Document   : consumo_producto
    Created on : 20 jun. 2025, 21:45:46
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.hotel.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Consumos por Habitación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">

    <h2 class="mb-3">Consumo de Productos - Habitación <strong>${idHabitacion}</strong></h2>

    <!-- 🛠️ Registrar nuevo consumo con origen -->
    <a href="ConsumoProductoServlet?accion=nuevo&idHabitacion=${idHabitacion}&origen=${param.origen}" 
       class="btn btn-primary mb-3">
        Registrar Nuevo Consumo
    </a>

    <c:choose>
        <c:when test="${not empty consumos}">
            <table class="table table-bordered table-hover">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Producto</th>
                        <th>Precio</th>
                        <th>Cantidad</th>
                        <th>Total</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="c" items="${consumos}">
                        <tr>
                            <td>${c.idConsumo}</td>
                            <td>${c.nombreProducto}</td>
                            <td>S/. <fmt:formatNumber value="${c.precio}" type="number" minFractionDigits="2"/></td>
                            <td>${c.cantidad}</td>
                            <td>S/. <fmt:formatNumber value="${c.total}" type="number" minFractionDigits="2"/></td>
                            <td>
                                <a href="ConsumoProductoServlet?accion=eliminar&id=${c.idConsumo}&idHabitacion=${idHabitacion}&origen=${param.origen}"
                                   class="btn btn-sm btn-danger"
                                   onclick="return confirm('¿Eliminar este consumo?')">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info">No hay consumos registrados para esta habitación.</div>
        </c:otherwise>
    </c:choose>

    <!-- ✅ Botón Volver con origen -->
    <a href="ConsumoProductoServlet?accion=volver&idHabitacion=${idHabitacion}&origen=${param.origen}" 
       class="btn btn-secondary mt-3">← Volver</a>

</body>
</html>
