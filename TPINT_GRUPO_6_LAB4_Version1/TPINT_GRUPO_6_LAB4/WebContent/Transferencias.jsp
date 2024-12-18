<%@page import="com.mysql.jdbc.PreparedStatement.ParseInfo"%>
<%@page import="daoImp.MovimientoDaoImp"%>
<%@page import="dao.MovimientoDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Cuenta"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
    int idCliente = (int) session.getAttribute("IdCliente");
    MovimientoDao movimientoDao = new MovimientoDaoImp();
    ArrayList<Cuenta> cuentas = movimientoDao.TraeCuentasPorIdCliente(idCliente);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="css/Transferencias.css">
        <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    	<link rel="stylesheet" type="text/css" href="css/Footer.css">
<style>
        <jsp:include page="css/Transferencias.css"></jsp:include>
        #inputSaldo {
            border: none;
            outline: none;
        }
        
.mensaje {
    padding: 10px;
    margin: 10px 0;
    border-radius: 5px;
    font-weight: bold;
    text-align: center;
}

.mensaje-exito {
    background-color: #d4edda;
    color: #155724;
    border: 1px solid #c3e6cb;
}

.mensaje-error {
    background-color: #f8d7da;
    color: #721c24;
    border: 1px solid #f5c6cb;
}
</style>
</head>

<script type="text/javascript">
function EventoSeleccionarCuenta() 
{
    var idCuenta = document.getElementById("ddlCuentasCliente").value;
    window.location.href = "ServletTransferencia?CargarSaldo=true&idCuenta=" + idCuenta;
}
</script>
<body>

<jsp:include page="Navbar.jsp"/>
    <div class="encabezado">
        <h1>Cuentas</h1>
    </div>
        <% 
	    String mensaje = (String) request.getAttribute("mensaje");
	    String mensajeError = (String) request.getAttribute("mensajeError");
	    %>
        
        <!-- Mensajes de éxito o error -->
	    <% if (mensaje != null) { %>
	    <div class="mensaje mensaje-exito"><%= mensaje %></div>
		<% } %>
		<% if (mensajeError != null) {
			session.removeAttribute("idCuenta");
			
			%>
		    <div class="mensaje mensaje-error"><%= mensajeError %></div>
		<% } %>
	
    <form method="post" action="ServletTransferencia" onsubmit="return confirmarTransferencia(this)">
        <fieldset>
            <legend>Transferencias</legend>
            
            <% 
                // Obtener el idCuenta  si existe
                Integer cuentaSeleccionada = (Integer) session.getAttribute("idCuenta");
            %>
            <select id="ddlCuentasCliente" onchange="EventoSeleccionarCuenta()">
                <option value="0" <%= (cuentaSeleccionada == null || cuentaSeleccionada == 0) ? "selected" : "" %>>
                    Selecciona una cuenta
                </option>
                <% 
                    String TipoCuenta;
                    if (cuentas != null && !cuentas.isEmpty()) {
                        for (Cuenta cue : cuentas) { 
                            TipoCuenta = (cue.getTipoCuenta() == 1) ? " (CAJA AHORRO)" : " (CUENTA CORRIENTE)";
                %>
                            <option value="<%= cue.getId() %>" 
                                <%= (cuentaSeleccionada != null && cuentaSeleccionada == cue.getId()) ? "selected" : "" %>>
                                <%= cue.getNumeroCuenta() + TipoCuenta %>
                            </option>
                <% 
                        }
                    } else { 
                %>
                        <option value="0">No hay cuentas disponibles</option>
                <% 
                    }
                %>
            </select>
			
            <p>
                <label for="CbuDestino">Ingresar CBU del destinatario</label>
                <input id="CbuDestino" type="number" placeholder="CBU Destino" required name="txtCbuDestino">
            </p>
            <p>
                <label for="Importe">Importe</label>
                <input id="Importe" type="number" placeholder="Ingrese el importe" required name="txtImporte">
            </p>
            <p>
                <label for="Detalle">Detalle</label>
                <input id="Detalle" type="text" placeholder="Ingrese el detalle" required name="txtDetalle">
            </p>
            <p>
                <label for="Saldo">Saldo Actual</label>
                <input id="inputSaldo" readonly="true" type="number" name="txtSaldo"
              value="<%= request.getAttribute("saldoActual") != null ? request.getAttribute("saldoActual") : "" %>">
            </p>
            <p>
                <input id="btnAceptar" type="submit" value="Transferir" required name="btnAceptar">
            </p>
        </fieldset>

    <a href="Cliente.jsp">
        <input class="BtnAtras" type="button" value="Atrás" name="btnAtras">
    </a>
</form>
	

<script>
    function confirmarTransferencia(form) 
    {
        const confirmacion = confirm("¿Estás seguro de transferir a esta cuenta?");
        if (confirmacion) {
            return true;
        } else {
            console.log("Transferencia cancelada.");
            return false;
        }
    }
</script>

 <jsp:include page="Footer.jsp"/>
    
</body>
</html>
