<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <title>Alta de Cuentas</title>

    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/ABMCuenta.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">

	<style>
	.mensaje 
	{
    padding: 10px;
    margin: 10px 0;
    border-radius: 5px;
    font-weight: bold;
    text-align: center;
    
    
	}

	.mensaje-exito 
	{
    background-color: #d4edda;
    color: #155724;
    border: 1px solid #c3e6cb;
	}

	.mensaje-error 
	{
    background-color: #f8d7da;
    color: #721c24;
    border: 1px solid #f5c6cb;
	}
	</style>

</head>
<body>

<div class="encabezado">
	<h1>Alta de Cuenta</h1>
</div>

        <% 
	    String mensaje = (String) request.getAttribute("mensaje");
        String mensajeError = (String) request.getAttribute("mensajeError");
	    %>
	    <!-- Mostrar alert en caso de mensajes -->
	    <% if (mensaje != null) { %>
		 <div style="color: green;">
        <%= mensaje %>
    </div>
		<% } %>
		
		<% if (mensajeError != null) { %>
		    <div style="color: red;">
        <%= mensajeError %>
    </div>
		<% } %>

<form method="post" action="ServletCuenta" onsubmit="return confirmarAlta(this)">
    <fieldset>
        <legend>Alta de Cuenta</legend>
         <p><label for="txtIdCliente">ID Cliente: </label><input type="text" name="txtIdCliente" onkeypress="validarSoloNumeros(event)"></p>
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

    <script>    
    function validarSoloNumeros(event) {
        const key = event.key;
        const regex = /^[0-9]$/;

        if (!regex.test(key) && key !== "Backspace" && key !== "Tab" && key !== "Delete" && key !== "ArrowLeft" && key !== "ArrowRight") {
            event.preventDefault(); // Evita que se ingrese un carácter no permitido
            alert("Solo se permiten números.");
        }
    }

    function confirmarAlta(form) 
    {
        const confirmacion = confirm("¿Estás seguro de dar Alta a esta cuenta?");
        if (confirmacion) {
            return true;
        } else {
            console.log("Alta cancelada.");
            return false;
        }
    }
</script>

</body>
</html>
