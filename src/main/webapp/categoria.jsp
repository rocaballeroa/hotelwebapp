<%-- 
    Document   : categoria
    Created on : 19 jun. 2025, 20:11:19
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.hotel.modelo.Categoria" %>
<%
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Categorías</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

    <!-- ✅ Incluimos el navbar aquí, ya dentro del body -->
    <jsp:include page="navbar-admin.jsp" />

    <div class="container mt-5">
        <h2 class="mb-4">Categorías</h2>

        <a href="editar_categoria.jsp" class="btn btn-success mb-3">+ Nueva Categoría</a>

        <table class="table table-bordered table-striped">
            <thead class="table-primary">
                <tr>
                    <th>ID</th>
                    <th>Descripción</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
            <% if (categorias != null) {
                for (Categoria c : categorias) { %>
                <tr>
                    <td><%= c.getIdCategoria() %></td>
                    <td><%= c.getDescripcion() %></td>
                    <td><%= c.isEstado() ? "Activo" : "Inactivo" %></td>
                    <td>
                        <a href="CategoriaServlet?accion=editar&id=<%= c.getIdCategoria() %>" class="btn btn-primary btn-sm">Editar</a>
                        <a href="CategoriaServlet?accion=eliminar&id=<%= c.getIdCategoria() %>" class="btn btn-danger btn-sm"
                           onclick="return confirm('¿Está seguro de eliminar?')">Eliminar</a>
                    </td>
                </tr>
            <% }} %>
            </tbody>
        </table>
    </div>
<jsp:include page="footer.jsp" />
</body>
</html>
