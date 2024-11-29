<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Movimientos de Cuenta</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/VerMovimientos.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
</head>
<body>
<jsp:include page="Navbar.jsp"/>
<div class="encabezado">
    <h1>Movimientos</h1>
</div>
<body>

<fieldset>
        <legend>Movimientos actuales</legend>
         <a href="Cliente.jsp">
    
    <input class="btnAtras" type="submit" value="Atras" name="btnAtras" >
    </a>
       
<table class="tabla"  border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Tipo de Movimiento</th>
            <th>Fecha de movimiento</th>
            <th>Importe</th>
        </tr>
    </thead>
    <tbody>
        <tr>
          
          
        </tr>
  
        <tr>
            <td colspan="8">No se encontraron Movimientos actualmente.</td>
        </tr>
    </tbody>
</table>

   
    </fieldset>
    
    
 <jsp:include page="Footer.jsp"/>

</body>

</html>