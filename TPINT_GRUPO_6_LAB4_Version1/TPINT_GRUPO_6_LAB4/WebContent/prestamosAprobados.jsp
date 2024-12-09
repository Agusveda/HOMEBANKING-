
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="negocioImpl.MovimientoNegocioImpl"%>
<%@ page import="daoImp.MovimientoDaoImp"%>
<%@ page import="Entidades.Prestamo"%>
<%@ page import="java.util.ArrayList"%>

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

<form method="get" action="" id="filtroForm">
    <fieldset>
    <legend>Filtrar Clientes por Importe de pedido</legend>
        <p>
            <label for="Importe">Buscar Préstamo:</label>
            <select id="buscar" name="txtfiltrar" onchange="this.form.submit()">
                <option value="todos" <%= "todos".equals(request.getParameter("txtfiltrar")) ? "selected" : "" %>>Todos</option>
                <option value="Menor" <%= "Menor".equals(request.getParameter("txtfiltrar")) ? "selected" : "" %>>Menor Importe</option>
                <option value="Mayor" <%= "Mayor".equals(request.getParameter("txtfiltrar")) ? "selected" : "" %>>Mayor Importe</option>
            </select>
        </p>
    </fieldset>
</form>

<%
    MovimientoNegocioImpl mov = new MovimientoNegocioImpl();
    ArrayList<Prestamo> prestamos;
    String filtro = request.getParameter("txtfiltrar");
    System.out.println("Filtro seleccionado: " + filtro);

    if (filtro == null || filtro.trim().isEmpty() || filtro.equals("todos")) {
    	System.out.println("Obteniendo todos los prestamos.");
        prestamos = mov.ListPrestamosPedidosAutorizados();
    } else {
    	System.out.println("Filtrado por Importe:" + filtro);
        prestamos = mov.filtrarClienteXImporteConfirmado(filtro);
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
    let table = $('#clientes_table').DataTable({
        stateSave: true,
        createdRow: function(row, data, dataIndex) {
            if ($(row).hasClass('selected-row')) {
                $(row).addClass('selected-row');
            }
        }
    });

    $('#clientes_table tbody').on('click', 'tr', function() {
        table.$('tr.selected-row').removeClass('selected-row');
        $(this).addClass('selected-row');
    });
});

</script>

</body>
</html>
