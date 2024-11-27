<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Solicitud de Prestamo</title>
<link rel="stylesheet" type="text/css" href="css/PagoPrestamo.css">
</head>
<body>

	<div class="container" >
		<h2> Pago de prestamo </h2>
		<form method="post" action="ServletBanco" >
			<label for="monto">Monto del Préstamo total:</label>
			<span> $2.500.000</span>
			<br>
			
			<label for="cuotas">Cuota a pagar:</label>
			<select id="cuotas" name="cuotas" required>
            <option value="">Seleccione...</option>
            <option value="1">1 cuota</option>
            <option value="2">2 cuota</option>
            <option value="3">3 cuota</option>
            <option value="4">4 cuota</option>
            <option value="5">5 cuota</option>
            <option value="6">6 cuota</option>
            <option value="7">7 cuota</option>
        	</select>
        	
        	<label for="cuenta">Cuenta de Depósito:</label>
	        <select id="cuenta" name="cuenta" required>
	            <option value="">Seleccione...</option>
	            <option value="caja-ahorro">Centa 1</option>
	            <option value="cuenta-corriente">Cuenta 2</option>
	            <option value="cuenta-corriente">Cuenta 3</option>
	        </select>
		
		</form>
	        <a href="Cliente.jsp">
	        <button type="submit" class="btn-pagar" >Pagar</button>
	        </a>
	
	</div>

</body>

</html>