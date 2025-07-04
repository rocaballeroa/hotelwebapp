<%-- 
    Document   : clientes
    Created on : 20 jun. 2025, 00:34:59
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hotel.modelo.Usuario" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    <title>Lista de Clientes</title>
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="css/estilos-vintage.css">
</head>

<body style="padding-top: 80px;">
<jsp:include page="navbar-admin.jsp" />


<div class="container mt-4">
    <h2 class="text-center mb-4" style="color: #365B73;">Clientes Registrados</h2>
<c:if test="${usuario.idTipoPersona == 2}">
    <div class="mb-3 text-start">
        <a href="UsuarioServlet?accion=nuevo&esCliente=true" class="btn btn-primary">
            Registrar Cliente
        </a>
    </div>
</c:if>
    <div class="table-responsive">
        <table class="table table-hover shadow-sm border rounded">
            <thead class="table-light">
                <tr>
                    <th>ID</th>
                    <th>Documento</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Correo</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cliente" items="${clientes}">
                    <tr>
                        <td>${cliente.idPersona}</td>
                        <td>${cliente.documento}</td>
                        <td>${cliente.nombre}</td>
                        <td>${cliente.apellido}</td>
                        <td>${cliente.correo}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</div>
<jsp:include page="footer.jsp" />
</body>
</html>
