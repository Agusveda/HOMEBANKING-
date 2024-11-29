<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/ABMCuenta.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
<title>Gestión de cuentas</title>



</head>
<body>
<jsp:include page="Navbar.jsp"/>

<div class="encabezado">
    <h1>Gestión de cuentas</h1>
</div>

    <table border="1">
        <tr>
            <th style="width: 52px;">Cliente</th> 
            <th style="width: 238px;">Fecha</th> 
            <th style="width: 69px;">Tipo de cuenta</th> 
            <th style="width: 74px;">Numero de cuenta</th> 
            <th style="width: 84px;">CBU</th>
            <th style="width: 84px;">Saldo</th> 
        </tr>
        <tr> 
        </tr>
    </table>
    
    <div>
    
<button id="btnAgregar" type="submit" class="button button-blue" onclick="window.location.href='AltaCuentas.jsp'" >Agregar Cuenta</button>
<button id="btnModificar" type="submit" class="button button-blue" onclick="window.location.href='ModificarCuenta.jsp'" >Modificar Cuenta</button>
<button id="btnEliminar" type="submit" class="button button-red">Eliminar</button>
    </div>
    
 <jsp:include page="Footer.jsp"/>

</body>

</html>


