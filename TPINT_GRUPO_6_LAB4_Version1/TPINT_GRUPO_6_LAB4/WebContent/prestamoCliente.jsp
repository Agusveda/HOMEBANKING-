<%@page import="daoImp.MovimientoDaoImp"%>
<%@page import="dao.MovimientoDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Cuenta"%><%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Solicitud de Préstamo</title>
<link rel="stylesheet" type="text/css" href="">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">

	<style>
		    /* Contenedor principal */
    .container {
        max-width: 500px;
        margin: 50px auto;
        padding: 20px;
        background-color: #fff;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
        font-family: Arial, sans-serif;
    }

    /* Títulos y etiquetas */
    label {
        font-size: 14px;
        font-weight: bold;
        display: block;
        margin-bottom: 5px;
        color: #333;
    }

    /* Campos de entrada */
    input[type="number"],
    select {
        width: 100%;
        padding: 10px;
        margin-bottom: 20px;
        border: 1px solid #ddd;
        border-radius: 5px;
        font-size: 14px;
        box-sizing: border-box;
    }

    input[type="number"]:focus,
    select:focus {
        border-color: #007bff;
        outline: none;
        box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
    }

    /* Botón de acción */
    .btn-pedir {
        width: 100%;
        padding: 12px;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .btn-pedir:hover {
        background-color: #0056b3;
    }

    /* Mensaje de error */
    .mensaje-error {
        font-size: 14px;
        color: red;
        text-align: center;
        margin-top: 20px;
    }
	</style>

</head>
<body>

<jsp:include page="Navbar.jsp"/>
<div class="encabezado">
    <h1>Solicitud de Préstamo</h1>
</div>


<div class="container">
    
    <% 
        Integer idCliente = (Integer) session.getAttribute("IdCliente");   
        if (idCliente != null) {
            MovimientoDao movimientoDao = new MovimientoDaoImp();
            ArrayList<Cuenta> cuentas = movimientoDao.TraeCuentasPorIdCliente(idCliente);
    %>
    
    <form method="post" action="ServletPrestamo">
        <label for="monto">Monto del Préstamo:</label>
        <input type="number" id="monto" name="monto" placeholder="Ingrese el monto" required>
        <br>
        
        <label for="cuotas">Cantidad de Cuotas:</label>
        <select id="cuotas" name="cuotas" required>
            <option value="">Seleccione...</option>
            <option value="6">6 cuotas</option>
            <option value="12">12 cuotas</option>
            <option value="24">24 cuotas</option>
            <option value="36">36 cuotas</option>
        </select>
        
        <label for="cuenta">Cuenta de Depósito:</label>
        <select id="cuenta" name="cuenta" required>
            <option value="">Seleccione...</option>
            <% 
                // Llenar dinámicamente las cuentas del cliente
                if (cuentas != null && !cuentas.isEmpty()) {
                    for (Cuenta cuenta : cuentas) {
                        String tipoCuenta = cuenta.getTipoCuenta() == 1 ? "Caja de Ahorro" : "Cuenta Corriente";
            %>
                        <option value="<%= cuenta.getId() %>">
                            <%= cuenta.getNumeroCuenta() + " - " + tipoCuenta %>
                        </option>
            <% 
                    }
                } else { 
            %>
                <option value="">No hay cuentas disponibles</option>
            <% 
                }
            %>
        </select>
        
        <button type="submit" class="btn-pedir">Solicitar Préstamo</button>
    </form>
    
    <% 
        } else { 
    %>
        <div class="mensaje-error">Debe iniciar sesión para solicitar un préstamo.</div>
    <% 
        }
    %>
</div>

</body>
<jsp:include page="Footer.jsp"/>
</html>