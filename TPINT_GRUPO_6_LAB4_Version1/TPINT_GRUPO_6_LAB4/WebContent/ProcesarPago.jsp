<%@ page import="daoImp.MovimientoDaoImp"%>
<%@ page import="dao.MovimientoDao"%>
<%@ page import="Entidades.Cuenta"%>
<%@ page import="Entidades.Prestamo"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Procesar Pago</title>
</head>
<body>
    <%
        // Obtener parámetros desde la solicitud
        String mesPago = request.getParameter("mesPago");
        String cuentaId = request.getParameter("cuenta");
        Integer idClienteObj = (Integer) session.getAttribute("IdCliente"); // Obtener ID de cliente desde la sesión

        if (idClienteObj == null) {
            out.println("<p>Error: No se encontró el ID del cliente en la sesión.</p>");
            out.println("<a href='Formulario.jsp'>Volver</a>");
        } else if (mesPago == null || cuentaId == null || mesPago.isEmpty() || cuentaId.isEmpty()) {
            out.println("<p>Error: Parámetros insuficientes para procesar el pago.</p>");
            out.println("<a href='Formulario.jsp'>Volver</a>");
        } else {
            // Convertir los parámetros a tipos correspondientes
            int mes = Integer.parseInt(mesPago);
            int idCuenta = Integer.parseInt(cuentaId);
            int idCliente = idClienteObj; // Utilizar el ID del cliente de la sesión

            // Lógica para procesar el pago
            MovimientoDao movimientoDao = new MovimientoDaoImp();
            Cuenta cuentaSeleccionada = movimientoDao.obtenerCuentaPorId(idCuenta);

            if (cuentaSeleccionada != null) {
                double saldoCuenta = cuentaSeleccionada.getSaldo();
                float pagoMes = movimientoDao.obtenerPagoPorMes(idCliente, mes);

                if (saldoCuenta >= pagoMes) {
                    // Realizar el pago (lógica del pago)
                    movimientoDao.realizarPago(idCliente, idCuenta, pagoMes);
                    out.println("<p>Pago realizado con éxito.</p>");
                } else {
                    out.println("<p>Error: No hay suficiente saldo en la cuenta.</p>");
                }
            } else {
                out.println("<p>Error: Cuenta no encontrada.</p>");
            }
        }
    %>

    <a href="Formulario.jsp">Volver al formulario</a>
</body>
</html>
