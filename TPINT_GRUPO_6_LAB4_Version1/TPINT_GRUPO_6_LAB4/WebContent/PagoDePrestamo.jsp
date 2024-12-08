<%@page import="daoImp.MovimientoDaoImp"%>
<%@page import="dao.MovimientoDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Cuenta"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Pago de Préstamo</title>
    <!-- Enlaces a los archivos de estilo -->
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/PagoPrestamo.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
</head>
<body>
    <!-- Inclusión del Navbar -->
    <jsp:include page="Navbar.jsp"/>

    <!-- Contenedor principal -->
    <div class="encabezado">
        <h1>Formulario de Pago de Préstamo</h1>
    </div>

    <div class="container">
        <h2>Pagar Préstamo</h2>
        <form method="post" action="ServletBanco">
            <div class="form-group">
                <label for="monto">Monto total de prestamos:</label>
                <span>$2.500.000</span>
            </div>

            <!-- Selección de cuotas -->
            <div class="form-group">
                <label for="cuotas">Cuota a pagar:</label>
                <select id="cuotas" name="cuotas" required>
                    <option value="">Seleccione...</option>
                    <option value="1">1 cuota</option>
                    <option value="2">2 cuotas</option>
                    <option value="3">3 cuotas</option>
                </select>
            </div>

            <!-- Selección de cuenta de depósito -->
            <div class="form-group">
                <label for="cuenta">Cuenta a debitar:</label>
                <select id="cuenta" name="cuenta" required>
                    <option value="">Seleccione...</option>
                    <%
                        int idCliente = (int) session.getAttribute("IdCliente");
                        MovimientoDao movimientoDao = new MovimientoDaoImp();
                        // Obtener las cuentas del cliente
                        ArrayList<Cuenta> cuentas = movimientoDao.TraeCuentasPorIdCliente(idCliente);

                        if (cuentas != null && !cuentas.isEmpty()) {
                            // Mostrar cada cuenta con su saldo y tipo
                            for (Cuenta cuenta : cuentas) {
                                // Obtener el tipo de cuenta
                                String tipoCuenta = (cuenta.getTipoCuenta() == 1) ? " (CAJA AHORRO)" : " (CUENTA CORRIENTE)";
                                double saldo = cuenta.getSaldo(); // Asumiendo que ya tienes el saldo de la cuenta
                    %>
                        <option value="<%= cuenta.getId() %>">
                            <%= cuenta.getNumeroCuenta() %> - <%= tipoCuenta %> - Saldo: $<%= saldo %>
                        </option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>


            <!-- Botón para enviar el formulario -->
            <div class="form-group">
                <button type="submit" class="btn-pagar">Pagar</button>
            </div>
        </form>

        <!-- Enlace para volver al cliente -->
        <a href="Cliente.jsp">Volver</a>
    </div>

    <!-- Inclusión del Footer -->
    <jsp:include page="Footer.jsp"/>
</body>
</html>
