<%@ page import="negocioImpl.MovimientoNegocioImpl" %>
<%@ page import="Entidades.Prestamo" %>
<%@ page import="java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Solicitudes de Préstamo</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    <link rel="stylesheet" type="text/css" href="css/AMPrestamos.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">

    <style>
        .selected-row {
            background-color: #d3e0ea;
        }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
</head>
<body>
<jsp:include page="Navbar.jsp"/>

<div class="encabezado">
    <h1>Solicitudes de Préstamo</h1>
</div>

<form method="get" action="#">
    <fieldset>
        <legend>Buscar Préstamo</legend>
        <p>
            <label for="buscar">Número de Solicitud:</label>
            <input id="buscar" type="text" name="txtBuscar" placeholder="Ingrese número de solicitud" required>
            <input type="submit" value="Buscar">
        </p>
    </fieldset>
</form>

<%
    // Instanciar la clase de lógica de negocio
    MovimientoNegocioImpl mov = new MovimientoNegocioImpl();
    ArrayList<Prestamo> prestamos = null;

    try {
        prestamos = mov.ListPrestamosPedidosAutorizados();
        if (prestamos == null || prestamos.isEmpty()) {
            out.println("<p style='color: red;'>No se encontraron préstamos autorizados.</p>");
        }
    } catch (Exception e) {
        out.println("<p style='color: red;'>Error al obtener la lista de préstamos: " + e.getMessage() + "</p>");
        e.printStackTrace();
    }
%>

<table id="prestamos_table" class="display">
    <thead>
        <tr>
            <th>ID Préstamo</th>
            <th>ID Cliente</th>
            <th>Importe Pedido</th>
            <th>Fecha Alta</th>
            <th>Importe Mensual</th>
            <th>Cantidad Cuotas</th>
            <th>Confirmado</th>
        </tr>
    </thead>
    <tbody>
        <% 
        if (prestamos != null && !prestamos.isEmpty()) {
            for (Prestamo prestamo : prestamos) { %>
                <tr onclick="selectRow(this)">
                    <td><%= prestamo.getId() %></td>
                    <td><%= prestamo.getIdCliente() %></td>
                    <td><%= prestamo.getImporteCliente() %></td>
                    <td><%= prestamo.getFechaAlta() %></td>
                    <td><%= prestamo.getImpxmes() %></td>
                    <td><%= prestamo.getCantCuo() %></td>
                    <td><%= prestamo.getConfimarcion()%></td>
                </tr>
            <% }
        } else { %>
            <tr>
                <td colspan="8" style="text-align: center;">No se encontraron préstamos.</td>
            </tr>
        <% } %>
    </tbody>
</table>

<jsp:include page="Footer.jsp"/>

<script>
    let selectedRow = null;

    $(document).ready(function() {
        $('#prestamos_table').DataTable();
    });

    function selectRow(row) {
        if (selectedRow) {
            selectedRow.classList.remove("selected-row");
        }
        row.classList.add("selected-row");
        selectedRow = row;
    }
</script>

</body>
</html>
