<%-- 
    Document   : vista_previa_venta
    Created on : 22 jun. 2025, 23:45:31
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.hotel.modelo.Venta, com.hotel.modelo.DetalleVenta" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirmar Venta</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/estilos-vintage.css">

</head>
<body>

<h2 class="text-center my-4 fw-bold" style="color: #5B584F;">🧾 Confirmar Detalle de Venta</h2>

<div class="container my-5">
    <div class="card shadow rounded-4 border-0">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover align-middle text-center mb-0">
                    <thead style="background-color: #92A78C; color: #FAFBF6;">
                        <tr>
                            <th>#</th>
                            <th>ID Producto</th>
                            <th>Descripción</th>
                            <th>Cantidad</th>
                            <th>Precio Unitario</th>
                            <th>Subtotal</th>
                        </tr>
                    </thead>
                    <tbody class="table-group-divider">
                        <c:forEach var="d" items="${detalles}" varStatus="s">
                            <tr class="${d.idProducto == 0 || d.idProducto == 1000 ? 'table-warning' : ''}">
                                <td>${s.index + 1}</td>
                                <td>${d.idProducto}</td>
                                <td>${d.nombreProducto}</td>
                                <td>${d.cantidad}</td>
                                <td>S/ ${d.precioUnitario}</td>
                                <td>S/ ${d.subTotal}</td>
                            </tr>
                        </c:forEach>
                        <tr style="background-color: #E0D5AD; color: #5B584F;" class="fw-bold">
                            <td colspan="5" class="text-end">TOTAL A PAGAR</td>
                            <td>S/ ${venta.total}</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="d-flex justify-content-between mt-4 px-2">
                <form action="VentaServlet" method="post">
                    <input type="hidden" name="accion" value="confirmar">
                    <input type="hidden" name="idRecepcion" value="${venta.idRecepcion}">
                    <button type="submit" class="btn btn-verde px-4">
                        ✅ Confirmar Venta
                    </button>
                </form>

                <c:choose>
                    <c:when test="${sessionScope.usuario.idTipoPersona == 3}">
                        <a href="RecepcionServlet?accion=verReservas" class="btn btn-beige px-4">Cancelar</a>
                    </c:when>
                    <c:otherwise>
                        <a href="RecepcionServlet?accion=empleado" class="btn btn-beige px-4">Cancelar</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>



</div>
<jsp:include page="footer.jsp" />
</body>
</html>
