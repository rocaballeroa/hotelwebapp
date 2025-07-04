<%-- 
    Document   : error
    Created on : 22 jun. 2025, 11:10:30
    Author     : User
--%>

<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error en la Aplicación</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8d7da;
            color: #721c24;
            padding: 30px;
        }

        .container {
            background-color: #f5c6cb;
            padding: 20px;
            border-radius: 8px;
            border: 1px solid #f5b7b1;
        }

        h1 {
            color: #721c24;
        }

        pre {
            background-color: #fff;
            padding: 10px;
            border: 1px solid #ddd;
            overflow-x: auto;
            white-space: pre-wrap;
        }

        .btn {
            margin-top: 20px;
            display: inline-block;
            background-color: #c82333;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 6px;
        }

        .btn:hover {
            background-color: #a71d2a;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>💥 Se ha producido un error</h1>

        <%
            if (exception != null) {
        %>
            <p><strong>Mensaje:</strong> <%= exception.getMessage() %></p>
            <p><strong>Tipo de error:</strong> <%= exception.getClass().getName() %></p>
        <%
            } else {
        %>
            <p><strong>No se encontró información de error.</strong></p>
        <%
            }
        %>

        <h3>🔍 Detalles:</h3>
        <pre>
<%
    if (exception != null) {
        exception.printStackTrace(new java.io.PrintWriter(out));
    }
%>
        </pre>

        <a href="index.jsp" class="btn">Volver al Inicio</a>
    </div>
</body>
</html>
