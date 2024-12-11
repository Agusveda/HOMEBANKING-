<%@page import="daoImp.MovimientoDaoImp"%>
<%@page import="dao.MovimientoDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Cuenta"%><%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Solicitud de Préstamo</title>
<link rel="stylesheet" type="text/css" href="">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">

	<style>
		        <jsp:include page="css/prestamoCliente.css"></jsp:include>
	</style>

</head>
<body>

<jsp:include page="Navbar.jsp"/>
<div class="encabezado">
    <h1>Solicitud de Préstamo</h1>
</div>


<div class="container">

<%-- Mostrar el mensaje de error o éxito --%>
<% 
    String mensaje = (String) request.getAttribute("mensaje");
    String mensajeError = (String) request.getAttribute("mensajeError");
    if (mensaje != null) {
%>
    <div class="mensaje-exito"><%= mensaje %></div>
<% 
    }
    if (mensajeError != null) {
%>
    <div class="mensaje-error"><%= mensajeError %></div>
<% 
    }
%>
    
    <% 
        Integer idCliente = (Integer) session.getAttribute("IdCliente");   
        if (idCliente != null) {
            MovimientoDao movimientoDao = new MovimientoDaoImp();
            ArrayList<Cuenta> cuentas = movimientoDao.TraeCuentasPorIdCliente(idCliente);
    %>
    
    <form method="post" action="ServletPrestamo">
        <label for="monto">Monto del Préstamo:</label>
        <input type="number" id="monto" name="monto" placeholder="Ingrese el monto" required>
        <br>
        
        <label for="cuotas">Cantidad de Cuotas:</label>
        <select id="cuotas" name="cuotas" required>
            <option value="">Seleccione...</option>
            <option value="1">1 cuota</option>
            <option value="3">3 cuotas</option>
            <option value="6">6 cuotas</option>
            <option value="9">9 cuotas</option>
            <option value="12">12 cuotas</option>
        </select>
        
        <label for="cuenta">Cuenta de Depósito:</label>
        <select id="cuenta" name="cuenta" required>
            <option value="">Seleccione...</option>
            <% 
                // Llenar dinámicamente las cuentas del cliente
                if (cuentas != null && !cuentas.isEmpty()) {
                    for (Cuenta cuenta : cuentas) {
                        String tipoCuenta = cuenta.getTipoCuenta() == 1 ? "Caja de Ahorro" : "Cuenta Corriente";
            %>
                        <option value="<%= cuenta.getId() %>">
                            <%= cuenta.getNumeroCuenta() + " - " + tipoCuenta %>
                        </option>
            <% 
                    }
                } else { 
            %>
                <option value="">No hay cuentas disponibles</option>
            <% 
                }
            %>
        </select>
        
        <button type="submit" class="btn-pedir">Solicitar Préstamo</button>
    </form>
    
    <% 
        } else { 
    %>
        <div class="mensaje-error">Debe iniciar sesión para solicitar un préstamo.</div>
    <% 
        }
    %>
</div>

</body>
<jsp:include page="Footer.jsp"/>
</html>