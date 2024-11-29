<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <title>Alta de Cuentas</title>

    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/ABMCuenta.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">

</head>
<body>
<jsp:include page="Navbar.jsp"/>

<div class="encabezado">
	<h1>Alta de Cuenta</h1>
</div>

<form method="post" action="ServletCuenta">
    <fieldset>
        <legend>Alta de Cuenta</legend>
         <p><label for="txtIdCliente">ID Cliente: </label><input type="text" name="txtIdCliente" required></p>
        <p><label for="txtTipoCuenta">Tipo de cuenta:</label>
            <select name="txtTipoCuenta">
                <option value="1">Caja de ahorro</option>
                <option value="2">Cuenta corriente</option>
            </select>
        </p>
         
        
        <p><label for="txtSaldo">Saldo</label>: <span id="txtSaldo">$ 10.000</span></p>
         <p><input type="submit" value="Aceptar" name="btnAltaCuenta" class ="button-blue"></p>
    </fieldset>
</form>
<jsp:include page="Footer.jsp"/>
</body>
</html>
