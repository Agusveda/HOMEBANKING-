<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
<title>Reportes</title>
</head>
<body>

<jsp:include page="Navbar.jsp"/>

<div class="encabezado">
    <h1>Seleccione el reporte que desee</h1>
</div>

    <form action="ServletReportes" method="get">
		
		<input type="submit" class="BtnMovimientos"  name="btnMovimientos" value="Movimientos" />
		<input type="submit" class="BtnClientes"  name="btnClientes" value="Clientes" />
		
	
    </form>


 <jsp:include page="Footer.jsp"/>


</body>
</html>