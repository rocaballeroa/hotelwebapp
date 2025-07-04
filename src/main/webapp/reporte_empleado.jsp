<%-- 
    Document   : reporte_empleado
    Created on : 1 jul. 2025, 15:31:49
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.hotel.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || usuario.getIdTipoPersona() != 2) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Reporte Diario - Empleado</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">
    <h2 class="mb-4">📅 Ventas de Hoy</h2>

    <c:choose>
        <c:when test="${not empty ventas}">
            <table class="table table-striped table-bordered">
                <thead class="table-light">
                    <tr>
                        <th>ID Venta</th>
                        <th>ID Recepción</th>
                        <th>Total</th>
                        <th>Estado</th>
                        <th>Fecha</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="v" items="${ventas}">
                        <tr>
                            <td>${v.idVenta}</td>
                            <td>${v.idRecepcion}</td>
                            <td>S/. <fmt:formatNumber value="${v.total}" type="number" minFractionDigits="2"/></td>
                            <td>${v.estado}</td>
                            <td><fmt:formatDate value="${v.fechaVenta}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info">No hay ventas registradas hoy.</div>
        </c:otherwise>
    </c:choose>

    <a href="empleado.jsp" class="btn btn-secondary mt-3">← Volver</a>
</body>
</html>
