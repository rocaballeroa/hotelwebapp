<%-- 
    Document   : recepcion_listar_empleado
    Created on : 20 jun. 2025, 17:15:41
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.hotel.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || usuario.getIdTipoPersona() != 2) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Recepciones - Empleado</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="css/estilos-vintage.css">
</head>
<body>

<jsp:include page="navbar-admin.jsp" />

<div class="container-fluid mt-4">
    <div class="container text-center">
        <h2 class="titulo-panel">🛎️ Recepciones Registradas</h2>
    </div>

    <!-- FILTRO -->
    <form method="get" action="RecepcionServlet" class="row g-2 mb-3 align-items-end">
        <input type="hidden" name="accion" value="empleado">
        
       <div class="col-sm-3">
    <label for="estado">Estado</label>
   <select name="estado" id="estado" class="form-select">
    <option value="">Todos</option>
    <option value="ACTIVO" ${param.estado == 'ACTIVO' ? 'selected' : ''}>Activo</option>
    <option value="CHECKOUT" ${param.estado == 'CHECKOUT' ? 'selected' : ''}>Checkout</option>
    <option value="ANULADO" ${param.estado == 'ANULADO' ? 'selected' : ''}>Anulado</option>
</select>

</div>


        <div class="col-sm-3">
            <label for="cliente">Cliente</label>
            <input type="text" name="cliente" id="cliente" value="${param.cliente}" class="form-control" placeholder="Buscar por cliente">
        </div>

        <div class="col-sm-3">
            <label for="orden">Orden</label>
            <select name="orden" id="orden" class="form-select">
                <option value="">Sin orden</option>
                <option value="reciente" ${param.orden == 'reciente' ? 'selected' : ''}>Más reciente</option>
                <option value="antiguo" ${param.orden == 'antiguo' ? 'selected' : ''}>Más antiguo</option>
            </select>
        </div>

        <div class="col-sm-3">
            <button type="submit" class="btn btn-primary w-100">
                <i class="bi bi-funnel"></i> Filtrar
            </button>
        </div>
    </form>

    <div class="d-flex justify-content-start gap-2 mb-3 flex-wrap">
        <a class="btn btn-success" href="RecepcionServlet?accion=nuevo">
            <i class="bi bi-plus-circle"></i> Nueva Recepción
        </a>
    </div>

    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-primary text-center">
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
                    <th>Consumo</th>
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
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${r.estadoReservaDescripcion == 'FINALIZADA'}">
                                    <span class="badge bg-secondary">🧹 Checkout</span>
                                </c:when>
                                <c:when test="${r.estadoReservaDescripcion == 'CANCELADA'}">
                                    <span class="badge bg-danger">❌ Anulado</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-success">✅ Activo</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <div class="d-grid gap-2">
                                <div class="row row-cols-2 g-2">
                                    <!-- Ver -->
                                    <div class="col">
                                        <a href="RecepcionServlet?accion=detalle&id=${r.idRecepcion}"
                                           class="btn btn-sm btn-warning w-100 text-nowrap">
                                           <i class="bi bi-eye"></i> Ver
                                        </a>
                                    </div>

                                    <!-- Anular -->
                                    <c:if test="${r.estadoReservaDescripcion == 'ACTIVA'}">
                                        <div class="col">
                                            <a href="RecepcionServlet?accion=anular&id=${r.idRecepcion}"
                                               class="btn btn-sm btn-danger w-100 text-nowrap"
                                               onclick="return confirm('¿Estás seguro de anular esta recepción?');">
                                               <i class="bi bi-x-circle"></i> Anular
                                            </a>
                                        </div>
                                    </c:if>

                                    <!-- Salida -->
                                    <c:if test="${r.estadoReservaDescripcion == 'ACTIVA'}">
                                        <div class="col">
                                            <a href="RecepcionServlet?accion=checkout&id=${r.idRecepcion}"
                                               class="btn btn-sm btn-success w-100 text-nowrap"
                                               onclick="return confirm('¿Deseas confirmar la salida del cliente?');">
                                               <i class="bi bi-door-open"></i> Salida
                                            </a>
                                        </div>

                                        <!-- Venta -->
                                        <div class="col">
                                            <a href="VentaServlet?accion=preparar&idRecepcion=${r.idRecepcion}"
                                               class="btn btn-sm btn-secondary w-100 text-nowrap">
                                               <i class="bi bi-cart4"></i> Venta
                                            </a>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </td>
                       <td class="text-center">
<a href="ConsumoProductoServlet?accion=nuevo&idRecepcion=${r.idRecepcion}&idHabitacion=${r.idHabitacion}&origen=empleado"
   class="btn btn-sm btn-info"
   title="Añadir Consumo">
    <i class="bi bi-cup-hot"></i>
</a>

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
