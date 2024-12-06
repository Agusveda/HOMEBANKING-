<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    <style>
    
    
.form-contenedor {
    display: flex;
    flex-direction: column; 
    align-items: center; 
    gap: 15px; 
    margin: 20px auto;
    width: 90%; 
    max-width: 400px; 
    padding: 20px; 
    background-color: #f8f9fa;
    border: 1px solid #dee2e6; 
    border-radius: 10px; 
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); 
}



label {
    font-size: 16px; 
    font-weight: bold; 
    color: #495057; 
}



select, input[type="date"] {
    width: 100%; 
    padding: 10px; 
    font-size: 16px; 
    border: 1px solid #ced4da; 
    border-radius: 5px; 
    background-color: white; 
    color: #495057; 
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
    transition: border-color 0.3s; 
}


select:focus, input[type="date"]:focus {
    border-color: #007bff; 
    outline: none; 
}



button[type="submit"] {
    background-color: #007bff; 
    color: white; 
    font-size: 16px; 
    padding: 10px 20px; 
    border: none; 
    border-radius: 5px; 
    cursor: pointer; 
    transition: background-color 0.3s, transform 0.2s; 
}


button[type="submit"]:hover {
    background-color: #0056b3; 
    transform: scale(1.05); 
}



.reporte-lista {
    margin: 20px auto; 
    max-width: 600px; 
    padding: 20px; 
    background-color: #f8f9fa; 
    border: 1px solid #dee2e6; 
    border-radius: 10px; 
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); 
}


.reporte-item {
    display: flex; 
    justify-content: space-between; 
    align-items: center; 
    padding: 15px; 
    margin-bottom: 10px; 
    background-color: white; 
    border: 1px solid #ced4da; 
    border-radius: 5px; 
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
    transition: transform 0.2s, box-shadow 0.2s; 
}


.reporte-item:last-child {
    margin-bottom: 0;
}


.reporte-item:hover {
    transform: translateY(-3px); 
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15); 
}


.reporte-item .tipo-movimiento {
    font-size: 18px; 
    font-weight: bold; 
    color: #007bff; 
}


.reporte-item .total {
    font-size: 16px; 
    font-weight: normal; 
    color: #28a745; 
}
    </style>
    
<title>Reportes</title>

</head>

<body>

<jsp:include page="Navbar.jsp"/>
<%
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
%>

<div class="encabezado">
    <h1>Generar Reporte de <% if(id == 1)
    	{%>
    		Movimientos
    	<% }else
    	{%>
    		Clientes
    	<%}
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
        <option value="4" >
                    Transferencia
        </option>
        
        </select>
        <%
    		}
        %>
        <br>
    
        <label for="fechaInicio">Fecha Inicio:</label>
        <input type="date" id="fechaInicio" name="fechaInicio" required>
        <br>
        
        <label for="fechaFin">Fecha Fin:</label>
        <input type="date" id="fechaFin" name="fechaFin" required>
        <br>
        <br>
        
        <button type="submit" name="btnReportes">Generar Reporte</button>
        
        
        <% 
        if (total != 0)
        	{
        %>
        	
        	<div class="reporte-lista">
        	
    			<div class="reporte-item">
        			<span class="tipo-movimiento">Transferencia: </span>
        			<span class="total"><%= total %></span>
    			</div>
    			
    	   </div>
        	
        	<%
        	}
        	%>
    	</div>
    </form>


 <jsp:include page="Footer.jsp"/>

</body>


</html>