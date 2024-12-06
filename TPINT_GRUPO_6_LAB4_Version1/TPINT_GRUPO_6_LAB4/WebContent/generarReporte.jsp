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
    </style>
    
<title>Reportes</title>

</head>

<body>

<jsp:include page="Navbar.jsp"/>
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
	
	/// REPORTE DE TRANSFERENCIA
	
	
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
        
        </select>
        <%
    		}
        %>
        <br>
    	
    	<%
    		if(id == 1 || id == 2)
    		{	
    	%>
        <label for="fechaInicio">Fecha Inicio:</label>
        <input type="date" id="fechaInicio" name="fechaInicio" required>
        <br>
        
        <label for="fechaFin">Fecha Fin:</label>
        <input type="date" id="fechaFin" name="fechaFin" required>
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
    	   
	 <a href="Reportes.jsp">
        <input class="BtnAtras" type="button" value="Atrás" name="btnAtras">
     </a>
        	
        	<%
        	session.removeAttribute("total");
        	}
        	%>
    	</div>
    	
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
     
 <jsp:include page="Footer.jsp"/>

</body>


</html>