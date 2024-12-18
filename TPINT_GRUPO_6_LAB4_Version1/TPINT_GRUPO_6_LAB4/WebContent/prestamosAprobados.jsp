
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="negocioImpl.MovimientoNegocioImpl"%>
<%@page import="negocioImpl.PrestamoNegocioImp"%>
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
        
        body {
        font-family: Arial, sans-serif;
        background-color: #f5f5f5;
        color: #333;
        margin: 0;
        padding: 0;
        line-height: 1.6;
    }

    h1, h2, h3, h4, h5, h6 {
        color: white;
    }

    /* Formularios */
    form {
        margin: 20px auto;
        padding: 15px;
        border: 1px solid #ccc;
        border-radius: 8px;
        background-color: #fff;
        width: 80%;
        max-width: 600px;
    }

    fieldset {
        border: 1px solid #ccc;
        border-radius: 5px;
        padding: 10px;
    }

    legend {
        font-weight: bold;
        color: #555;
    }

    label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }

    select {
        width: 100%;
        padding: 8px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        background-color: #fff;
    }

    /* Tablas */
    table {
        width: 90%;
        margin: 20px auto;
        border-collapse: collapse;
        background-color: #fff;
    }

    table thead th {
        background-color: #007BFF;
        color: #fff;
        text-align: left;
        padding: 10px;
    }

    table tbody td {
        border: 1px solid #ddd;
        padding: 8px;
    }

    table tbody tr:nth-child(even) {
        background-color: #f9f9f9;
    }

    table tbody tr:hover {
        background-color: #f1f1f1;
        cursor: pointer;
    }

    table tbody tr td {
        text-align: center;
    }

    /* Botones y enlaces */
    button, input[type="submit"] {
        background-color: #007BFF;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    button:hover, input[type="submit"]:hover {
        background-color: #0056b3;
    }     
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
</head>
<body>

<div class="encabezado">
    <h1>Préstamos Aprobados</h1>
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

PrestamoNegocioImp prestamoNegocio = new PrestamoNegocioImp();
ArrayList<Prestamo> prestamos;
String filtro = request.getParameter("txtfiltrar");
System.out.println("Filtro seleccionado: " + filtro);

    if (filtro == null || filtro.trim().isEmpty() || filtro.equals("todos")) {
    	System.out.println("Obteniendo todos los prestamos.");
        prestamos = prestamoNegocio.ListPrestamosPedidosAutorizados();
    } else {
    	System.out.println("Filtrado por Importe:" + filtro);
        prestamos = prestamoNegocio.filtrarClienteXImporteConfirmado(filtro);
    }
%>

<table id="prestamos_table" class="display">
    <thead>
        <tr>
            <th>ID Préstamo</th>
            <th>ID Cliente</th>
            <th>Importe Pedido</th>
            <th>Fecha Alta</th>
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
