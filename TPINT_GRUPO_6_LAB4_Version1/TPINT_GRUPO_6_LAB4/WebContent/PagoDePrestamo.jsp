<%@ page import="daoImp.MovimientoDaoImp"%>
<%@ page import="dao.MovimientoDao"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="Entidades.Cuenta"%>
<%@ page import="Entidades.Prestamo"%>
<%@ page import="java.util.Collections"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Pago de Préstamo</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/PagoPrestamo.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
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

                try {
                    totalPrestamos = movimientoDao.obtenerTotalPrestamosConfirmados(idCliente);
                    cuentas = movimientoDao.TraeCuentasPorIdCliente(idCliente);
                    List<Prestamo> prestamos = movimientoDao.obtenerPrestamosConfirmados(idCliente);

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
                } catch (Exception e) {
                    e.printStackTrace();
                    totalPrestamos = 0;
                }
        %>

        <div class="form-group">
            <label for="monto">Monto total de préstamos:</label>
            <span>$<%= (totalPrestamos == 0) ? "0" : String.format("%.2f", totalPrestamos) %></span>
        </div>

        <div class="form-group">
            <label for="pagosAcumulados">Pagos acumulados por mes:</label>
            <select id="pagosAcumulados" name="mesPago" required>
                <option value="">Seleccione...</option>
                <%
                    // Crear un array con los nombres de los meses en español
                    String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

                    // Mostrar los pagos acumulados con los nombres de los meses
                    for (int i = 0; i < 12; i++) {
                        String nombreMes = meses[i]; // Obtener el nombre del mes correspondiente
                        float pagoMes = pagosAcumulados.get(i);
                %>
                    <option value="<%= i %>">
                        <%= nombreMes %>: $<%= String.format("%.2f", pagoMes) %>
                    </option>
                <%
                    }
                %>
            </select>
        </div>

        <form action="PrestamoVer.jsp" method="POST">
            <input type="hidden" name="totalPrestamos" value="<%= totalPrestamos %>"/>


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

            <div class="form-group">
                <button type="submit" class="btn-pagar">Pagar</button>
            </div>
        </form>

        <a href="Cliente.jsp">Volver</a>

        <%
            }
        %>
    </div>

    <jsp:include page="Footer.jsp"/>
</body>
</html>
