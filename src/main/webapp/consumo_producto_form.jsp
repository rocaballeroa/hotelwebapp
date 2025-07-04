<%-- 
    Document   : consumo_producto_form
    Created on : 20 jun. 2025, 21:45:17
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registrar Consumo</title>
    <link rel="stylesheet" href="css/estilos-vintage.css">
</head>
<body class="container mt-4">

<jsp:include page="navbar-admin.jsp" />

<h2 class="mb-4">Registrar Consumo - Habitación <strong>${idHabitacion}</strong></h2>

<form action="ConsumoProductoServlet" method="post" class="border p-4 rounded shadow-sm bg-light">

    <!-- Campos ocultos -->
    <input type="hidden" name="idHabitacion" value="${idHabitacion}" />

    <c:if test="${not empty idRecepcion}">
        <input type="hidden" name="idRecepcion" value="${idRecepcion}" />
    </c:if>

    <!-- 👇 Campo oculto para conservar origen -->
    <c:if test="${not empty param.origen}">
        <input type="hidden" name="origen" value="${param.origen}" />
    </c:if>

    <!-- Selector de producto -->
    <div class="mb-3">
        <label for="idProducto" class="form-label">Producto</label>
        <select name="idProducto" id="idProducto" class="form-select" required>
            <option value="">-- Seleccione un producto --</option>
            <c:forEach var="p" items="${productos}">
                <option value="${p.idProducto}">${p.nombre}</option>
            </c:forEach>
        </select>
    </div>

    <!-- Campo cantidad -->
    <div class="mb-3">
        <label for="cantidad" class="form-label">Cantidad</label>
        <input type="number" name="cantidad" id="cantidad" class="form-control" min="1" required />
    </div>

    <!-- Botones -->
    <button type="submit" class="btn btn-success">Registrar</button>
<a href="ConsumoProductoServlet?accion=cancelar&idHabitacion=${idHabitacion}&origen=${param.origen}" class="btn btn-warning">Cancelar</a>



</form>

<jsp:include page="footer.jsp" />

</body>
</html>
