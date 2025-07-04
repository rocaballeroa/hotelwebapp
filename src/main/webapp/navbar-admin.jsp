<%-- 
    Document   : navbar-admin
    Created on : 23 jun. 2025, 12:02:45
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hotel.modelo.Usuario" %>

<%@ page import="com.hotel.modelo.Usuario" %>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String rol;
    int tipo = usuario.getIdTipoPersona();
    if (tipo == 1) {
        rol = "Administrador";
    } else if (tipo == 2) {
        rol = "Empleado";
    } else if (tipo == 3) {
        rol = "Cliente";
    } else {
        rol = "Desconocido";
    }

    String rutaImagen = (usuario.getImagen() != null && !usuario.getImagen().isEmpty())
            ? request.getContextPath() + "/" + usuario.getImagen()
            : "https://cdn-icons-png.flaticon.com/512/149/149071.png";

    String inicial = (usuario.getNombre() != null && !usuario.getNombre().isEmpty())
            ? usuario.getNombre().substring(0, 1).toUpperCase()
            : "?";
%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/estilos-vintage.css">

<nav class="navbar navbar-expand-lg navbar-vintage fixed-top w-100">
    <div class="container-fluid">
        <span class="navbar-brand"><%= rol %> - <%= usuario.getNombre() %></span>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbar" aria-controls="navbar" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <% if (tipo == 1) { %>
                    <li class="nav-item"><a class="nav-link" href="admin.jsp">Inicio</a></li>
                    <li class="nav-item"><a class="nav-link" href="HabitacionServlet">Habitaciones</a></li>
                    <li class="nav-item"><a class="nav-link" href="CategoriaServlet">Categorías</a></li>
                    <li class="nav-item"><a class="nav-link" href="UsuarioServlet">Usuarios</a></li>
                    <li class="nav-item"><a class="nav-link" href="ClienteServlet">Clientes</a></li>
                    <li class="nav-item"><a class="nav-link" href="RecepcionServlet?accion=listar">Recepciones</a></li>
                    <li class="nav-item"><a class="nav-link" href="ProductoServlet">Productos</a></li>
                    <li class="nav-item"><a class="nav-link" href="ReporteServlet?accion=reporteAdmin">Ventas</a></li>
                    <li class="nav-item"><a class="nav-link" href="prueba.jsp">nuevo</a></li>
                <% } else if (tipo == 2) { %>
                    <li class="nav-item"><a class="nav-link" href="empleado.jsp">Inicio</a></li>
                    <li class="nav-item"><a class="nav-link" href="RecepcionServlet?accion=empleado">Recepcionar</a></li>
                    <li class="nav-item"><a class="nav-link" href="ClienteServlet">Clientes</a></li>
                     <li class="nav-item"><a class="nav-link" href="ReporteServlet?accion=reporteHoy">Ventas del Día</a></li>
                <% } else if (tipo == 3) { %>
                     <li class="nav-item"><a class="nav-link" href="cliente.jsp">Inicio</a></li>
                    <li class="nav-item"><a class="nav-link" href="RecepcionServlet?accion=verReservas">Mis reservas</a></li>
                    <li class="nav-item"><a class="nav-link" href="RecepcionServlet?accion=solicitar">Habitaciones</a></li>
                    <li class="nav-item"><a class="nav-link" href="ConsumoProductoServlet?accion=verTodo">Mis Consumos</a></li>
                 <% } %>    
            </ul>

            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle d-flex align-items-center gap-2" href="#" id="userDropdown"
                       role="button" data-bs-toggle="dropdown" aria-expanded="false" style="color: #F7CD82 !important;">
                        <% if (usuario.getImagen() != null && !usuario.getImagen().isEmpty()) { %>
                            <img src="<%= rutaImagen %>" alt="Perfil" width="32" height="32" class="rounded-circle border border-light shadow-sm" />
                        <% } else { %>
                            <div class="rounded-circle bg-secondary d-flex justify-content-center align-items-center text-white fw-bold"
                                 style="width: 32px; height: 32px; font-size: 16px;">
                                <%= inicial %>
                            </div>
                        <% } %>
                        <span class="fw-bold"><%= usuario.getNombre() %> <%= usuario.getApellido() %></span>
                    </a>

                    <ul class="dropdown-menu dropdown-menu-end shadow-lg border-0 p-3 rounded-3" aria-labelledby="userDropdown" style="min-width: 250px;">
                        <li class="text-center mb-3">
                            <img src="<%= rutaImagen %>" width="70" height="70"
                                 class="rounded-circle mb-2 border border-2 border-light shadow-sm"
                                 alt="Imagen de perfil">
                            <p class="mb-0 fw-bold"><%= usuario.getNombre() %> <%= usuario.getApellido() %></p>
                            <p class="mb-0 text-muted"><%= usuario.getCorreo() %></p>
                            <p class="mb-0 text-muted small"><%= usuario.getTipoDocumento() %>: <%= usuario.getDocumento() %></p>
                            <p class="mb-0 text-primary"><small>👤 <%= rol %></small></p>
                        </li>

                        <li><hr class="dropdown-divider my-2"></li>

                        <li><a class="dropdown-item" href="UsuarioServlet?accion=editar&id=<%= usuario.getIdPersona() %>">📝 Mi perfil</a></li>
                        <li><a class="dropdown-item" href="UsuarioServlet?accion=foto">🖼️ Cambiar foto</a></li>
                        <li><hr class="dropdown-divider my-2"></li>
                        <li class="text-center"><a href="CerrarSesionServlet" class="btn btn-sm btn-danger px-3">🔒 Cerrar sesión</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
