<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.hotel.modelo.Usuario" %>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || usuario.getIdTipoPersona() != 1) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Lista de Recepciones</title>
    <!-- Estilos personalizados -->
    <link rel="stylesheet" href="css/estilos-vintage.css">
</head>
<body style="padding-top: 80px;">

<jsp:include page="navbar-admin.jsp" />

<div class="container mt-4">
    <h2 class="text-center mb-4" style="color: #365B73;">Recepciones Registradas</h2>

    <div class="d-flex justify-content-between mb-3">
        <a class="btn btn-registrar" href="RecepcionServlet?accion=nuevo">➕ Nueva Recepción</a>
        
    </div>

    <div class="table-responsive">
        <form method="get" action="RecepcionServlet" class="row g-3 mb-4">
    <input type="hidden" name="accion" value="filtrar" />

    <div class="col-md-4">
        <label class="form-label">Estado de Reserva</label>
        <select name="estado" class="form-select">
            <option value="">-- Todos --</option>
            <option value="ACTIVA" ${param.estado == 'ACTIVA' ? 'selected' : ''}>Activa</option>
            <option value="FINALIZADA" ${param.estado == 'FINALIZADA' ? 'selected' : ''}>Finalizada</option>
            <option value="CANCELADA" ${param.estado == 'CANCELADA' ? 'selected' : ''}>Cancelada</option>
        </select>
    </div>

    <div class="col-md-4">
        <label class="form-label">Cliente</label>
        <input type="text" name="cliente" class="form-control" value="${param.cliente}" placeholder="Nombre del cliente" />
    </div>

    <div class="col-md-4">
        <label class="form-label">Ordenar por fecha</label>
        <select name="orden" class="form-select">
            <option value="">-- Sin ordenar --</option>
            <option value="reciente" ${param.orden == 'reciente' ? 'selected' : ''}>Más recientes</option>
            <option value="antiguo" ${param.orden == 'antiguo' ? 'selected' : ''}>Más antiguos</option>
        </select>
    </div>

    <div class="col-md-12 d-flex gap-2 justify-content-end">
        <button type="submit" class="btn btn-primary">🔍 Filtrar</button>
        <a href="RecepcionServlet?accion=listar" class="btn btn-secondary">❌ Limpiar</a>
    </div>
</form>

        <table class="table table-hover shadow-sm border rounded">
            <thead class="table-light">
                <tr>
                    <th>Cliente</th>
                    <th>Habitación</th>
                    <th>Categoría</th>
                    <th>Fecha Entrada</th>
                    <th>Fecha Salida</th>
                    <th>Precio</th>
                    <th>Adelanto</th>
                    <th>Restante</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="r" items="${recepciones}">
                    <tr>
                        <td>${r.nombreCliente}</td>
                        <td>${r.numeroHabitacion}</td>
                        <td>${r.categoriaDescripcion}</td>
                        <td>${r.fechaEntrada}</td>
                        <td>${r.fechaSalida}</td>
                        <td>S/ ${r.precioInicial}</td>
                        <td>S/ ${r.adelanto}</td>
                        <td>S/ ${r.precioRestante}</td>
                        <td>
                      <c:choose>
    <c:when test="${r.estadoReservaDescripcion == 'FINALIZADA'}">
        <span class="text-warning fw-bold">🧹 Checkout</span>
    </c:when>
    <c:when test="${r.estadoReservaDescripcion == 'CANCELADA'}">
        <span class="text-danger fw-bold">❌ Anulado</span>
    </c:when>
    <c:otherwise>
        <span class="text-success fw-bold">✅ ${r.estadoReservaDescripcion}</span>
    </c:otherwise>
</c:choose>



                        </td>
                        <td>
                            <a href="RecepcionServlet?accion=detalle&id=${r.idRecepcion}" class="btn btn-sm btn-beige">🔍 </a>

                            <c:choose>
                                <c:when test="${r.estadoReservaDescripcion == 'ACTIVA'}">


                                    <a href="RecepcionServlet?accion=anular&id=${r.idRecepcion}" class="btn btn-sm btn-eliminar"
                                       onclick="return confirm('¿Estás seguro de anular esta recepción?');">❌</a>
                                    <a href="RecepcionServlet?accion=checkout&id=${r.idRecepcion}" class="btn btn-sm btn-editar"
                                       onclick="return confirm('¿Confirmar Check-Out?');">🧹</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="RecepcionServlet?accion=reactivar&id=${r.idRecepcion}" class="btn btn-sm btn-verde"
                                       onclick="return confirm('¿Reactivar esta recepción?');">♻</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
