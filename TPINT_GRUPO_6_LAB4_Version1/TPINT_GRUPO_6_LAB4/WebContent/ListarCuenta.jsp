<%@ page import="daoImp.CuentaDaoImpl" %>
<%@ page import="Entidades.Cuenta" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Listado de Cuentas</title>
    <link rel="stylesheet" type="text/css" href="css/Cliente.css">
    <link rel="stylesheet" type="text/css" href="css/ABMCuenta.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
    

    
    
</head>
<body>
<jsp:include page="Navbar.jsp"/>
<div class="encabezado">
    <h1>Listado de Cuentas</h1>
</div>

<form method="post" action="ServletCuenta" id="filtroForm">
    <fieldset>
        <legend>Buscar Cuenta</legend>
        <p>
            <label for="buscar">Numero de DNI:</label>
            <input id="buscar" type="text" name="txtBuscar">
            <input type="submit" value="Buscar" name="btnFiltrar">
        </p>
    </fieldset>
</form>

<%
	CuentaDaoImpl cuenta = new CuentaDaoImpl();
    ArrayList<Cuenta> listaCuenta;
 
    		if ((ArrayList<Cuenta>)request.getAttribute("listaCuentaFiltrada") != null){
    			
    			listaCuenta =(ArrayList<Cuenta>)request.getAttribute("listaCuentaFiltrada");
    		}
    		else {
    			listaCuenta = cuenta.ListarCuenta();
    
    		}  
    		
   
%>

<table id="table_Cuenta" class="display">
    <thead>
        <tr>
            <th>ID</th>
            <th>id cliente</th>
            <th>Tipo de Cuenta</th>
            <th>Fecha Creacion</th>
            <th>Numero Cuenta</th>
            <th>CBU</th>
            <th>Saldo</th>
            <th>Activo</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <% 
            if (listaCuenta != null && !listaCuenta.isEmpty()) {
                for (Cuenta cuentaItem : listaCuenta) {  
        %>
            <tr>
                <form action="ModificarCuenta.jsp" method="get">
                    <td><input type="hidden" name="idCuenta" value="<%= cuentaItem.getId() %>"></td>
                    <td><%= cuentaItem.getIdCliente() %></td>
                    <td><%= cuentaItem.getTipoCuenta() %></td>
                    <td><%= cuentaItem.getFechaCreacion() %></td>
                    <td><%= cuentaItem.getNumeroCuenta() %></td>
                    <td><%= cuentaItem.getCbu() %></td>
                    <td><%= cuentaItem.getSaldo() %></td>
                    <td><%= cuentaItem.isActivo() %></td>
                    <td>
                        <input type="submit" class="button button-blue" value="Modificar" name="btnModificar"/>
                        <input type="submit" class="button button-red" value="Eliminar" name="btnEliminar"/>
                    </td>
                </form>
            </tr>
        <% 
                }
            } else { 
        %> 
            <tr>
                <td colspan="9">No se encontraron cuentas.</td>
            </tr>
        <% 
            }
        %>
    </tbody>
</table>

<jsp:include page="Footer.jsp"/>
</body>
</html>
