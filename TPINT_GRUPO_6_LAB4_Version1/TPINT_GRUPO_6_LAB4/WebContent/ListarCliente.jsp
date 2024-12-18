<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="daoImp.ClienteDaoImp"%>
<%@ page import="Entidades.Cliente"%>
<%@ page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html>
<head>
    <title>Listado de Clientes</title>
     <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/ABMCliente.css">
        <link rel="stylesheet" type="text/css" href="css/Footer.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">

    <style>
        .selected-row {
            background-color: #d3e0ea;
        }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
</head>
<body>

<div class="encabezado">
    <h1>Listado de Clientes</h1>
</div>

<form method="get" action="" id="filtroForm">
    <fieldset>
    <legend>Filtrar Clientes por Género</legend>
        <p>
            <label for="sexo">Género:</label>
            <select id="sexo" name="sexo" onchange="this.form.submit()">
                <option value="todos" <%="todos".equals(request.getParameter("sexo")) ? "selected" : ""%>>Todos</option>
                <option value="Masculino" <%="Masculino".equals(request.getParameter("sexo")) ? "selected" : ""%>>Masculino</option>
                <option value="Femenino" <%="Femenino".equals(request.getParameter("sexo")) ? "selected" : ""%>>Femenino</option>
                <option value="Otro" <%="Otro".equals(request.getParameter("sexo")) ? "selected" : ""%>>Otro</option>
            </select>
        </p>
    </fieldset>
</form>

<%
	ClienteDaoImp banco = new ClienteDaoImp();
	ArrayList<Cliente> lista;
	String sexo = request.getParameter("sexo");
	System.out.println("Sexo seleccionado: " + sexo);
	
	if (sexo == null || sexo.trim().isEmpty() ||sexo.equals("todos")) {
		System.out.println("Obteniendo todos los clientes.");
	    lista = banco.ListarCliente();
	} else {
		System.out.println("Filtrando por género: " + sexo);
	    lista = banco.filtrarClienteXsexo(sexo);
	}
	
	if (lista == null || lista.isEmpty()) {
        System.out.println("No se encontraron clientes para el filtro: " + (sexo == null ? "null" : sexo));
    } else {
        System.out.println("Cantidad de clientes encontrados: " + lista.size());
    }
	
%>

<table id="clientes_table" class="display">
    <thead>
        <tr>
           <th>ID</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>DNI</th>
            <th>CUIL</th>
            <th>Sexo</th>
            <th>Nacionalidad</th>
            <th>Fecha de Nacimiento</th>
            <th>Dirección</th>
            <th>Localidad</th>
            <th>Provincia</th>
            <th>Correo Electrónico</th>
            <th>Teléfono</th>
            <th>Usuario</th>
            <th>Contraseña</th>
        </tr>
    </thead>
    <tbody>
        <% if (lista != null && !lista.isEmpty()) {
            for (Cliente cli : lista) { %>
                <tr onclick="selectRow(this)">
                     <td><%= cli.getId() %></td>
                    <td><%= cli.getNombre() %></td>
                    <td><%= cli.getApellido() %></td>
                    <td><%= cli.getDni() %></td>
                    <td><%= cli.getCuil()%></td>
                    <td><%= cli.getSexo() %></td>
                    <td><%= cli.getNacionalidad() %></td>
                    <td><%= cli.getFechaNacimiento() %></td>
                    <td><%= cli.getDireccion() %></td>
                    <td><%= cli.getLocalidad() %></td>
                    <td><%= cli.getProvincia() %></td>
                    <td><%= cli.getCorreoElectronico() %></td>
                    <td><%= cli.getTelefono() %></td>
                 	<td><%= cli.getUsuario() %></td>
                    <td><%= cli.getContrasenia() %></td>
                </tr>
            <% }
        } else { %>
            <tr>
                <td colspan="13" style="text-align: center;">No se encontraron clientes :(</td>
            </tr>
        <% } %>
    </tbody>
</table>

<div>
    <button type="button" class="button button-blue" onclick="modificarCliente()">Modificar Cliente</button>
    <button type="button" class="button button-red" onclick="eliminarCliente()">Eliminar Cliente</button>
</div>

<script>
    let selectedRow = null;
    let selectedId = null;

    $(document).ready(function() {
        $('#clientes_table').DataTable().destroy(); // Elimina cualquier inicialización previa
        $('#clientes_table').DataTable();          // Inicializa la tabla de nuevo
    });


    
    function selectRow(row) {
        if (selectedRow) {
            selectedRow.classList.remove("selected-row");
        }
        row.classList.add("selected-row");
        selectedRow = row;
        selectedId = row.cells[0].innerText;
        console.log("ID seleccionado:", selectedId);
    }

    function modificarCliente() {
        if (selectedId) {
            window.location.href = 'ModificarCliente.jsp?id=' + selectedId;
        } else {
            alert("Por favor, selecciona una fila primero.");
        }
    }

    function eliminarCliente() {
        if (selectedId) {
            let confirmDelete = confirm("¿Estás seguro de que quieres eliminar este cliente?");
            if (confirmDelete) {
                window.location.href = 'ServletBanco?idCliente=' + selectedId;
            }
        } else {
            alert("Por favor, selecciona una fila primero.");
        }
    }

    function actualizarFiltro() {
        // Evitar recargar la página completa. En su lugar, reenvía solo el filtro.
        var form = document.getElementById('filtroForm');
        form.submit(); // Esto hará que se re-renderice el JSP con el filtro aplicado.
    }
    
   

</script>