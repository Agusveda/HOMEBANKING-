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

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">

    <style>
		<jsp:include page="css/SolicitudesPrestamo.css"></jsp:include>
		
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
</head>
<body>
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
	PrestamoNegocioImp prestamoNegocio = new PrestamoNegocioImp();
	ArrayList<Prestamo> prestamos;
	String filtro = request.getParameter("txtfiltrar");
	System.out.println("Filtro seleccionado: " + filtro);

    if (filtro == null || filtro.trim().isEmpty() || filtro.equals("todos")) {
    	System.out.println("Obteniendo todos los prestamos.");
        prestamos = prestamoNegocio.ListPrestamosPedidos();
    } else {
    	System.out.println("Filtrado por Importe:" + filtro);
        prestamos = prestamoNegocio.filtrarClienteXImporte(filtro);
    }
%>

<table id="prestamos_table" class="display">
    <thead>
        <tr>
        	<th>ID Prestamo</th>
            <th>ID Cliente</th>
             <th>ID Cuenta</th>
            <th>Importe Pedido</th>
            <th>Cantidad Cuotas</th>  
            <th>Fecha Solicitud</th>    
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <% if (prestamos != null && !prestamos.isEmpty()) {
            for (Prestamo prestamo : prestamos) { %>
                <tr onclick="selectRow(this)">
                	<td><%= prestamo.getId() %></td>
                    <td><%= prestamo.getIdCliente() %></td>
                    <td><%= prestamo.getIdCuenta() %></td>
                    <td><%= prestamo.getImporteCliente() %></td>
                    <td><%= prestamo.getCantCuo() %></td>
                    <td><%= prestamo.getFechaAlta() %></td>
                   
                    <td>
                        <!-- Formulario para aprobar el préstamo -->
                        <form action="ServletPrestamo" method="post" style="display:inline;">
                            <input type="hidden" name="idPrestamo" value="<%= prestamo.getId() %>">
                            <input type="hidden" name="confirmacion" value="1">
                                <input type="hidden" name="cuenta" value="<%= prestamo.getIdCuenta() %>"> 
                            
                             <input type="hidden" name="monto" value="<%= prestamo.getImporteCliente() %>">
                            <button type="submit" class="acciones-btn">Aprobar</button>
                        </form>
                        <!-- Formulario para denegar el préstamo -->
                        <form action="ServletPrestamo" method="post" style="display:inline;">
                            <input type="hidden" name="idPrestamo" value="<%= prestamo.getId() %>">
                            <input type="hidden" name="confirmacion" value="2">
                          <input type="hidden" name="cuenta" value="<%= prestamo.getIdCuenta() %>"> 

                             <input type="hidden" name="monto" value="<%= prestamo.getImporteCliente() %>">
                            <button type="submit" class="acciones-btn">Denegar</button>
                        </form>
                    </td>
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
        $('#clientes_table').DataTable().destroy(); // Elimina cualquier inicialización previa
        $('#clientes_table').DataTable();          // Inicializa la tabla de nuevo
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