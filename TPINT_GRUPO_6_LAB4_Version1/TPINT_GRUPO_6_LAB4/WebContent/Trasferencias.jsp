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
    <style>
        <jsp:include page="css/Transferencias.css"></jsp:include>
        #inputSaldo {
            border: none;
            outline: none;
        }
    </style>
</head>
<script type="text/javascript">
    function EventoSeleccionarCuenta() {
        var x = document.getElementById("ddlCuentasCliente").value;
        window.location.replace("ServletTransferencia?CargarSaldo=" + x);
    }
</script>
<body>
    <jsp:include page="Navbar.jsp" />
    <div class="encabezado">
        <h1>Cuentas</h1>
    </div>

    <form method="post" action="ServletTransferencia">
        <fieldset>
            <legend>Transferencias</legend>
            <select id="ddlCuentasCliente" onchange="EventoSeleccionarCuenta()">
                <option value="0">Selecciona una cuenta</option>
                <% 
                	String TipoCuenta = "";
                    if (cuentas != null && !cuentas.isEmpty()) {
                        for (Cuenta cue : cuentas) { 
                        	if (cue.getTipoCuenta() == 1)
                        	{
                        		TipoCuenta = "CAJA AHORRO";
                        	}
                        	else
                        	{
                        		TipoCuenta = "CUENTA CORRIENTE";
                        	}
                %>
                            <option value="<%= cue.getId() %>">
                                <%= cue.getNumeroCuenta() + TipoCuenta%>
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
                <input id="inputSaldo" readonly="true" type="number" required name="txtSaldo">
            </p>
            <p>
                <input id="btnAceptar" type="submit" value="Transferir" required name="btnAceptar">
            </p>
        </fieldset>
    </form>

    <a href="Cliente.jsp">
        <input class="btnAtras" type="button" value="Atrás" name="btnAtras">
    </a>
    <jsp:include page="Footer.jsp" />
</body>
</html>
