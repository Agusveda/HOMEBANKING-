<%@ page import="daoImp.MovimientoDaoImp"%>
<%@ page import="dao.MovimientoDao"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="Entidades.Cuenta"%>
<%@ page import="Entidades.Prestamo"%>
<%@ page import="Entidades.Cuota"%>
<%@ page import="java.util.Collections"%>

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
            if (idClienteObj == null) {
        %>
                <p>Error: No se encontró el ID del cliente en la sesión.</p>
                <a href="Cliente.jsp">Volver</a>
        <%
            } else {
                int idCliente = idClienteObj;
                MovimientoDao movimientoDao = new MovimientoDaoImp();
                double totalPrestamos = 0;
                ArrayList<Cuenta> cuentas = new ArrayList<>();
                List<Float> pagosAcumulados = new ArrayList<>(Collections.nCopies(12, 0f));
                List<Prestamo> prestamos = new ArrayList<>();
                List<Cuota> cuotas = new ArrayList<>();

                try {
                    // Obtener total de préstamos confirmados
                    totalPrestamos = movimientoDao.obtenerTotalPrestamosConfirmados(idCliente);
                    // Obtener cuentas asociadas al cliente
                    cuentas = movimientoDao.TraeCuentasPorIdCliente(idCliente);
                    // Obtener préstamos confirmados
                    prestamos = movimientoDao.obtenerPrestamosConfirmados(idCliente);
                    
                    // Obtener cuotas para cada préstamo
                    for (Prestamo prestamo : prestamos) {
                        cuotas.addAll(movimientoDao.obtenerCuotas(idCliente, prestamo.getId()));
                    }

                    // Llenar pagos acumulados
                    for (Prestamo prestamo : prestamos) {
                        float importePagarXmes = prestamo.getImpxmes();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(prestamo.getFechaAlta());
                        int mesAlta = calendar.get(Calendar.MONTH);

                        for (int i = 0; i < prestamo.getCantCuo(); i++) {
                            int mes = (mesAlta + i) % 12;
                            pagosAcumulados.set(mes, pagosAcumulados.get(mes) + importePagarXmes);
                        }
                    }

                    // Depuración: Mostrar detalles obtenidos
                    System.out.println("Total de préstamos: " + totalPrestamos);
                    System.out.println("Cuotas obtenidas: ");
                    for (Cuota cuota : cuotas) {
                        System.out.println("Cuota ID: " + cuota.getId() + ", Préstamo: " + cuota.getIdPrestamo() + ", Monto: " + cuota.getMonto() + ", Fecha Pago: " + cuota.getFechaPago());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    totalPrestamos = 0;
                }
        %>

        <div class="form-group">
            <label for="monto">Monto total de préstamos:</label>
            <span>$<%= (totalPrestamos == 0) ? "0" : String.format("%.2f", totalPrestamos) %></span>
        </div>

        <!-- Selección de cuenta -->
        <div class="form-group">
            <label for="cuenta">Cuenta a debitar:</label>
            <select id="cuenta" name="cuenta" required>
                <option value="">Seleccione...</option>
                <%
                    if (cuentas != null && !cuentas.isEmpty()) {
                        for (Cuenta cuenta : cuentas) {
                            String tipoCuenta = (cuenta.getTipoCuenta() == 1) ? " (CAJA AHORRO)" : " (CUENTA CORRIENTE)";
                            double saldo = cuenta.getSaldo();
                %>
                    <option value="<%= cuenta.getId() %>">
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
                                <form action="PagoDePrestamo.jsp" method="POST" onsubmit="return confirmarPago(<%= cuota.getId() %>, <%= cuota.getMonto() %>)">
                                    <input type="hidden" name="cuotaId" id="cuotaId" />
                                    <input type="hidden" name="cuentaId" value="<%= request.getParameter("cuenta") %>" />
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
