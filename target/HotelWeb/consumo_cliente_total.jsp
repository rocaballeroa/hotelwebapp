<%-- 
    Document   : consumo_cliente_total
    Created on : 22 jun. 2025, 10:46:50
    Author     : User
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="com.hotel.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || usuario.getIdTipoPersona() != 3) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Todos mis consumos</title>
    <link rel="stylesheet" href="css/estilos-vintage.css">
</head>
<body class="container mt-4">
<jsp:include page="navbar-admin.jsp" />
    <h2 class="mb-4">🧾 Todos Mis Consumos</h2>

    <c:choose>
        <c:when test="${not empty consumos}">
            <table class="table table-striped table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Habitación</th>
                        <th>Producto</th>
                        <th>Precio</th>
                        <th>Cantidad</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="c" items="${consumos}">
                        <tr>
                            <td>${c.idConsumo}</td>
                            <td>${c.idHabitacion}</td>
                            <td>${c.nombreProducto}</td>
                            <td>S/. <fmt:formatNumber value="${c.precio}" type="number" minFractionDigits="2"/></td>
                            <td>${c.cantidad}</td>
                            <td>S/. <fmt:formatNumber value="${c.total}" type="number" minFractionDigits="2"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning">No se encontraron consumos registrados.</div>
        </c:otherwise>
    </c:choose>

    <a href="cliente.jsp" class="btn btn-secondary mt-3">← Volver al Panel</a>
<jsp:include page="footer.jsp" />
</body>
</html>
