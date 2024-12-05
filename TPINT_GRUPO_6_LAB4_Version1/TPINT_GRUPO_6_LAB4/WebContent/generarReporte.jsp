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
    <h1>Generar Reporte de Ingresos y Egresos</h1>
</div>

    <form action="ServletReportes" method="post">
    	
    	<select id="TipoMovimiento" name="TipoMovimiento">
        <option value="0" >
                    Selecciona una cuenta
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
    
        <label for="fechaInicio">Fecha Inicio:</label>
        <input type="date" id="fechaInicio" name="fechaInicio" required>
        
        <label for="fechaFin">Fecha Fin:</label>
        <input type="date" id="fechaFin" name="fechaFin" required>
        
        <button type="submit">Generar Reporte</button>
    </form>


 <jsp:include page="Footer.jsp"/>

</body>


</html>