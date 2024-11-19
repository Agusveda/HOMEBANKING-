<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Solicitudes de Pr�stamo</title>
    <link rel="stylesheet" type="text/css" href="css/AMPrestamos.css">
</head>
<body>
<jsp:include page="Navbar.jsp"/>
<div class="encabezado">
    <h1>Solicitudes de Pr�stamo</h1>
</div>

<form method="get" action="#">
    <fieldset>
        <legend>Buscar Pr�stamo</legend>
        <p>
            <label for="buscar">N�mero de Solicitud:</label>
            <input id="buscar" type="text" name="txtBuscar" placeholder="Ingrese n�mero de solicitud" required>
            <input type="submit" value="Buscar">
        </p>
    </fieldset>
</form>

<table border="1">
    <thead>
        <tr>
            <th>ID Pr�stamo</th>
            <th>Nombre del Cliente</th>
            <th>Documento</th>
            <th>Monto Solicitado</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
   
    </tbody>
</table>

 <jsp:include page="Footer.jsp"/>

</body>

</html>