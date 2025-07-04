<%-- 
    Document   : admin
    Created on : 20 jun. 2025, 00:08:07
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Panel de Administrador</title>
    <link rel="stylesheet" href="css/estilos-vintage.css">

</head>

<body>

<jsp:include page="navbar-admin.jsp" />

<main class="container mt-5">

    <!-- Carrusel animado -->
    <div id="carruselHotel" class="carousel slide shadow rounded mb-5" data-bs-ride="carousel">
        <div class="carousel-inner rounded">
            <div class="carousel-item active">
                <img src="imagenes/windsor2.jpg" class="d-block w-100" style="height: 450px; object-fit: cover;" alt="Recepción hotel">
                <div class="carousel-caption bg-dark bg-opacity-50 p-3 rounded">
                    <h4>Bienvenido al Panel del Administrador</h4>
                    <p>Controla reservas, habitaciones, productos y clientes desde aquí.</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="imagenes/windsor5.jpg" class="d-block w-100" style="height: 450px; object-fit: cover;" alt="Habitación hotel">
                <div class="carousel-caption bg-dark bg-opacity-50 p-3 rounded">
                    <h4>Habitaciones & Servicios</h4>
                    <p>Administra la disponibilidad de cuartos y servicios consumidos.</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="imagenes/imagencarrusel.jpg" class="d-block w-100" style="height: 450px; object-fit: cover;" alt="Atención al cliente">
                <div class="carousel-caption bg-dark bg-opacity-50 p-3 rounded">
                    <h4>Clientes Satisfechos</h4>
                    <p>Brinda un servicio excepcional gracias a un sistema bien gestionado.</p>
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carruselHotel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon"></span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carruselHotel" data-bs-slide="next">
            <span class="carousel-control-next-icon"></span>
        </button>
    </div>

    <!-- Imagen + Descripción -->
    <div class="row align-items-center mb-5">
        <div class="col-md-6">
            <img src="imagenes/imagencarrusel2.jpg" class="img-fluid rounded shadow" alt="Hotel Lobby">
        </div>
        <div class="col-md-6">
            <h3 class="mb-3">¿Qué puedes hacer desde aquí?</h3>
            <p class="text-muted" style="text-align: justify;">
                Este sistema está diseñado para facilitarte la gestión completa del hotel. Desde la administración de habitaciones, categorías, productos hasta las recepciones y el registro de clientes. Todo en un entorno intuitivo, elegante y potente.
            </p>
        </div>
    </div>

    <!-- Métricas en cards -->
    <div class="row text-center g-4 mb-5">
        <div class="col-md-3">
            <div class="card shadow h-100">
                <div class="card-body">
                    <h1>🛌</h1>
                    <h5>Habitaciones</h5>
                    <p class="text-muted">Gestiona disponibilidad y mantenimiento.</p>
                </div>
                <div class="card-footer bg-transparent">
                    <a href="HabitacionServlet" class="btn btn-sm btn-verde">Ver</a>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card shadow h-100">
                <div class="card-body">
                    <h1>📦</h1>
                    <h5>Productos</h5>
                    <p class="text-muted">Revisa stock y consumos recientes.</p>
                </div>
                <div class="card-footer bg-transparent">
                    <a href="ProductoServlet" class="btn btn-sm btn-verde">Ver</a>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card shadow h-100">
                <div class="card-body">
                    <h1>👥</h1>
                    <h5>Clientes</h5>
                    <p class="text-muted">Consulta historial y datos de clientes.</p>
                </div>
                <div class="card-footer bg-transparent">
                    <a href="ClienteServlet" class="btn btn-sm btn-verde">Ver</a>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card shadow h-100">
                <div class="card-body">
                    <h1>📋</h1>
                    <h5>Recepciones</h5>
                    <p class="text-muted">Control de entradas, salidas y pagos.</p>
                </div>
                <div class="card-footer bg-transparent">
                    <a href="RecepcionServlet?accion=listar" class="btn btn-sm btn-verde">Ver</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Últimos movimientos -->
    <div class="mb-5">
        <h4 class="mb-3">📊 Últimas Recepciones</h4>
        <table class="table table-hover shadow-sm">
            <thead class="table-light">
                <tr>
                    <th>Cliente</th>
                    <th>Habitación</th>
                    <th>Check-in</th>
                    <th>Check-out</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Juan Pérez</td>
                    <td>203</td>
                    <td>2025-06-20</td>
                    <td>2025-06-22</td>
                    <td><span class="badge bg-success">Completado</span></td>
                </tr>
                <tr>
                    <td>Ana Torres</td>
                    <td>105</td>
                    <td>2025-06-22</td>
                    <td>-</td>
                    <td><span class="badge bg-warning text-dark">Activo</span></td>
                </tr>
                <tr>
                    <td>Mario Ríos</td>
                    <td>302</td>
                    <td>2025-06-21</td>
                    <td>2025-06-22</td>
                    <td><span class="badge bg-danger">Cancelado</span></td>
                </tr>
            </tbody>
        </table>
    </div>

    <!-- Paneles informativos -->
    <div class="row mb-5 g-4">
        <div class="col-md-6">
            <div class="p-4 rounded bg-light shadow-sm">
                <h5 class="mb-3">🔔 Recordatorio Diario</h5>
                <ul class="list-unstyled">
                    <li>• Revisa los check-ins del día</li>
                    <li>• Actualiza productos consumidos</li>
                    <li>• Verifica habitaciones por limpiar</li>
                    <li>• Genera reportes de salida</li>
                </ul>
            </div>
        </div>
        <div class="col-md-6">
            <div class="p-4 rounded bg-light shadow-sm">
                <h5 class="mb-3">🔐 Seguridad del sistema</h5>
                <p>Recuerda cerrar sesión si te ausentas del sistema.</p>
                <a href="CerrarSesionServlet" class="btn btn-danger btn-sm">Cerrar sesión</a>
            </div>
        </div>
    </div>

</main>

<jsp:include page="footer.jsp" />


</body>
</html>
