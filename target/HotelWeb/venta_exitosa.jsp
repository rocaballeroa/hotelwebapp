<%-- 
    Document   : venta_exitosa
    Created on : 22 jun. 2025, 15:54:40
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.hotel.modelo.Venta, com.hotel.modelo.DetalleVenta" %>
<!DOCTYPE html>
<html>
<head>
    <title>Venta Exitosa</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/estilos-vintage.css">
</head>

<body>

<div class="container my-5">
    <!-- Banner de éxito -->
    <div class="text-center mb-5">
        <div class="rounded-pill py-3 px-4 text-white fw-bold shadow"
             style="background: linear-gradient(90deg, #92A78C, #E0D5AD); font-size: 1.5rem;">
            ✅ Venta registrada con éxito
        </div>
    </div>

    <!-- Datos generales de la venta -->
    <div class="card mb-4 shadow-sm border-0 rounded-4">
        <div class="card-header text-white fw-semibold rounded-top-4" style="background-color: #92A78C;">
            <i class="bi bi-receipt-cutoff"></i> Detalles Generales
        </div>
        <div class="card-body bg-light rounded-bottom-4">
            <ul class="list-group list-group-flush">
                <li class="list-group-item d-flex justify-content-between">
                    <strong>ID Recepción:</strong> <span>${venta.idRecepcion}</span>
                </li>
                <li class="list-group-item d-flex justify-content-between">
                    <strong>Total:</strong> <span>S/ ${venta.total}</span>
                </li>
                <li class="list-group-item d-flex justify-content-between">
                    <strong>Estado:</strong> <span>${venta.estado}</span>
                </li>
            </ul>
        </div>
    </div>

    <!-- Detalle de productos vendidos -->
    <div class="card shadow-sm border-0 rounded-4">
        <div class="card-header text-white fw-semibold rounded-top-4" style="background-color: #92A78C;">
            🧾 Detalle de Productos Vendidos
        </div>
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-bordered table-hover mb-0 align-middle text-center"
                       style="--bs-table-bg: #fdfaf5;">
                    <thead style="background-color: #92A78C; color: #FAFBF6;">
                        <tr>
                            <th>#</th>
                            <th>ID Producto</th>
                            <th>Nombre</th>
                            <th>Cantidad</th>
                            <th>Precio Unitario</th>
                            <th>Subtotal</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="d" items="${detalles}" varStatus="s">
                            <tr class="${d.idProducto == 0 || d.idProducto == -1 ? 'table-warning' : ''}">
                                <td>${s.index + 1}</td>
                                <td>${d.idProducto}</td>
                                <td>${d.nombreProducto}</td>
                                <td>${d.cantidad}</td>
                                <td>S/ ${d.precioUnitario}</td>
                                <td>S/ ${d.subTotal}</td>
                            </tr>
                        </c:forEach>
                        <tr class="fw-bold" style="background-color: #E0D5AD;">
                            <td colspan="5" class="text-end">TOTAL</td>
                            <td>S/ ${venta.total}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Botón volver -->
    <div class="text-end mt-4">
        <c:choose>
            <c:when test="${usuario.idTipoPersona == 2}">
                <a href="RecepcionServlet?accion=empleado" class="btn btn-beige px-4">🔙 Volver a Recepciones</a>
            </c:when>
            <c:when test="${usuario.idTipoPersona == 3}">
                <a href="RecepcionServlet?accion=verReservas" class="btn btn-beige px-4">🔙 Volver a Mis Reservas</a>
            </c:when>
            <c:otherwise>
                <a href="RecepcionServlet" class="btn btn-beige px-4">🔙 Volver</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>



<jsp:include page="footer.jsp" />
</body>
</html>
