<%-- 
    Document   : reporte_admin
    Created on : 1 jul. 2025, 15:03:35
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.hotel.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || usuario.getIdTipoPersona() != 1) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Reporte de Ventas - Administrador</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">
    <h2 class="mb-4">📊 Reporte de Ventas (Administrador)</h2>

    <form method="get" action="ReporteServlet" class="row g-2 mb-4">
    <input type="hidden" name="accion" value="reporteRango">

    <div class="col-md-4">
        <label for="inicio" class="form-label">Desde</label>
        <input type="date" name="inicio" id="inicio" class="form-control" value="${param.inicio}">
    </div>

    <div class="col-md-4">
        <label for="fin" class="form-label">Hasta</label>
        <input type="date" name="fin" id="fin" class="form-control" value="${param.fin}">
    </div>

    <div class="col-md-4 d-flex align-items-end">
        <button type="submit" class="btn btn-primary w-100">Filtrar</button>
    </div>
</form>

    <c:choose>
        <c:when test="${not empty ventas}">
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
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
            <div class="alert alert-warning">No hay ventas registradas.</div>
        </c:otherwise>
    </c:choose>

    <a href="admin.jsp" class="btn btn-secondary mt-3">← Volver al Panel</a>
</body>
</html>
