<%@ page import="daoImp.CuentaDaoImpl" %>
<%@ page import="Entidades.Cuenta" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Listado de Cuentas</title>
   
    <link rel="stylesheet" type="text/css" href="css/ABMCuenta.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">   
</head>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <script>
    $(document).ready(function() {
        $('#table_Cuenta').DataTable();
    });
</script>
    
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
    		}if ((ArrayList<Cuenta>)request.getAttribute("listaCuenta") != null){  			
    			listaCuenta =(ArrayList<Cuenta>)request.getAttribute("listaCuenta");
    		}
    		else {
    			listaCuenta = cuenta.ListarCuenta();    
    		}  
%>

    
<table id="table_Cuenta" class="display">
    <thead>
        <tr>
        	<th>ID Cuenta</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>DNI</th>
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
      <tr onclick="selectRow(this)">

   		<td><%= cuentaItem.getId() %></td>
        <td><%= cuentaItem.getCliente().getNombre() %></td>
        <td><%= cuentaItem.getCliente().getApellido() %></td>
        <td><%= cuentaItem.getCliente().getDni() %></td>
        <td><%= cuentaItem.getTipoCuenta() %></td>
        <td><%= cuentaItem.getFechaCreacion() %></td>
        <td><%= cuentaItem.getNumeroCuenta() %></td>
        <td><%= cuentaItem.getCbu() %></td>
        <td>$<%= cuentaItem.getSaldo() %></td>
        <td><%= cuentaItem.isActivo() ? "Sí" : "No" %></td>
        <td>
            <form action="ModificarCuenta.jsp" method="get">
            <input type="hidden" name="idCuenta" value="<%= cuentaItem.getId() %>">
            <input type="submit" class="button button-blue" value="Modificar" name="btnModificar"/>
            </form>
				<form action="ServletCuenta" method="post">
    <input type="hidden" name="idCuenta" value="<%= cuentaItem.getId() %>">
    <button type="submit" name="btnEliminar" style="background-color: red; color: white;">Eliminar</button>
</form>

        </td>
    
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
