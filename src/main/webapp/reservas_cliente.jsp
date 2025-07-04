<%-- 
    Document   : reservas_cliente
    Created on : 21 jun. 2025, 13:36:48
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <title>Mis Reservas</title>
    <link rel="stylesheet" href="css/estilos-vintage.css">
</head>
<body class="bg-light">
<jsp:include page="navbar-admin.jsp" />

<div class="container mt-5 mb-4">
    <h2 class="text-center mb-4">🛏️ Mis Reservas</h2>

    <c:choose>
        <c:when test="${not empty reservas}">

            <!-- 🔵 Activas -->
            <h4 class="mb-3 text-success">✅ Reservas Activas</h4>
            <div class="row">
                <c:forEach var="r" items="${reservas}">
                    <c:if test="${r.idEstadoReserva == 3}">
                        <div class="col-md-6 col-lg-4">
                            <div class="tarjeta">
                                <h5 class="fw-bold">Habitación ${r.numeroHabitacion}</h5>
                                <p><strong>Categoría:</strong> ${r.categoriaDescripcion}</p>
                                <p><strong>Entrada:</strong> ${r.fechaEntrada}</p>
                                <p><strong>Salida:</strong> ${r.fechaSalida}</p>
                                <p><strong>Total Pagado:</strong> S/. ${r.totalPagado}</p>
                                <c:choose>
  <c:when test="${r.idEstadoReserva == 1}">PENDIENTE</c:when>
  <c:when test="${r.idEstadoReserva == 2}">CONFIRMADA</c:when>
  <c:when test="${r.idEstadoReserva == 3}">ACTIVA</c:when>
  <c:when test="${r.idEstadoReserva == 4}">FINALIZADA</c:when>
  <c:when test="${r.idEstadoReserva == 5}">CANCELADA</c:when>
  <c:otherwise>DESCONOCIDO</c:otherwise>
</c:choose>


                                <div class="btn-group-custom">
                                    <form method="get" action="VentaServlet">
    <input type="hidden" name="accion" value="preparar">
    <input type="hidden" name="idRecepcion" value="${r.idRecepcion}">
    <button type="submit" class="btn btn-sm btn-success btn-igual">✅ Confirmar Salida</button>
</form>

                                    <form method="get" action="RecepcionServlet">
                                        <input type="hidden" name="accion" value="anular">
                                        <input type="hidden" name="id" value="${r.idRecepcion}">
                                        <button type="submit" class="btn btn-sm btn-danger btn-igual">❌ Cancelar</button>
                                    </form>
                                    <a href="ConsumoProductoServlet?accion=listar&idHabitacion=${r.idHabitacion}" class="btn btn-sm btn-primary btn-igual">🧾 Ver Consumos</a>
                                    <a href="ConsumoProductoServlet?accion=nuevo&idHabitacion=${r.idHabitacion}&idRecepcion=${r.idRecepcion}&origen=cliente"
   class="btn btn-sm btn-secondary btn-igual">🛒 Agregar Producto</a>


                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>

            <!-- 🟡 Completadas -->
            <h4 class="mt-5 mb-3 text-primary">✔️ Reservas Completadas</h4>
            <div class="row">
                <c:forEach var="r" items="${reservas}">
                    <c:if test="${r.idEstadoReserva == 4}">
                        <div class="col-md-6 col-lg-4">
                            <div class="tarjeta tarjeta-completada">
                                <h5 class="fw-bold">Habitación ${r.numeroHabitacion}</h5>
                                <p><strong>Categoría:</strong> ${r.categoriaDescripcion}</p>
                                <p><strong>Entrada:</strong> ${r.fechaEntrada}</p>
                                <p><strong>Salida:</strong> ${r.fechaSalida}</p>
                                <p><strong>Total Pagado:</strong> S/. ${r.totalPagado}</p>
                                <c:choose>
  <c:when test="${r.idEstadoReserva == 1}">PENDIENTE</c:when>
  <c:when test="${r.idEstadoReserva == 2}">CONFIRMADA</c:when>
  <c:when test="${r.idEstadoReserva == 3}">ACTIVA</c:when>
  <c:when test="${r.idEstadoReserva == 4}">FINALIZADA</c:when>
  <c:when test="${r.idEstadoReserva == 5}">CANCELADA</c:when>
  <c:otherwise>DESCONOCIDO</c:otherwise>
</c:choose>

                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>

            <!-- 🔴 Canceladas -->
            <h4 class="mt-5 mb-3 text-danger">❌ Reservas Canceladas</h4>
            <div class="row">
                <c:forEach var="r" items="${reservas}">
                    <c:if test="${r.idEstadoReserva == 5}">
                        <div class="col-md-6 col-lg-4">
                            <div class="tarjeta tarjeta-cancelada">
                                <h5 class="fw-bold">Habitación ${r.numeroHabitacion}</h5>
                                <p><strong>Categoría:</strong> ${r.categoriaDescripcion}</p>
                                <p><strong>Entrada:</strong> ${r.fechaEntrada}</p>
                                <p><strong>Salida:</strong> ${r.fechaSalida}</p>
                                <p><strong>Total Pagado:</strong> S/. ${r.totalPagado}</p>
                                <p><strong>Estado:</strong> 
  <span class="estado
    <c:choose>
      <c:when test="${r.idEstadoReserva == 3}"> text-success</c:when>
      <c:when test="${r.idEstadoReserva == 4}"> text-primary</c:when>
      <c:when test="${r.idEstadoReserva == 5}"> text-danger</c:when>
    </c:choose>">
    <c:choose>
      <c:when test="${r.idEstadoReserva == 3}">ACTIVA</c:when>
      <c:when test="${r.idEstadoReserva == 4}">FINALIZADA</c:when>
      <c:when test="${r.idEstadoReserva == 5}">CANCELADA</c:when>
      <c:otherwise>DESCONOCIDO</c:otherwise>
    </c:choose>
  </span>
</p>

                                <div class="etiqueta-cancelada">❌ Cancelada</div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>

        </c:when>
        <c:otherwise>
            <div class="alert alert-info text-center">No tienes reservas registradas aún.</div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="footer.jsp" />

<!-- ✅ Modal de Confirmación de Consumo -->
<div id="consumoModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="cerrarModal()">&times;</span>
        <h2>🛒 ¡Producto Registrado!</h2>
        <p>El consumo ha sido registrado exitosamente.</p>
    </div>
</div>

<style>
    .modal {
        display: none; 
        position: fixed; 
        z-index: 9999;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0,0,0,0.5); 
    }

    .modal-content {
        background-color: #fff;
        margin: 15% auto;
        padding: 30px;
        border: 1px solid #ccc;
        width: 90%;
        max-width: 400px;
        border-radius: 10px;
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
        document.getElementById('consumoModal').style.display = 'none';
        window.history.replaceState({}, document.title, window.location.pathname);
    }

    // Mostrar modal si URL contiene ?consumo=ok
    const params = new URLSearchParams(window.location.search);
    if (params.get("consumo") === "ok") {
        document.getElementById("consumoModal").style.display = "block";
    }
</script>

</body>
</html>
