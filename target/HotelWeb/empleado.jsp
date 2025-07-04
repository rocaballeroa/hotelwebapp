<%-- 
    Document   : empleado
    Created on : 20 jun. 2025, 00:14:13
    Author     : User
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel de Empleado</title>


    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="css/estilos-vintage.css">
</head>

<body>

    <!-- Navbar general (reutilizable) -->
    <jsp:include page="navbar-admin.jsp" />

    <div class="container mt-5">
        <div class="row align-items-center">
            <div class="col-md-6">
                <h2 class="mb-4 text-success">👋 Bienvenido al panel del empleado</h2>
                <p class="lead">Aquí puedes realizar acciones esenciales como recepcionar clientes o consultar información de tus funciones dentro del hotel.</p>
                <a href="RecepcionServlet?accion=empleado" class="btn btn-verde me-2">📥 Recepcionar Clientes</a>
                <a href="CerrarSesionServlet" class="btn btn-eliminar">🔒 Cerrar Sesión</a>
            </div>
            <div class="col-md-6">
                <img src="imagenes/windsor6.jpg" alt="Recepción del hotel" class="img-fluid rounded shadow">
            </div>
        </div>
    </div>

    <!-- Footer general -->
    <jsp:include page="footer.jsp" />


</body>
</html>
