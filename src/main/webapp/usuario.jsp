<%-- 
    Document   : usuario
    Created on : 19 jun. 2025, 23:51:32
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
        <link rel="stylesheet" href="css/estilos-vintage.css">

</head>
<body class="bg-light">

<jsp:include page="navbar-admin.jsp" />

<div class="container mt-5">
    <h2 class="mb-4 text-primary">👨‍💼 Usuarios (Administradores y Empleados)</h2>

    <a href="UsuarioServlet?accion=nuevo" class="btn btn-registrar mb-3">
        <i class="bi bi-person-plus-fill"></i> Registrar Nuevo Usuario
    </a>

    <table class="table table-bordered table-hover">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Documento</th>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Tipo</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${usuarios}">
            <tr>
                <td>${u.idPersona}</td>
                <td>${u.tipoDocumento} - ${u.documento}</td>
                <td>${u.nombre} ${u.apellido}</td>
                <td>${u.correo}</td>
                <td>
                    <c:choose>
                        <c:when test="${u.idTipoPersona == 1}">
                            <span class="badge bg-primary">Administrador</span>
                        </c:when>
                        <c:when test="${u.idTipoPersona == 2}">
                            <span class="badge bg-info text-dark">Empleado</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-secondary">Otro</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <span class="badge ${u.estado ? 'bg-success' : 'bg-danger'}">
                        ${u.estado ? 'Activo' : 'Inactivo'}
                    </span>
                </td>
                <td>
                    <a href="UsuarioServlet?accion=editar&id=${u.idPersona}" class="btn btn-sm btn-primary">
                        <i class="bi bi-pencil-square"></i> Editar
                    </a>
                    <a href="UsuarioServlet?accion=eliminar&id=${u.idPersona}"
                       onclick="return confirm('¿Eliminar?')"
                       class="btn btn-sm btn-danger">
                        <i class="bi bi-trash3-fill"></i> Eliminar
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
