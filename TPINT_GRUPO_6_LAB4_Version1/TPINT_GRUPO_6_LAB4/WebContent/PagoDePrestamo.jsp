<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Solicitud de Préstamo</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/PagoPrestamo.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
</head>
<body>
    <jsp:include page="Navbar.jsp"/>

    <div class="encabezado">
        <h1>Solicitud de Préstamo</h1>
    </div>

    <div class="container">
        <h2>Pago de Préstamo</h2>
        <form method="post" action="ServletBanco">
            <div class="form-group">
                <label for="monto">Monto del Préstamo total:</label>
                <span>$2.500.000</span>
            </div>

            <div class="form-group">
                <label for="cuotas">Cuota a pagar:</label>
                <select id="cuotas" name="cuotas" required>
                    <option value="">Seleccione...</option>
                    <option value="1">1 cuota</option>
                    <option value="2">2 cuotas</option>
                    <option value="3">3 cuotas</option>
                    <option value="4">4 cuotas</option>
                    <option value="5">5 cuotas</option>
                    <option value="6">6 cuotas</option>
                    <option value="7">7 cuotas</option>
                </select>
            </div>

            <div class="form-group">
                <label for="cuenta">Cuenta de Depósito:</label>
                <select id="cuenta" name="cuenta" required>
                    <option value="">Seleccione...</option>
                    <option value="caja-ahorro">Cuenta 1</option>
                    <option value="cuenta-corriente">Cuenta 2</option>
                    <option value="cuenta-corriente">Cuenta 3</option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit" class="btn-pagar">Pagar</button>
            </div>
        </form>
        <a href="Cliente.jsp">Volver</a>
    </div>

    <jsp:include page="Footer.jsp"/>
</body>
</html>
