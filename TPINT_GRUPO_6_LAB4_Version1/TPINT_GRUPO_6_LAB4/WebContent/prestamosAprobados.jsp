<%@ page import="negocioImpl.MovimientoNegocioImpl" %>
<%@ page import="Entidades.Prestamo" %>
<%@ page import="java.util.ArrayList" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Solicitudes de Pr�stamo</title>
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
    <h1>Solicitudes de Pr�stamo</h1>
</div>

<form method="get" action="#">
    <fieldset>
        <legend>Buscar Pr�stamo</legend>
        <p>
            <label for="buscar">N�mero de Solicitud:</label>
            <input id="buscar" type="text" name="txtBuscar" placeholder="Ingrese n�mero de solicitud" required>
            <input type="submit" value="Buscar">
        </p>
    </fieldset>
</form>

<%
    MovimientoNegocioImpl mov = new MovimientoNegocioImpl();
    ArrayList<Prestamo> prestamos = mov.ListPrestamosPedidos();

    if (prestamos == null || prestamos.isEmpty()) {
        System.out.println("No se encontraron pr�stamos.");
    } else {
        System.out.println("Cantidad de pr�stamos encontrados: " + prestamos.size());
    }

    request.setAttribute("prestamos", prestamos);
%>

<table id="prestamos_table" class="display">
    <thead>
        <tr>
            <th>ID Cliente</th>
            <th>Importe Pedido</th>
            <th>Fecha Alta</th>
            <th>Plazo Pago</th>
            <th>Importe Mensual</th>
            <th>Cantidad Cuotas</th>
            <th>Confirmado</th>
        </tr>
    </thead>
    <tbody>
        <% if (prestamos != null && !prestamos.isEmpty()) {
            for (Prestamo prestamo : prestamos) { %>
                <tr onclick="selectRow(this)">
                    <td><%= prestamo.getIdCliente() %></td>
                    <td><%= prestamo.getImporteCliente() %></td>
                    <td><%= prestamo.getFechaAlta() %></td>
                    <td><%= prestamo.getPlazoPago() %></td>
                    <td><%= prestamo.getImpxmes() %></td>
                    <td><%= prestamo.getCantCuo() %></td>
                    <td><%= prestamo.getConfimarcion() ? "S�" : "No" %></td>
                </tr>
            <% }
        } else { %>
            <tr>
                <td colspan="7" style="text-align: center;">No se encontraron pr�stamos.</td>
            </tr>
        <% } %>
    </tbody>
</table>

<jsp:include page="Footer.jsp"/>

<script>
    let selectedRow = null;

    $(document).ready(function() {
        $('#prestamos_table').DataTable().destroy(); 
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