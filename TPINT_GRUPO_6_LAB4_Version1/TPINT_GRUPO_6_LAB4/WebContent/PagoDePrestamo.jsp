<%@ page import="daoImp.MovimientoDaoImp" %>
<%@ page import="daoImp.PrestamoDaoImp" %>
<%@ page import="dao.MovimientoDao" %>
<%@ page import="negocioImpl.PrestamoNegocioImp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="Entidades.Cuenta" %>
<%@ page import="Entidades.Prestamo" %>
<%@ page import="Entidades.Cuota" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pago de Préstamo</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/PagoPrestamo.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    <script type="text/javascript">
        // Función para confirmar el pago antes de proceder
        function confirmarPago(cuotaId, montoPago) {
            var cuentaSeleccionada = document.getElementById("cuenta").options[document.getElementById("cuenta").selectedIndex].value;
            if (!cuentaSeleccionada) {
                alert("Por favor, seleccione una cuenta antes de continuar.");
                return false;
            }

            // Obtener el saldo de la cuenta seleccionada
            var saldoCuenta = parseFloat(document.getElementById("cuenta" + cuentaSeleccionada).getAttribute("data-saldo"));
            
            // Validar si el saldo es suficiente para el pago
            if (saldoCuenta < montoPago) {
                alert("Saldo insuficiente. El monto disponible en la cuenta es $" + saldoCuenta.toFixed(2));
                return false;
            }

            var confirmacion = confirm("¿Estás seguro de realizar el pago?\nMonto a pagar: $" + montoPago);
            if (confirmacion) {
                document.getElementById("cuotaId").value = cuotaId;
                document.getElementById("cuentaId").value = cuentaSeleccionada;
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
        <h1>Pago de Préstamo</h1>
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
                PrestamoNegocioImp prestamoNegocio = new PrestamoNegocioImp();
                double totalPrestamos = 0;
                double totalCuotasPendientes = 0;
                ArrayList<Cuenta> cuentas = new ArrayList<>();
                List<Prestamo> prestamos = new ArrayList<>();
                List<Cuota> cuotas = new ArrayList<>();

                try {
                    // Obtener datos
                    totalPrestamos = prestamoNegocio.obtenerTotalPrestamosConfirmados(idCliente);
                    cuentas = movimientoDao.TraeCuentasPorIdCliente(idCliente);
                    prestamos = prestamoNegocio.obtenerPrestamosConfirmados(idCliente);

                    // Obtener cuotas pendientes
                    for (Prestamo prestamo : prestamos) {
                        cuotas.addAll(prestamoNegocio.obtenerCuotas(idCliente, prestamo.getId()));
                    }

                    // Obtener suma de cuotas pendientes
                    totalCuotasPendientes = prestamoNegocio.obtenerSumaCuotasPendientes(idCliente);

                    // Procesar el pago si se hace un POST
                    if (request.getMethod().equalsIgnoreCase("POST")) {
                        String cuotaIdParam = request.getParameter("cuotaId");
                        String cuentaIdParam = request.getParameter("cuentaId");
                        String montoPagoParam = request.getParameter("montoPago");

                        // Validar que los parámetros no sean nulos ni vacíos
                        if (cuotaIdParam != null && !cuotaIdParam.isEmpty() &&
                            cuentaIdParam != null && !cuentaIdParam.isEmpty() &&
                            montoPagoParam != null && !montoPagoParam.isEmpty()) {

                            int cuotaId = Integer.parseInt(cuotaIdParam);
                            int cuentaId = Integer.parseInt(cuentaIdParam);
                            float montoPago = Float.parseFloat(montoPagoParam);

                            // Realizar el pago
                            boolean exito = prestamoNegocio.realizarPagoCuota(cuotaId, cuentaId, montoPago);
                            mensaje = exito ? "El pago se realizó con éxito." : "Ocurrió un error al realizar el pago.";

                            if (exito) {
                                // Redirigir a la página Cliente.jsp
                                response.sendRedirect("Cliente.jsp");
                                return; // Importante: Evitar que se continúe ejecutando el código posterior.
                            }
                        } else {
                            mensaje = "Faltan parámetros requeridos para realizar el pago.";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mensaje = "Error al obtener datos: " + e.getMessage();
                }
        %>

        <%
            if (!mensaje.isEmpty()) {
        %>
            <p><strong><%= mensaje %></strong></p>
        <%
            }
        %>

        <div class="form-group">
            <label for="cuenta">Cuenta a debitar:</label>
            <select id="cuenta" name="cuentaId" required>
                <option value="">Seleccione...</option>
                <%
                    if (cuentas != null && !cuentas.isEmpty()) {
                        for (Cuenta cuenta : cuentas) {
                            String tipoCuenta = (cuenta.getTipoCuenta() == 1) ? " (CAJA AHORRO)" : " (CUENTA CORRIENTE)";
                            double saldo = cuenta.getSaldo();
                %>
                    <option value="<%= cuenta.getId() %>" id="cuenta<%= cuenta.getId() %>" data-saldo="<%= cuenta.getSaldo() %>">
                        <%= cuenta.getNumeroCuenta() %> - <%= tipoCuenta %> - Saldo: $<%= String.format("%.2f", saldo) %>
                    </option>
                <%
                        }
                    } else {
                %>
                    <option value="">No tiene cuentas asociadas</option>
                <%
                    }
                %>
            </select>
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
                %>
                    <tr>
                        <td><%= cuota.getIdPrestamo() %></td>
                        <td><%= cuota.getNumeroCuota() %></td>
                        <td>$<%= String.format("%.2f", cuota.getMonto()) %></td>
                        <td><%= cuota.getFechaPago() != null ? cuota.getFechaPago() : "Pendiente" %></td>
                        <td><%= estado %></td>
                        <td>
                            <% if (!cuota.isPagada()) { %>
                                <form action="" method="POST" onsubmit="return confirmarPago(<%= cuota.getId() %>, <%= cuota.getMonto() %>)">
                                    <input type="hidden" name="cuotaId" id="cuotaId" value="<%= cuota.getId() %>" />
                                    <input type="hidden" name="cuentaId" id="cuentaId" value="<%= request.getParameter("cuentaId") %>" />
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
