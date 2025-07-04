<%-- 
    Document   : editar_categoria
    Created on : 19 jun. 2025, 20:12:00
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hotel.modelo.Categoria" %>
<%
    Categoria categoria = (Categoria) request.getAttribute("categoria");
    boolean esNuevo = (categoria == null);
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= esNuevo ? "Nueva Categoría" : "Editar Categoría" %></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">

    <h2><%= esNuevo ? "Nueva Categoría" : "Editar Categoría" %></h2>

    <form action="CategoriaServlet" method="post">
        <% if (!esNuevo) { %>
            <input type="hidden" name="id" value="<%= categoria.getIdCategoria() %>"/>
        <% } %>

        <div class="mb-3">
            <label class="form-label">Descripción:</label>
            <input type="text" name="descripcion" class="form-control" required
                   value="<%= esNuevo ? "" : categoria.getDescripcion() %>">
        </div>

        <div class="form-check mb-3">
            <input class="form-check-input" type="checkbox" name="estado" id="estado"
                   <%= !esNuevo && categoria.isEstado() ? "checked" : "" %>>
            <label class="form-check-label" for="estado">Activo</label>
        </div>

        <button type="submit" class="btn btn-success">Guardar</button>
        <a href="CategoriaServlet?accion=listar" class="btn btn-secondary">Cancelar</a>
    </form>

</body>
</html>

