<%@ page import="daoImp.CuentaDaoImpl" %>

<%@ page import="Entidades.Cuenta" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Listado de Cuentas</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/ABMCuenta.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
</head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
<script>
    $(document).ready(function () {
        $('#table_Cuenta').DataTable();
    });
</script>

<body>

<div class="encabezado">
    <h1>Listado de Cuentas</h1>
</div>

<form method="get" action="" id="filtroForm">
    <fieldset>
        <legend>Filtrar Cuentas por Tipo de Cuenta</legend>
        <p>
            <label for="tipoCuenta">Tipo de Cuenta:</label>
            <select id="tipoCuenta" name="tipoCuenta" onchange="this.form.submit()">
                <option value="-1" <%= "-1".equals(request.getParameter("tipoCuenta")) ? "selected" : "" %>>Todos</option>
                <option value="1" <%= "1".equals(request.getParameter("tipoCuenta")) ? "selected" : "" %>>Ahorro</option>
                <option value="2" <%= "2".equals(request.getParameter("tipoCuenta")) ? "selected" : "" %>>Cta. Corriente</option>
            </select>
        </p>
    </fieldset>
</form>

<%
    CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
    ArrayList<Cuenta> listaCuenta;
    int tipoCuenta = -1; // Valor predeterminado para "Todos"

    if (request.getParameter("tipoCuenta") != null) {
        try {
            tipoCuenta = Integer.parseInt(request.getParameter("tipoCuenta"));
        } catch (NumberFormatException e) {
            tipoCuenta = -1; // Si no es un número, usar "Todos"
        }
    }

    if (tipoCuenta != -1) {
        // Filtrar por Tipo de Cuenta
        listaCuenta = cuentaDao.filtrarCuentaXTipoCuenta(tipoCuenta);
    } else {
        // Mostrar todas las cuentas si no hay filtro aplicado
        listaCuenta = cuentaDao.ListarCuenta();
    }
%>

<!-- Tabla para Mostrar las Cuentas -->
<table id="table_Cuenta" class="display">
    <thead>
        <tr>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Tipo de Cuenta</th>
            <th>Fecha Creación</th>
            <th>Numero Cuenta</th>
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
            <td><%= cuentaItem.getCliente().getNombre() %></td>
            <td><%= cuentaItem.getCliente().getApellido() %></td>
            <td><%= cuentaItem.getTipoCuenta() == 1 ? "Ahorro" : "Cta. Corriente" %></td>
            <td><%= cuentaItem.getFechaCreacion() %></td>
            <td><%= cuentaItem.getNumeroCuenta() %></td>
            <td><%= cuentaItem.getCbu() %></td>
            <td>$<%= cuentaItem.getSaldo() %></td>
            <td>
                <form action="ModificarCuenta.jsp" method="get">
                    <input type="hidden" name="idCuenta" value="<%= cuentaItem.getId() %>">
                    <input type="submit" class="button button-blue" value="Modificar" name="btnModificar" />
                </form>
                <form action="ServletCuenta" method="post" onsubmit="return confirmarEliminacion(this)">
                    <input type="hidden" name="idCuenta" value="<%= cuentaItem.getId() %>">
                    <input type="submit" name="btnEliminar" value="Eliminar" style="background-color: red; color: white;" />
                </form>
            </td>
        </tr>
        <% 
                }
            } else { 
        %> 
        <tr>
            <td colspan="10">No se encontraron cuentas.</td>
        </tr>
        <% 
            }
        %>
    </tbody>
</table>

<script>
    function confirmarEliminacion(form) {
        const confirmacion = confirm("¿Estás seguro de eliminar esta cuenta?");
        if (confirmacion) {
            console.log("Eliminación confirmada para el formulario:", form);
            return true; // Permite enviar el formulario
        } else {
            console.log("Eliminación cancelada.");
            return false; // Cancela el envío del formulario
        }
    }
</script>

</body>
</html>
