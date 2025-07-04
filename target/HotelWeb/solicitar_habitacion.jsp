<%-- 
    Document   : solicitar_habitacion
    Created on : 21 jun. 2025, 12:54:10
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>Solicitar Habitación</title>
<link rel="stylesheet" href="css/estilos-vintage.css">
</head>
<body>
<jsp:include page="navbar-admin.jsp" />
<h2>Solicitar Habitación</h2>
<input type="hidden" name="accion" value="guardar">
<div class="container">
    <div class="row">
        <c:forEach var="h" items="${habitaciones}">
            <c:if test="${h.idEstadoHabitacion == 1}">
                <div class="col-lg-4 col-md-6 col-sm-12 mb-4">
                    <div class="card h-100 shadow rounded-4 border-0">
                        <img src="${pageContext.request.contextPath}/imagenes/${h.imagen}"
                             class="card-img-top rounded-top-4"
                             style="height: 200px; object-fit: cover;" />
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title fw-bold text-primary">Hab. ${h.numero}</h5>
                            <p class="mb-1"><strong>📝 Detalle:</strong> ${h.detalle}</p>
                            <p class="mb-1"><strong>💲 Precio:</strong> S/. ${h.precio}</p>
                            <p class="mb-1"><strong>🏢 Piso:</strong> ${h.idPiso}</p>
                            <p class="mb-1"><strong>🏷️ Categoría:</strong> ${h.categoriaDescripcion}</p>
                            <button type="button"
                                    class="btn btn-primary mt-auto w-100"
                                    onclick="abrirModal('${h.idHabitacion}', '${h.numero}', '${h.categoriaDescripcion}', '${h.precio}')">
                                📩 Seleccionar
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
</div>

<!-- 🌟 MODAL CON ESTILO -->
<div id="modal">
    <div class="contenido">
        <h3>Reservar Habitación <span id="habitacionTitulo"></span></h3>
       <form action="RecepcionServlet" method="post">
    <input type="hidden" name="accion" value="guardar">
    <input type="hidden" name="idCliente" value="<%= usuario.getIdPersona() %>">
    <input type="hidden" id="idHabitacionInput" name="idHabitacion">

    <div class="form-group">
        <label>Fecha de Salida:</label>
        <input type="datetime-local" name="fechaSalida" required />
    </div>

    <div class="form-group">
        <label>Precio Inicial:</label>
        <input type="number" step="0.1" id="precioInput" name="precioInicial" required />
    </div>

    <div class="form-group">
        <label>Adelanto:</label>
        <input type="number" step="0.1" name="adelanto" required />
    </div>

    <div style="text-align: right; margin-top: 15px;">
        <button type="submit" class="btn confirmar">Confirmar Reserva</button>
        <button type="button" class="btn cancelar" onclick="cerrarModal()">Cancelar</button>
    </div>
</form>

    </div>
</div>


<script>
    function abrirModal(id, numero, categoria, precio) {
        document.getElementById('modal').style.display = 'flex';
        document.getElementById('habitacionTitulo').innerText = numero + ' - ' + categoria;
        document.getElementById('idHabitacionInput').value = id;
        document.getElementById('precioInput').value = precio;
    }

    function cerrarModal() {
        document.getElementById('modal').style.display = 'none';
    }
</script>
<jsp:include page="footer.jsp" />
</body>
</html>
