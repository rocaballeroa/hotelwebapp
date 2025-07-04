<%-- 
    Document   : dashboardLayout
    Created on : 2 jul. 2025, 19:13:35
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel | <%= rol %></title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            color: #000;
            margin: 0;
        }

        .wrapper {
            display: flex;
        }

        .sidebar {
            width: 250px;
            height: 100vh;
            position: fixed;
            top: 0;
            left: 0;
            background: #fff;
            padding: 1rem;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            overflow-y: auto;
        }

        .sidebar h4 {
            text-align: center;
            margin-bottom: 1.5rem;
        }

        .sidebar .nav-link {
            color: #000;
            padding: 0.7rem 1rem;
            margin-bottom: 0.5rem;
            border-radius: 5px;
            transition: background 0.2s;
        }

        .sidebar .nav-link:hover,
        .sidebar .nav-link.active {
            background-color: #f1f1f1;
        }

        .main {
            margin-left: 250px;
            width: calc(100% - 250px);
            padding: 2rem;
        }

        .topbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .user-info img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
        }

        .loader {
            display: none;
            text-align: center;
            margin-top: 50px;
        }

        .loader.show {
            display: block;
        }
    </style>
</head>
<body class="bg-light">

<div class="wrapper">
    <!-- Sidebar -->
    <nav class="sidebar">
        <h4>BRISA ANDINA</h4>
        <ul class="nav flex-column">
            <li class="nav-item">
                <a href="dashboard.jsp" class="nav-link" data-page="admin.jsp">
                    <i class="bi bi-house"></i> Inicio
                </a>
            </li>
            <li class="nav-item">
                <a href="RecepcionServlet?accion=listar" class="nav-link" data-page="RecepcionServlet?accion=listar">
                    <i class="bi bi-clipboard-data"></i> Recepciones
                </a>
            </li>
            <li class="nav-item">
                <a href="HabitacionServlet" class="nav-link" data-page="HabitacionServlet">
                    <i class="bi bi-door-closed"></i> Habitaciones
                </a>
            </li>
            <li class="nav-item">
                <a href="ProductoServlet" class="nav-link" data-page="ProductoServlet">
                    <i class="bi bi-box-seam"></i> Productos
                </a>
            </li>
            <li class="nav-item">
                <a href="ReporteServlet?accion=reporteAdmin" class="nav-link" data-page="ReporteServlet?accion=reporteAdmin">
                    <i class="bi bi-bag-dash"></i> Ventas
                </a>
            </li>
        </ul>
    </nav>

    <!-- Main -->
    <div class="main">
        <div class="topbar">
            <h5 class="mb-0">Panel | <%= rol %></h5>
            
        </div>

        <!-- Loader -->
        <div class="loader" id="loader">
            <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Cargando...</span>
            </div>
            <p class="mt-2">Cargando contenido...</p>
        </div>

        <!-- Contenido dinámico -->
        <div id="contenido"></div>
    </div>
</div>

<!-- Bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- AJAX para cargar contenido -->
<script>
    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();

            document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
            this.classList.add('active');

            const pageUrl = this.getAttribute('data-page');
            const loader = document.getElementById('loader');
            const contenido = document.getElementById('contenido');

            loader.classList.add('show');
            contenido.innerHTML = '';

            fetch(pageUrl)
                .then(res => res.text())
                .then(data => {
                    setTimeout(() => {
                        loader.classList.remove('show');
                        contenido.innerHTML = data;
                    }, 500); // animación de carga
                })
                .catch(() => {
                    loader.classList.remove('show');
                    contenido.innerHTML = '<div class="alert alert-danger">Error al cargar contenido.</div>';
                });
        });
    });

    // Cargar la página inicial automáticamente
    window.addEventListener('DOMContentLoaded', () => {
        document.querySelector('.nav-link').click();
    });
</script>
</body>
</html>
