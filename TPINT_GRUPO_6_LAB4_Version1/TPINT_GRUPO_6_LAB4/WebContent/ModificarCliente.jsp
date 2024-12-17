<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="daoImp.ClienteDaoImp"%>
<%@page import="Entidades.Cliente"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/altclien.css">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">

</head>
<body>
<div class="encabezado">
        <h1>Modificar Cliente</h1>
</div>

<%
	String clienteId = request.getParameter("id"); // Recibe el parámetro 'id' desde la URL
	int id = 0;
    Cliente cli = new Cliente(); 
    	
	if (clienteId != null) {
    id = Integer.parseInt(clienteId); // Convierte el id a entero
    ClienteDaoImp ban = new ClienteDaoImp();
    cli = ban.ObtenerDatosXid(id);
    	
	} else 
	{
    out.println("No se ha proporcionado un ID válido.");
	}
%>


<%
/**
    String fechaNacimiento = cli.getFechaNacimiento();
    String[] partes = fechaNacimiento.split("-");
    String fechaFormateada = partes[2] + "-" + partes[1] + "-" + partes[0];
**/
%>


    <div class="form-register">

        
        <% 
	    	String mensaje = (String) request.getAttribute("mensaje");
	    	String mensajeError = (String) request.getAttribute("mensajeError");
	    %>
        
        <% if (mensaje != null) { %>
	    <div class="mensaje mensaje-exito"><%= mensaje %></div>
		<% } %>
		<% if (mensajeError != null) { %>
		    <div class="mensaje mensaje-error"><%= mensajeError %></div>
		<% } %>
        
        <form method="post" action="ServletBanco" onsubmit="return confirmarModificar(this)">
        <fieldset>
            <legend>Datos del Cliente</legend>

	        <p>
                <label class="form-label" for="id">ID Cliente</label>
                <input class="controls" id="id" readOnly="true" type="text" value="<%= id %>" required name="txtId">
            </p>
            <p>
                <label class="form-label" for="nombre">Nombre</label>
                <input class="controls" id="nombre" type="text" placeholder="Ingrese el nombre" value="<%= cli.getNombre() %>" required name="txtNombre" onkeypress="validarSoloLetras(event)">
            </p>
            <p>
                <label class="form-label" for="apellido">Apellido</label>
                <input class="controls" id="apellido" type="text" placeholder="Ingrese el apellido" value="<%= cli.getApellido() %>" required name="txtApellido" onkeypress="validarSoloLetras(event)">
            </p>
            <p>
                <label class="form-label" for="dni">DNI</label>
                <input class="controls" id="dni" type="number" maxlength="8" placeholder="Ingrese el DNI" Value="<%= cli.getDni() %>" required name="txtDNI" onkeypress="validarSoloNumeros(event)" oninput="validarLongitudDni(this)">
            </p>
            <p>
                <label class="form-label" for="cuil">CUIL</label>
                <input class="controls" id="cuil" type="number" maxlength="11"  placeholder="Ingrese el CUIL" Value="<%= cli.getCuil() %>" required name="txtCUIL" onkeypress="validarSoloNumeros(event)" oninput="validarLongitudCuil(this)" >
            </p>
            <p>
            		<label class="form-label" for="sexo">Sexo</label>
		           <select class="controls" id="sexo" required name="txtSexo">
					    <option value="">Seleccione</option>
					    <option value="Masculino" <%= "Masculino".equals(cli.getSexo()) ? "selected" : "" %>>Masculino</option>
					    <option value="Femenino" <%= "Femenino".equals(cli.getSexo()) ? "selected" : "" %>>Femenino</option>
					    <option value="Otro" <%= "Otro".equals(cli.getSexo()) ? "selected" : "" %>>Otro</option>
				   </select>
			</p>
                     <p>
				    <label class="form-label" for="fechaNacimiento">Fecha de Nacimiento</label>
				    <input 
				    	class="controls" 
				    	id="fechaNacimiento" 
				    	type="date" 
				        value="<%=cli.getFechaNacimiento()%>"         
				        maxlength="10" 
				        placeholder="YYYY-MM-DD" 
				        pattern="\d{4}-\d{2}-\d{2}" 
				        oninput="validateDateInput(this)"
				        required name="txtFechaNacimiento" />
					</p>
           
            <p>
                <label class="form-label" for="direccion">Dirección</label>
                <input class="controls" id="direccion" type="text" placeholder="Ingrese la dirección" Value="<%= cli.getDireccion() %>" required name="txtDireccion">
            </p>
            <p>
                <label class="form-label" for="nacionalidad">Nacionalidad</label>
                <input class="controls" id="nacionalidad" type="text" placeholder="Ingrese la nacionalidad" Value="<%= cli.getNacionalidad() %>" required name="txtNacionalidad">
            </p>
            <p>
                <label class="form-label" for="provincia">Provincia</label>
                <input class="controls" id="provincia" type="text" placeholder="Ingrese la provincia" Value="<%= cli.getProvincia() %>" required name="txtProvincia">
            </p>
            <p>
                <label class="form-label" for="localidad">Localidad</label>
                <input class="controls" id="localidad" type="text" placeholder="Ingrese la localidad" Value="<%= cli.getLocalidad() %>" required name="txtLocalidad">
            </p>
            <p>
                <label class="form-label" for="email">Correo Electrónico</label>
                <input class="controls" id="email" type="email" placeholder="Ingrese el correo electrónico" Value="<%= cli.getCorreoElectronico() %>" required name="txtEmail">
            </p>
            <p>
                <label class="form-label" for="telefono">Teléfono</label>
                <input class="controls" id="telefono" type="text" maxlength="9" placeholder="Ingrese el teléfono" Value="<%= cli.getTelefono() %>" required name="txtTelefono" onkeypress="validarSoloNumeros(event)">
            </p>
            <p>
                <label class="form-label" for="usuario">Usuario</label>
                <input class="controls" id="usuario" type="text" placeholder="Ingrese el nombre de usuario" value="<%= cli.getUsuario() %>" required name="txtUsuario">
            </p>
            <p>
                <label class="form-label" for="contrasena">Contraseña</label>
                <input class="controls" id="contrasena" type="password" readonly placeholder="Ingrese la contraseña" value="<%= cli.getContrasenia() %>" required name="txtContrasena">
            </p>
            
        </fieldset>
        <p>
            <input class="botons" id="btnAceptar" type="submit" value="Aceptar" name="btnModificarCliente">
       		
        </p>
       <button class="btnVolver" type="button" onclick="window.location.href='ListarCliente.jsp'" style="background-color: gray; color: white;">Cancelar</button>

       
    </form>
    </div>
    
    <script>
