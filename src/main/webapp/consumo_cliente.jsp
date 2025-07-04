<%-- 
    Document   : consumo_cliente
    Created on : 22 jun. 2025, 10:06:22
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page import="com.hotel.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || (usuario.getIdTipoPersona() != 1 && usuario.getIdTipoPersona() != 2 && usuario.getIdTipoPersona() != 3)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html>
<head>
    <title>Mis Consumos</title>
    
    <link rel="stylesheet" href="css/estilos-vintage.css">
</head>
<body class="container mt-4">
<jsp:include page="navbar-admin.jsp" />
    <h2 class="mb-3">🧾 Mis Consumos - Habitación <strong>${idHabitacion}</strong></h2>

    <c:choose>
        <c:when test="${not empty consumos}">
            <table class="table table-bordered table-hover">
                <thead class="table-light">
                    <tr>
                        <th>#</th>
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
            <div class="alert alert-info">Aún no hay consumos registrados para esta habitación.</div>
        </c:otherwise>
    </c:choose>

    <a href="RecepcionServlet?accion=verReservas" class="btn btn-secondary mt-4">← Volver a mis Reservas</a>
<jsp:include page="footer.jsp" />
</body>
</html>
