<%-- 
    Document   : venta_ya_registrada
    Created on : 23 jun. 2025, 00:09:45
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.hotel.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || (usuario.getIdTipoPersona() != 2 && usuario.getIdTipoPersona() != 3)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Venta ya registrada</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f0f2f5;
            padding: 40px;
            color: #2c3e50;
        }
        .mensaje {
            background: #fff3cd;
            padding: 25px;
            border: 1px solid #ffeeba;
            border-radius: 8px;
            max-width: 600px;
            margin: auto;
            text-align: center;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h2 {
            color: #d35400;
        }
        .btn-volver {
            margin-top: 20px;
            background-color: #3498db;
            color: white;
            padding: 10px 18px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            display: inline-block;
        }
        .btn-volver:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>

<div class="mensaje">
    <h2>⚠️ Venta ya registrada</h2>
    <p>Ya existe una venta registrada para esta recepción.</p>
    <p>Si deseas ver el detalle o generar la factura, dirígete al módulo de ventas o reportes.</p>

    <c:choose>
    <c:when test="${usuario.idTipoPersona == 2}">
        <a href="RecepcionServlet?accion=empleado" class="btn-volver">🔙 Volver a Recepciones</a>
    </c:when>
    <c:when test="${usuario.idTipoPersona == 3}">
        <a href="RecepcionServlet?accion=verReservas" class="btn-volver">🔙 Volver a Recepciones</a>
    </c:when>
</c:choose>

</div>
<jsp:include page="footer.jsp" />
</body>
</html>
