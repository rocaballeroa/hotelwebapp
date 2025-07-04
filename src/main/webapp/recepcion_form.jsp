<%-- 
    Document   : recepcion_form
    Created on : 20 jun. 2025, 10:46:17
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.hotel.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || (usuario.getIdTipoPersona() != 1 && usuario.getIdTipoPersona() != 2)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Recepción de Cliente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f7f7f7;
            font-family: Arial, sans-serif;
        }

        .form-container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            margin: 50px auto;
        }

        .form-container h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 25px;
        }

        .btn-group-custom {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        .session-info {
            text-align: center;
            margin-top: 20px;
            font-weight: bold;
        }
      .btn-volver {
    background-color: #FA7F08;
    color: white;
    border: none;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px; /* 👈 aquí ajustas la separación */
    padding: 8px 16px;
    border-radius: 5px;
    transition: background-color 0.2s ease;
    min-width: 180px;
    font-size: 16px;
}

.btn-volver:hover {
    background-color: #e76e05;

}

.btn-volver i {
    font-size: 20px;
}


    </style>
</head>
<body>

<div class="form-container">
    <h2>Registrar Recepción</h2>

    <form action="RecepcionServlet" method="post">
      <input type="hidden" name="accion" value="guardar" />  
      <div class="mb-3">
    <label class="form-label">Cliente:</label>
    <div class="input-group">
        <input type="text" id="clienteSeleccionado" class="form-control" placeholder="Escribe nombre o DNI" required autocomplete="off">
        <input type="hidden" name="idCliente" id="idCliente">
    </div>
    <div id="sugerenciasClientes" class="list-group position-absolute w-100" style="z-index: 10;"></div>
</div>



        <div class="mb-3">
            <label class="form-label">Habitación:</label>
            <select name="idHabitacion" id="habitacionSelect" class="form-select" required onchange="actualizarPrecio()">
    <c:forEach var="hab" items="${habitaciones}">
        <option value="${hab.idHabitacion}" data-precio="${hab.precio}">
            Hab. ${hab.numero} - ${hab.categoriaDescripcion}
        </option>
    </c:forEach>
</select>

        </div>

        <div class="mb-3">
            <label class="form-label">Fecha de salida:</label>
            <input type="datetime-local" name="fechaSalida" class="form-control" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Precio inicial:</label>
            <input type="number" step="0.01" name="precioInicial" id="precioInicial" class="form-control" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Adelanto:</label>
            <input type="number" step="0.01" name="adelanto" class="form-control" required />
        </div>

       <div class="btn-group-custom">
    <button type="submit" class="btn btn-success">✅ Registrar Recepción</button>

<c:choose>
    <c:when test="${usuario.idTipoPersona == 1}">
        <!-- ADMIN -->
        <button type="button" class="btn btn-volver" onclick="location.href='RecepcionServlet?accion=listar'">
            <i class="bi bi-arrow-left-circle"></i>
            <span class="btn-text"> Volver a la lista </span>
        </button>
    </c:when>
    <c:otherwise>
        <!-- EMPLEADO -->
        <button type="button" class="btn btn-volver" onclick="location.href='RecepcionServlet?accion=empleado'">
            <i class="bi bi-arrow-left-circle"></i>
            <span class="btn-text"> Volver a la lista </span>
        </button>
    </c:otherwise>
</c:choose>


</div>

    </form>

    <p class="session-info text-secondary mt-4">Sesión activa: ${usuario.nombre}</p>
</div>
<script>
    function actualizarPrecio() {
        const select = document.getElementById('habitacionSelect');
        const precioInput = document.getElementById('precioInicial');
        const selectedOption = select.options[select.selectedIndex];
        const precio = selectedOption.getAttribute('data-precio');
        if (precio) {
            precioInput.value = parseFloat(precio).toFixed(2);
        }
    }

    // Llenar al cargar por defecto
    window.addEventListener('DOMContentLoaded', actualizarPrecio);
    
    
</script>
<script>
    const clientes = [
        <c:forEach var="cli" items="${clientes}" varStatus="loop">
            {
                id: "${cli.idPersona}",
                nombreCompleto: "${cli.nombre} ${cli.apellido} (${cli.documento})"
            }<c:if test="${!loop.last}">,</c:if>
        </c:forEach>
    ];
</script>

<script>
    const inputCliente = document.getElementById('clienteSeleccionado');
    const sugerenciasDiv = document.getElementById('sugerenciasClientes');
    const idClienteHidden = document.getElementById('idCliente');

    inputCliente.addEventListener('input', function () {
        const texto = this.value.toLowerCase();
        sugerenciasDiv.innerHTML = '';

        if (texto.length === 0) return;

        const filtrados = clientes.filter(c =>
            c.nombreCompleto.toLowerCase().includes(texto)
        );

        filtrados.forEach(c => {
            const item = document.createElement('button');
            item.type = 'button';
            item.classList.add('list-group-item', 'list-group-item-action');
            item.textContent = c.nombreCompleto;

            item.addEventListener('click', () => {
                inputCliente.value = c.nombreCompleto;
                idClienteHidden.value = c.id;
                sugerenciasDiv.innerHTML = '';
            });

            sugerenciasDiv.appendChild(item);
        });
    });

    // Ocultar sugerencias al hacer clic fuera
    document.addEventListener('click', (e) => {
        if (!sugerenciasDiv.contains(e.target) && e.target !== inputCliente) {
            sugerenciasDiv.innerHTML = '';
        }
    });
</script>


<jsp:include page="footer.jsp" />


<!-- Modal de búsqueda de cliente -->
<div class="modal fade" id="modalClientes" tabindex="-1" aria-labelledby="modalClientesLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Buscar Cliente</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      <div class="modal-body">
        <input type="text" id="filtroCliente" class="form-control mb-3" placeholder="Buscar por nombre o DNI">

        <table class="table table-bordered table-hover">
          <thead class="table-dark">
            <tr>
              <th>Nombre</th>
              <th>Apellido</th>
              <th>DNI</th>
              <th>Acción</th>
            </tr>
          </thead>
          <tbody id="tablaClientes">
            <c:forEach var="cli" items="${clientes}">
              <tr>
                <td>${cli.nombre}</td>
                <td>${cli.apellido}</td>
                <td>${cli.documento}</td>
                <td>
                  <button type="button" class="btn btn-sm btn-success seleccionar-cliente"
                          data-id="${cli.idPersona}"
                          data-nombre="${cli.nombre} ${cli.apellido} (${cli.documento})">
                    Seleccionar
                  </button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>

</body>

</html>
