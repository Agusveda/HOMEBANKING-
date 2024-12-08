<%@ page import="daoImp.MovimientoDaoImp"%>
<%@ page import="dao.MovimientoDao"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Entidades.Cuenta"%>

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

                try {
                    // Obtener el total de préstamos confirmados
                    totalPrestamos = movimientoDao.obtenerTotalPrestamosConfirmados(idCliente);
                    // Obtener las cuentas del cliente
                    cuentas = movimientoDao.TraeCuentasPorIdCliente(idCliente);
                } catch (Exception e) {
                    e.printStackTrace();
                    totalPrestamos = 0;
                }
        %>

        <!-- Mostrar el total de préstamos confirmados -->
        <div class="form-group">
            <label for="monto">Monto total de préstamos:</label>
            <span>$<%= totalPrestamos %></span>
        </div>

        <!-- Formulario para seleccionar cuota -->
        <form action="PrestamoVer.jsp" method="POST">
            <input type="hidden" name="totalPrestamos" value="<%= totalPrestamos %>"/>

            <div class="form-group">
                <label for="cuotas">Cuota a pagar:</label>
                <select id="cuotas" name="cuotas" required>
                    <option value="">Seleccione...</option>
                    <option value="1">1 cuota</option>
                    <option value="2">2 cuotas</option>
                    <option value="3">3 cuotas</option>
                </select>
            </div>

            <!-- Selección de la cuenta a debitar -->
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
                            <%= cuenta.getNumeroCuenta() %> - <%= tipoCuenta %> - Saldo: $<%= saldo %>
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

            <!-- Botón de pago -->
            <div class="form-group">
                <button type="submit" class="btn-pagar">Pagar</button>
            </div>

        </form>

        <!-- Volver al cliente -->
        <a href="Cliente.jsp">Volver</a>

        <%
            }
        %>
    </div>

    <jsp:include page="Footer.jsp"/>
</body>
</html>
