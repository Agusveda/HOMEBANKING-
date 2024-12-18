<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    <style>
		<jsp:include page="css/generarReporte.css"></jsp:include>
		
	.mensaje 
	{
    padding: 10px;
    margin: 10px 0;
    border-radius: 5px;
    font-weight: bold;
    text-align: center;
	}
	
	.mensaje-exito 
	{
    background-color: #d4edda;
    color: #155724;
    border: 1px solid #c3e6cb;
	}

	.mensaje-error 
	{
    background-color: #f8d7da;
    color: #721c24;
    border: 1px solid #f5c6cb;
	}

    </style>
    
<title>Reportes</title>

</head>

<body>

<%
	///REPORTE DE MOVIMIENTOS
	String TipoMovimiento = "";
	float total=0;
	int id = 0;

	if (session.getAttribute("id") != null)
	{
    	id = (int) session.getAttribute("id");	
	}
	
	if (session.getAttribute("total") != null)
	{
    	total = (float) session.getAttribute("total");	
	}
	if (session.getAttribute("TipoMovimiento") != null)
	{
    	TipoMovimiento = (String) session.getAttribute("TipoMovimiento");	
	}
	
	///REPORTE DE CUENTAS
	float saldo = 0;
	
	if (session.getAttribute("saldo") != null)
	{
    	saldo = (float) session.getAttribute("saldo");	
	}
%>

<div class="encabezado">
    <h1>Generar Reporte de <% 
    	if(id == 1)
    	{%>
    		Movimientos
    	<% 
    	}else
    	{ 
    	  if (id == 2)
    	
    	  {
    	%>
    		Transferencia
    	<%}else
    	  {%>
    		Cuentas
    		
    	<%}
    	
    	}
    	%>
    
    </h1> 
        <% 
	    	String mensaje = (String) request.getAttribute("mensaje");
	    	String mensajeError = (String) request.getAttribute("mensajeError");
	    %>
        
	    <% if (mensaje != null) { %>
	    <div class="mensaje mensaje-exito"><%= mensaje %></div>
		<% } %>
		<% if (mensajeError != null) { %>
		    <div class="mensaje mensaje-error"><%= mensajeError %></div>
		<% } %>
</div>

    <form action="ServletReportes" method="post">	
    	<div class="form-contenedor"> 	
    	<%
    		if (id == 1)
    		{
    	%>
    	<select id="TipoMovimiento" name="TipoMovimiento">
        <option value="0" >
                    Selecciona un movimiento
        </option>
        <option value="1" >
                    Alta cuenta
        </option>
        <option value="2" >
                    Alta prestamo
        </option>
        <option value="3" >
                    Pago prestamo
        </option>
        <option value="4" >
                    Transferencia
        </option>
        
        </select>
        <%
    		}
        %>
        <br>
    	
    	<%
    		if(id == 1 )
    		{	
    	%>
    	<p style="font-size: 0.9em; color: gray;">Ejemplo: 2024-12-06 (Año-Mes-Día)</p>
    	
        <label for="fechaInicio">Fecha Inicio:</label>
        <input type="date" id="fechaInicio" name="fechaInicio" 
        maxlength="10" 
        placeholder="YYYY-MM-DD" 
        pattern="\d{4}-\d{2}-\d{2}" 
        oninput="this.value = this.value.replace(/[^0-9-]/g, '').slice(0, 10);" required>
        <br>
        
        <label for="fechaFin">Fecha Fin:</label>
        <input type="date" id="fechaFin" name="fechaFin" 
        maxlength="10" 
        placeholder="YYYY-MM-DD" 
        pattern="\d{4}-\d{2}-\d{2}" 
        oninput="this.value = this.value.replace(/[^0-9-]/g, '').slice(0, 10);" required>
        <br>
        <br>
        <%
    		}        
        %>
        
        <button type="submit" name="btnReportes">Generar Reporte</button>
        
        <% 
        /// REPORTE DE MOVIMIENTOS
        if (total != 0)
        	{
        %>
        	
        	<div class="reporte-lista">
        	
    			<div class="reporte-item">
        			<span class="tipo-movimiento"><%= TipoMovimiento  %>: </span>
        			<span class="total"><%= total %></span>
    			</div>
    			
    	   </div>
    	        	
        	<%
        	session.removeAttribute("total");
        	}
        	%>
    	</div>
    	
    	<%
    	if(id == 2 )
		{	
    	%>
    	
    	<p style="font-size: 0.9em; color: gray;">Ejemplo: 44552521</p>
    	
       <label for="DNICliente">DNI del cliente:</label>
<input type="text" id="DNICliente" name="DNICliente" pattern="\d*" title="Solo se permiten números" oninput="this.value = this.value.replace(/[^0-9-]/g, '').slice(0, 10);" maxlength="8"  required>
        <br> 
        <br>
        <br>
    	
<%
    Float ImporteEgreso = (Float) session.getAttribute("ImporteEgreso");
    Float ImporteIngreso = (Float) session.getAttribute("ImporteIngreso");

    if (ImporteEgreso == null) {
        ImporteEgreso = 0f;
    }
    if (ImporteIngreso == null) {
        ImporteIngreso = 0f;
    }
%>
<div class="reporte-item">
    <span class="egreso">La cantidad de plata que se debitó de la cuenta es </span>
    <span class="total"> <%= ImporteEgreso %> </span>
</div>
<div class="reporte-item">
    <span class="ingreso">La cantidad de plata que se ingresó en la cuenta es </span>
    <span class="total"> <%= ImporteIngreso %> </span>
</div>
    	<%
    	session.removeAttribute("ImporteEgreso");
    	session.removeAttribute("ImporteIngreso");
		} %>

    	<% 
    	/// REPORTE DE CUENTAS
        if (saldo != 0)
        	{
        %>
        	
        	<div class="reporte-lista">
        	
    			<div class="reporte-item">
        			<span class="tipo-movimiento">La cantidad de plata en el banco es de:  </span>
        			<span class="total"> <%= saldo %></span>
    			</div>
    			
    	   </div>
        	
        	<%
        	session.removeAttribute("saldo");
        	}
        	%>
    	
    </form>

   	 <a href="Reportes.jsp">
        <input class="BtnAtras" type="button" value="Atrás" name="btnAtras">
     </a>   
</body>
</html>