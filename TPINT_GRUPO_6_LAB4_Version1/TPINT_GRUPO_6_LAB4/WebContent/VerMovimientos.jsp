<%@page import="Entidades.Movimiento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="negocioImpl.MovimientoNegocioImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
int idCuenta = (int) session.getAttribute("idCuenta");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Movimientos de Cuenta</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/VerMovimientos.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
 </head>
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
    
    <%	
    	MovimientoNegocioImpl moviN = new MovimientoNegocioImpl();
    	ArrayList<Movimiento> listaMov = moviN.ListarMovimientosPorCuenta(idCuenta);
    %>
       
<table class="tabla"  border="1">
    <thead>
        <tr>
      
            <th>Fecha de movimiento</th>
            <th>Importe</th>
            <th>Detalle</th>
        </tr>
    </thead>
        <tbody>
        <% 
            if (listaMov != null && !listaMov.isEmpty()) {
                for (Movimiento movItem : listaMov) {
        %>
        <tr>
        
            <td><%= movItem.getFechaMovimiento() %></td>
            <td><%= movItem.getImporte() %></td>
            <td><%= movItem.getDetalle() %></td>
        </tr>
        <% 
                }
            } else { 
        %> 
        <tr>
            <td colspan="10">No se encontraron Movimientos.</td>
        </tr>
        <% 
            }
        %>
    </tbody>
</table>
  
    </fieldset>
</body>

</html>