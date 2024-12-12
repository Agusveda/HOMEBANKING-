<%@ page import="daoImp.CuentaDaoImpl" %>
<%@ page import="Entidades.Cuenta" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%
    String idCuentaParam = request.getParameter("idCuenta");
    int idCuenta = 0;

    // Obtener el ID de la cuenta
    if (idCuentaParam != null && !idCuentaParam.isEmpty()) {
        try {
            idCuenta = Integer.parseInt(idCuentaParam); 
        } catch (NumberFormatException e) {
            e.printStackTrace();     
        }
    }
    
    // Buscar la cuenta por su ID
    CuentaDaoImpl cuentadao = new CuentaDaoImpl();
    Cuenta cuenta = cuentadao.obtenerCuentaPorId(idCuenta); 
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Modificar Cuenta</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/ABMCuenta.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">


</head>
<body>
<div class="encabezado">
        <h1>Modificar Cuenta</h1>
</div>

 

    <div class="container">
 

        <% if (cuenta != null) { %>
            <!-- Formulario para modificar la cuenta -->
            <form method="post" action="ServletCuenta" onsubmit="return confirmarModificacion(this)">
                <input type="hidden" name="txtIdCuenta" value="<%= cuenta.getId() %>" />

                <table>
                    <tr>
                        <td><label for="txtTipoCuenta">Tipo de Cuenta:</label></td>
                        <td><input type="text" name="txtTipoCuenta" value="<%= cuenta.getTipoCuenta() %>" required /></td>
                    </tr>
                    <tr>
                        <td><label for="txtNumeroCuenta">Número de Cuenta:</label></td>
                        <td><input type="text" name="txtNumeroCuenta" value="<%= cuenta.getNumeroCuenta() %>" required /></td>
                    </tr>
                    <tr>
                        <td><label for="txtCBU">CBU:</label></td>
                        <td><input type="text" name="txtCBU" value="<%= cuenta.getCbu() %>" required /></td>
                    </tr>
                    <tr>
                        <td><label for="txtSaldo">Saldo:</label></td>
                        <td><input type="text" name="txtSaldo" value="<%= cuenta.getSaldo() %>" required /></td>
                    </tr>
                </table>

                <!-- Botones de acci n -->
                <button type="submit" name="btnModificarCuenta">Modificar Cuenta</button>
                <button type="button" onclick="window.location.href='ListarCuenta.jsp'">Cancelar</button>
            </form>
        <% } %> <!-- El bloque "else" que muestra el mensaje de cuenta no encontrada se elimin  -->

    </div>
    
<%-- Mostrar mensajes de error si existen --%>
<%
    String mensajeError = (String) request.getAttribute("mensajeError");
    if (mensajeError != null) {
%>
    <div class="error">
        <p><%= mensajeError %></p>
    </div>
<%
    }
%>

    
    
    
    <script>
    function confirmarModificacion(form) {
        const confirmacion = confirm("¿Estás seguro de modificar la cuenta?");
        if (confirmacion) {
            console.log("Modificacion confirmada para el formulario:", form);
            return true; // Permite enviar el formulario
        } else {
            console.log("Modificacion cancelada.");
            return false; // Cancela el envío del formulario
        }
    }
</script>

</body>
</html>
