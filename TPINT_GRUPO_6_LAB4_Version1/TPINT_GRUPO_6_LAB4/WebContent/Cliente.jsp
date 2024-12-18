<%@page import="daoImp.MovimientoDaoImp"%>
<%@page import="Entidades.Cuenta"%>
<%@page import="java.util.ArrayList"%>
<%@page import="daoImp.CuentaDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
     int idCliente = (int) session.getAttribute("IdCliente");
    %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Cliente.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    
    <style>
    
		        <jsp:include page="css/ClienteVentana.css"></jsp:include>
    
    </style>
    
<title>Bienvenida</title>
</head>

<body>

<jsp:include page="Navbar.jsp"/>

<div class="encabezado">
    <h1>Estado de tu cuenta</h1>
</div>


<div class="Cuentas">
 
<%
    // Backend para Filtrar por Tipo de Cuenta
    MovimientoDaoImp Movimientodao = new MovimientoDaoImp();
    ArrayList<Cuenta> listaCuenta = Movimientodao.TraeCuentasPorIdCliente(idCliente);

%>


<!-- Tabla para Mostrar las Cuentas -->
<table id="table_Cuenta" class="display">
    <thead>
        <tr>

            <th>Tipo cuenta</th>
            <th>CBU</th>
            <th>Saldo</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <% 
            if (listaCuenta != null && !listaCuenta.isEmpty()) {
                for (Cuenta cuentaItem : listaCuenta) {
        %>
        <tr>
           
            <td><%= cuentaItem.getTipoCuenta() == 1 ? "Ahorro" : "Cta. Corriente" %></td>
            <td><%= cuentaItem.getCbu() %></td>
            <td><%= cuentaItem.getSaldo() %></td>
            <td>
                <form action="ServletVerMovimiento" method="post">
                    <input type="hidden" name="idCuenta" value="<%= cuentaItem.getId() %>" />
                    <input type="submit" class="ver-movimientos-btn" value="Ver Movimiento" name="btnVerMovimientos" />
                </form>
            </td>
        </tr>
        <% 
                }
            } else { 
        %> 
        <tr>
            <td colspan="5">No se encontraron cuentas.</td>
        </tr>
        <% 
            }
        %>
    </tbody>
</table>
</div>

<br>
<BR>
<div class="accesoRapido">
Accesos Rapidos
</div>


	<div class="Opciones">
	
	<a href="Transferencias.jsp" > 
	<input type="submit" class="BtnOpciones"  name="btnTransferencias" value="Transferencias"/>
	</a>
	
	<a href="prestamoCliente.jsp" > 
	<input type="submit" class="BtnOpciones"  name="btnPrestamos" value="Prestamos" />
	</a>
	
	<a href="PagoDePrestamo.jsp">
	<input type="submit" class="BtnOpciones"   name="btnPagoServicios" value="Pago de Prestamos"/>
	</a>
	
	</div>

 <jsp:include page="Footer.jsp"/>

</body>

</html>