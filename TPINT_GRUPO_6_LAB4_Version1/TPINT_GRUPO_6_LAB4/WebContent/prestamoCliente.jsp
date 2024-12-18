<%@page import="daoImp.MovimientoDaoImp"%>
<%@page import="dao.MovimientoDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Cuenta"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
		.BtnAtras 
		{
        	background-color: #4fc3f7; 
        	color: white; 
        	font-size: 16px; 
        	padding: 8px 20px; 
        	border: none; 
        	border-radius: 5px;
        	cursor: pointer; 
        	transition: background-color 0.3s, transform 0.3s;
        	box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1); 
        	display: inline-block; 
        	margin: 10px 0; 
        	text-align: center;
    	}

    	.BtnAtras:hover 
    	{
        	background-color: #29b6f6; 
        	transform: scale(1.05); 
    	}

    	.BtnAtras:active 
    	{
        	background-color: #0288d1; 
        	transform: scale(0.95); 
    	}

    	.BtnAtras-container 
    	{
        	text-align: left; 
    	}
    	
    	 .BtnRedirigir {
        background-color: #4fc3f7;
        color: white;
        font-size: 16px;
        padding: 8px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: background-color 0.3s, transform 0.3s;
        box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
        display: inline-block;
        margin: 10px 0;
        text-align: center;
    }

    .BtnRedirigir:hover {
        background-color: #29b6f6;
        transform: scale(1.05);
    }

    .BtnRedirigir:active {
        background-color: #0288d1;
        transform: scale(0.95);
    }
	</style>

</head>
<body>

<jsp:include page="Navbar.jsp"/>
<div class="encabezado">
    <h1>Solicitud de Préstamo</h1>
</div>


<div class="container">
<%
Integer idClien = (Integer) session.getAttribute("IdCliente"); 
%>
 <a href="ProcesoDePrestamo.jsp?idCliente=<%= idClien %>">
    <input class="BtnRedirigir" type="button" value="Préstamos en espera">
</a>

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
                        String tipoCuenta = cuenta.getTipoCuenta() == 1 ? "(CAJA AHORRO)" : "(CTA. CORRIENTE)";
            %>
                        <option value="<%= cuenta.getId() %>">
                            <%= "Nro Cuenta: #" + cuenta.getNumeroCuenta() + " - " + tipoCuenta %>
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

    <a href="Cliente.jsp">
        <input class="BtnAtras" type="button" value="Atrás" name="btnAtras">
    </a>


</body>
</html>