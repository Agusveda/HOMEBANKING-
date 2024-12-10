<%@ page import="daoImp.MovimientoDaoImp"%>
<%@ page import="dao.MovimientoDao"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Collections"%>
<%@ page import="Entidades.Cuenta"%>
<%@ page import="Entidades.Prestamo"%>
<%@ page import="Entidades.Cuota"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Pago de Préstamo</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/PagoPrestamo.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    <script type="text/javascript">
        function confirmarPago(cuotaId, montoPago) {
            var confirmacion = confirm("¿Estás seguro de realizar el pago?\nMonto a pagar: $" + montoPago);
            if (confirmacion) {
                document.getElementById("cuotaId").value = cuotaId;
                return true; 
            } else {
                return false; 
            }
        }
    </script>
</head>
<body>
    <jsp:include page="Navbar.jsp"/>

    <div class="encabezado">
        <h1>Formulario de Pago de Préstamo</h1>
    </div>

    <div class="container">
        <h2>Pagar Préstamo</h2>

        <%
            Integer idClienteObj = (Integer) session.getAttribute("IdCliente");
            String mensaje = "";

            if (idClienteObj == null) {
        %>
                <p>Error: No se encontró el ID del cliente en la sesión.</p>
                <a href="Cliente.jsp">Volver</a>
        <%
            } else {
                int idCliente = idClienteObj;
                MovimientoDao movimientoDao = new MovimientoDaoImp();
                double totalPrestamos = 0;
                double totalCuotasPendientes = 0;
                ArrayList<Cuenta> cuentas = new ArrayList<>();
                List<Prestamo> prestamos = new ArrayList<>();
                List<Cuota> cuotas = new ArrayList<>();

                try {
                    // Obtener datos
                    totalPrestamos = movimientoDao.obtenerTotalPrestamosConfirmados(idCliente);
                    cuentas = movimientoDao.TraeCuentasPorIdCliente(idCliente);
                    prestamos = movimientoDao.obtenerPrestamosConfirmados(idCliente);

                    // Obtener cuotas pendientes
                    for (Prestamo prestamo : prestamos) {
                        cuotas.addAll(movimientoDao.obtenerCuotas(idCliente, prestamo.getId()));
                    }

                    // Obtener suma de cuotas pendientes
                    totalCuotasPendientes = movimientoDao.obtenerSumaCuotasPendientes(idCliente);

                    if (request.getMethod().equalsIgnoreCase("POST")) {
                        int cuotaId = Integer.parseInt(request.getParameter("cuotaId"));
                        int cuentaId = Integer.parseInt(request.getParameter("cuentaId"));
                        float montoPago = Float.parseFloat(request.getParameter("montoPago"));

                        // Realizar el pago
                        boolean exito = movimientoDao.realizarPagoCuota(cuotaId, cuentaId, montoPago);
                        mensaje = exito ? "El pago se realizó con éxito." : "Ocurrió un error al realizar el pago.";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mensaje = "Error al obtener datos: " + e.getMessage();
                }
        %>

        <!-- Mensaje de resultado -->
        <%
            if (!mensaje.isEmpty()) {
        %>
            <p><strong><%= mensaje %></strong></p>
        <%
            }
        %>

        <div class="form-group">

        </div>

        <div class="form-group">
            <label for="cuotas-pendientes">Monto total de cuotas pendientes:</label>
            <span>$<%= String.format("%.2f", totalCuotasPendientes) %></span>
        </div>

        <!-- Tabla de cuotas -->
        <h3>Cuotas Pendientes</h3>
        <table class="tabla-cuotas">
            <thead>
                <tr>
                    <th>ID Préstamo</th>
                    <th>Cuota #</th>
                    <th>Monto</th>
                    <th>Fecha de Vencimiento</th>
                    <th>Estado</th>
                    <th>Acción</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (cuotas.isEmpty()) {
                %>
                    <tr>
                        <td colspan="6">No hay cuotas pendientes para este cliente.</td>
                    </tr>
                <%
                    } else {
                        for (Cuota cuota : cuotas) {
                            String estado = cuota.isPagada() ? "Pagada" : "Pendiente";
                            Cuenta cuenta = cuentas.isEmpty() ? null : cuentas.get(0);
                %>
                    <tr>
                        <td><%= cuota.getIdPrestamo() %></td>
                        <td><%= cuota.getNumeroCuota() %></td>
                        <td>$<%= String.format("%.2f", cuota.getMonto()) %></td>
                        <td><%= cuota.getFechaPago() != null ? cuota.getFechaPago() : "Pendiente" %></td>
                        <td><%= estado %></td>
                        <td>
                            <% if (!cuota.isPagada() && cuenta != null) { %>
                                <form action="" method="POST" onsubmit="return confirmarPago(<%= cuota.getId() %>, <%= cuota.getMonto() %>)">
                                    <input type="hidden" name="cuotaId" value="<%= cuota.getId() %>" />
         							 <input type="hidden" name="cuentaId" value="<%= cuenta.getId() %>" />
                                    <input type="hidden" name="montoPago" value="<%= cuota.getMonto() %>" />
                                    <button type="submit" class="btn-pagar">Pagar</button>
                                </form>
                            <% } %>
                        </td>
                    </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>

        <a href="Cliente.jsp">Volver</a>

        <%
            }
        %>
    </div>

    <jsp:include page="Footer.jsp"/>
</body>
</html>
