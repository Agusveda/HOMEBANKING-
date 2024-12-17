<%@page import="java.util.ArrayList"%>
<%@page import="daoImp.PrestamoDaoImp"%>
<%@page import="dao.PrestamoDao"%>
<%@page import="java.util.List"%>
<%@page import="Entidades.Prestamo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
    int iDcliente = (int) session.getAttribute("IdCliente");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Préstamos en Espera</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    <link rel="stylesheet" type="text/css" href="css/ProcesoDePrestamo.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <script>
        $(document).ready(function () {
            $('#TablaPrestamos').DataTable();
        });
    </script>
</head>
<body>
    <jsp:include page="Navbar.jsp" />
    <div class="encabezado">
        <h1>Préstamos en Espera</h1>
    </div>
    <div class="container">
        <%
            PrestamoDaoImp prestamoimp = new PrestamoDaoImp();
            ArrayList<Prestamo> ListaPrestamosPendientes = prestamoimp.obtenerPrestamosEnEspera(iDcliente);
        %>
        <table class="display" id="TablaPrestamos">
            <thead>
                <tr>
                    <th>Préstamo N°</th>
                    <th>Monto Solicitado</th>
                    <th>Fecha de Alta</th>
                    <th>Cantidad de Cuotas</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <% if (ListaPrestamosPendientes != null && !ListaPrestamosPendientes.isEmpty()) {
                    for (Prestamo prestamo : ListaPrestamosPendientes) { %>
                        <tr>
                            <td><%= prestamo.getId() %></td>
                            <td><%= prestamo.getImporteCliente() %></td>
                            <td><%= prestamo.getFechaAlta() %></td>
                            <td><%= prestamo.getCantCuo() %></td>
                            <td>
                                <% if (prestamo.getConfimarcion() == 0) { %>
                                    Préstamo en espera
                                <% } else if (prestamo.getConfimarcion() == 1) { %>
                                    Préstamo confirmado
                                <% } else if (prestamo.getConfimarcion() == 2) { %>
                                    Préstamo denegado
                                <% } %>
                            </td>
                        </tr>
                <% } } else { %>
                    <tr>
                        <td colspan="5" class="no-datos">No tienes préstamos en espera.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        <div class="boton-atras">
            <a href="Cliente.jsp">
                <input class="BtnAtras" type="button" value="Atrás" name="btnAtras">
            </a>
        </div>
    </div>
    <jsp:include page="Footer.jsp" />
</body>
</html>
