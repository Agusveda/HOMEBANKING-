<%@page import="negocioImpl.MovimientoNegocioImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
	MovimientoNegocioImpl mov = new MovimientoNegocioImpl();
	ArrayList<Prestamo> lista = mov.ListPrestamosPedidos();
%>

<table border="solicitud_table" class="display">
    <thead>
        <tr>
            <th>CLIENTE</th>
            <th>IMPORTE DEL CLIENTE</th>
            <th>FECHA ALTA</th>
            <th>PLAZO DEL PAGO</th>
            <th>IMPORTE POR MES</th>
            <th>CANTIDAD DE CUOTAS</th>
            <th>CONFIRMACION</th>
        </tr>
    </thead>
    <tbody>
        <% if (lista != null && !lista.isEmpty()) {
            for (Prestamo pre : lista) { %>
                <tr onclick="selectRow(this)">
                    <td><%= pre.getIdCliente() %></td>
                    <td><%= pre.getImporteCliente()%></td>
                    <td><%= pre.getFechaAlta()%></td>
                    <td><%= pre.getPlazoPago()%></td>
                    <td><%= pre.getImpxmes() %></td>
                    <td><%= pre.getCantCuo() %></td>
                    <td><%= pre.getConfimarcion()%></td>
                </tr>
            <% }
        } else { %>
            <tr>
                <td colspan="13" style="text-align: center;">No se encontraron Prestamos :(</td>
            </tr>
        <% } %>
    </tbody>
</table>


 <jsp:include page="Footer.jsp"/>

</body>

</html>