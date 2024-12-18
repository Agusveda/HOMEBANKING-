<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    
    <style>
		<jsp:include page="css/Reportes.css"></jsp:include>
    </style>
<title>Reportes</title>
</head>
<body>


<div class="encabezado">
    <h1>Seleccione el reporte que desee</h1>
</div>

    <form action="ServletReportes" method="get">
		
	<div class="botones-contenedor">
    	<input type="submit" class="BtnMovimientos" name="btnMovimientos" value="Movimientos" />
	</div>
	<br>
	<div class="botones-contenedor">
    	<input type="submit" class="BtnTransferencia" name="btnTransferencia" value="Transferencia" />
	</div>
	<div class="botones-contenedor">
    	<input type="submit" class="BtnCuentas" name="btnCuentas" value="Cuentas" />
	</div>	
    </form>

</body>
</html>
