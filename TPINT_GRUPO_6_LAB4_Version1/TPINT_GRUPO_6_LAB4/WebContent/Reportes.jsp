<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    
    <style>
    
.botones-contenedor {
    display: flex; 
    justify-content: center; 
    gap: 20px;
    margin-top: 20px; 
}


.botones-contenedor input[type="submit"] {
    font-size: 18px; 
    padding: 10px 20px; 
    border-radius: 8px; 
    border: none; 
    cursor: pointer; 
    transition: transform 0.3s, background-color 0.3s; 
}

/* Botón Movimientos */
.BtnMovimientos {
    background-color: #007bff; 
    color: white; 
}

.BtnMovimientos:hover {
    background-color: #0056b3; 
    transform: scale(1.1); 
}

/* Botón Clientes */
.BtnClientes {
    background-color: #28a745; 
    color: white; 
}

.BtnClientes:hover {
    background-color: #1e7e34; 
    transform: scale(1.1);
}
    </style>
<title>Reportes</title>
</head>
<body>

<jsp:include page="Navbar.jsp"/>

<div class="encabezado">
    <h1>Seleccione el reporte que desee</h1>
</div>

    <form action="ServletReportes" method="get">
		
	<div class="botones-contenedor">
    	<input type="submit" class="BtnMovimientos" name="btnMovimientos" value="Movimientos" />
	</div>
	<br>
	<div class="botones-contenedor">
    	<input type="submit" class="BtnClientes" name="btnClientes" value="Clientes" />
	</div>
		
	
    </form>


 <jsp:include page="Footer.jsp"/>


</body>
</html>