function confirmarModificar(form) 
{
    const confirmacion = confirm("¿Estás seguro de modificar este cliente?");
    if (confirmacion) {
        return true;
    } else {
        console.log("Modificacion cancelada.");
        return false;
    }
}
   
    
function validarSoloLetras(event) {
    const key = event.key;
    const regex = /^[a-zA-Z]+$/;

    if (!regex.test(key) && key !== "Backspace") {
        event.preventDefault(); // Evita que se ingrese el carácter no permitido
        alert("Solo se permiten letras.");
    }
}

function validarSoloNumeros(event) {
    const key = event.key;
    const regex = /^[0-9]$/;

    if (!regex.test(key) && key !== "Backspace" && key !== "Tab" && key !== "Delete" && key !== "ArrowLeft" && key !== "ArrowRight") {
        event.preventDefault(); // Evita que se ingrese un carácter no permitido
        alert("Solo se permiten números.");
    }
}

function validarLongitudDni(input) {
	const longitudExacta   = 8;
	 
    if (input.value.length > longitudExacta) {
        alert("El DNI no puede tener más de 8 dígitos.");
        input.value = input.value.slice(0, longitudExacta); // Recorta el valor a 11 caracteres
        return;
    }
    

}

function validarLongitudCuil(input) {
    const longitudExacta   = 11;
 
    if (input.value.length > longitudExacta) {
        alert("El CUIL no puede tener más de 11 dígitos.");
        input.value = input.value.slice(0, longitudExacta); // Recorta el valor a 11 caracteres
        return;
    }
    
}
function validateDateInput(input) {
    // Reemplaza caracteres no válidos y limita a 10 caracteres
    input.value = input.value.replace(/[^0-9-]/g, '').slice(0, 10);

    // Validar formato y mostrar error si es incorrecto
    const pattern = /^\d{4}-\d{2}-\d{2}$/; // Formato DD-MM-YYYY
    if (input.value && !pattern.test(input.value)) {
        input.setCustomValidity("Por favor, ingrese la fecha en el formato DD-MM-YYYY.");
    } else {
        input.setCustomValidity(""); // Limpiar el mensaje de error
    }
}

function validaYConfirma(form){
	if(!confirmarAlta(form)){
		return false;
	}
	return true;
	
}


</script>
    


</body>
</html>