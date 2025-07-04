<%-- 
    Document   : recepcion_detalle
    Created on : 20 jun. 2025, 11:38:54
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.hotel.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || (usuario.getIdTipoPersona() != 1 && usuario.getIdTipoPersona() != 2)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Detalle de Recepción</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f9f9f9;
        }

        h2 {
            color: #2c3e50;
        }

        table {
            border-collapse: collapse;
            width: 50%;
        }

        td, th {
            padding: 10px;
            border: 1px solid #ccc;
        }

        th {
            text-align: left;
            background-color: #3498db;
            color: white;
        }

        .back-link {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #3498db;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<h2>Detalle de Recepción</h2>

<table>
    <tr>
        <th>Cliente</th>
        <td>${recepcion.nombreCliente}</td>
    </tr>
    <tr>
        <th>Habitación</th>
        <td>${recepcion.numeroHabitacion}</td>
    </tr>
    <tr>
        <th>Categoría</th>
        <td>${recepcion.categoriaDescripcion}</td>
    </tr>
    <tr>
        <th>Fecha Entrada</th>
        <td>${recepcion.fechaEntrada}</td>
    </tr>
    <tr>
        <th>Fecha Salida</th>
        <td>${recepcion.fechaSalida}</td>
    </tr>
    <tr>
        <th>Precio Inicial</th>
        <td>S/ ${recepcion.precioInicial}</td>
    </tr>
    <tr>
        <th>Adelanto</th>
        <td>S/ ${recepcion.adelanto}</td>
    </tr>
    <tr>
        <th>Restante</th>
        <td>S/ ${recepcion.precioRestante}</td>
    </tr>
    <tr>
        <th>Total Pagado</th>
        <td>S/ ${recepcion.totalPagado}</td>
    </tr>
    <tr>
        <th>Estado</th>
        <td>
            <c:choose>
                <c:when test="${recepcion.estadoFinal == 'CHECKOUT'}">🧹 Checkout</c:when>
                <c:when test="${recepcion.estadoFinal == 'ANULADO'}">❌ Anulado</c:when>
                <c:otherwise>✅ Activo</c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>

<%
    String rutaVolver = "RecepcionServlet?accion=listar";
    if (usuario.getIdTipoPersona() == 2) {
        rutaVolver = "RecepcionServlet?accion=empleado";
    }
%>
<a class="back-link" href="<%= rutaVolver %>">← Volver a la lista</a>

</body>
</html>
