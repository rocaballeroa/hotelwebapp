<%-- 
    Document   : cliente
    Created on : 20 jun. 2025, 00:28:17
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hotel.modelo.Usuario" %>
<%@ page session="true" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || usuario.getIdTipoPersona() != 3) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Panel de Cliente</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background-color: #f4f4f4;
        }
        h1 {
            color: #2c3e50;
        }
        .menu {
            margin-top: 20px;
        }
        .menu a {
            display: inline-block;
            margin-right: 15px;
            padding: 10px 15px;
            background-color: #27ae60;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .menu a:hover {
            background-color: #1e8449;
        }
    </style>
</head>
<body>
<jsp:include page="navbar-admin.jsp" />
<h1>Bienvenido, <%= usuario.getNombre() + " " + usuario.getApellido() %> 🌟</h1>



<p>Este es tu panel como cliente del hotel.</p>



<!-- Modal de Confirmación -->
<div id="reservaModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="cerrarModal()">&times;</span>
        <h2>¡Reserva confirmada! 🛏️</h2>
        <p>Tu solicitud ha sido registrada exitosamente.</p>
    </div>
</div>

<style>
    .modal {
        display: none; 
        position: fixed; 
        z-index: 999;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0,0,0,0.4); 
    }

    .modal-content {
        background-color: #fff;
        margin: 15% auto;
        padding: 30px;
        border: 1px solid #ccc;
        width: 400px;
        border-radius: 12px;
        box-shadow: 0 5px 15px rgba(0,0,0,0.3);
        text-align: center;
        animation: aparecer 0.3s ease-out;
    }

    .close {
        color: #aaa;
        float: right;
        font-size: 24px;
        font-weight: bold;
        cursor: pointer;
    }

    .close:hover {
        color: #000;
    }

    @keyframes aparecer {
        from { opacity: 0; transform: translateY(-20px); }
        to { opacity: 1; transform: translateY(0); }
    }
</style>

<script>
    function cerrarModal() {
        document.getElementById('reservaModal').style.display = 'none';
        // Opcional: eliminar el parámetro de la URL después de mostrarlo
        window.history.replaceState({}, document.title, window.location.pathname);
    }

    // Mostrar el modal si el parámetro "reserva=ok" está presente
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get("reserva") === "ok") {
        document.getElementById('reservaModal').style.display = 'block';
    }
</script>

<jsp:include page="footer.jsp" />
</body>
</html>
