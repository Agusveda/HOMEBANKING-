<%@page import="Entidades.Provincia"%>
<%@page import="negocioImpl.ClienteNegocioImpl"%>
<%@page import="negocio.ClienteNegocio"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="Entidades.Nacionalidades"%>
<%@page import="Entidades.Localidad"%>
<%@page import="Entidades.Provincia"%>

<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/altclien.css">
    <title>Alta de Cliente</title>
    
    <style>
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
<body>
<jsp:include page="Navbar.jsp"/>

  <div class="form-register">
        <h4>Alta de Cliente</h4>
        
        <% 
	    String mensaje = (String) request.getAttribute("mensaje");
	    String mensajeError = (String) request.getAttribute("mensajeError");
	    %>
		    <% if (mensaje != null) { %>
			    <div class="mensaje mensaje-exito">
			        <%= mensaje %>
			    </div>
			<% } %>
			<% if (mensajeError != null) { %>
			    <div class="mensaje mensaje-error">
			        <%= mensajeError %>
			    </div>
			<% } %>

		       

        <!-- Formulario para registrar cliente -->
        <form method="POST" action="ServletBanco" onsubmit="return validaYConfirma(this)">
        <input type="hidden" name="action" value="loadProvinces">
        
           <p>
    <label class="form-label" for="nacionalidad">Nacionalidad</label>
    <select class="controls" id="nacionalidad" required name="txtNacionalidad" onchange="this.form.submit()">
        <option value="">Seleccione</option>
        <option value="1" <%= "1".equals(request.getParameter("txtNacionalidad")) ? "selected" : "" %>>Argentina</option>
        <option value="2" <%= "2".equals(request.getParameter("txtNacionalidad")) ? "selected" : "" %>>Brasil</option>
        <option value="3" <%= "3".equals(request.getParameter("txtNacionalidad")) ? "selected" : "" %>>Chile</option>
        <option value="4" <%= "4".equals(request.getParameter("txtNacionalidad")) ? "selected" : "" %>>Uruguay</option>
        <option value="5" <%= "5".equals(request.getParameter("txtNacionalidad")) ? "selected" : "" %>>Paraguay</option>
    </select>
</p>

   <p>
    <label class="form-label" for="provincia">Provincia</label>
    <select class="controls" id="provincia" name="txtProvincia" required onchange="this.form.submit()">
        <option value="">Seleccione</option>
        <%
            if (request.getAttribute("provincias") != null) {
                ArrayList<Provincia> provincias = (ArrayList<Provincia>) request.getAttribute("provincias");
                String provinciaSeleccionada = request.getParameter("txtProvincia"); // Obtener el valor de la provincia seleccionada

                for (Provincia provincia : provincias) {
        %>
        <option value="<%= provincia.getId() %>" <%= provinciaSeleccionada != null && provinciaSeleccionada.equals(String.valueOf(provincia.getId())) ? "selected" : "" %>>
    <%= provincia.getProvincia() %>
</option>
        <% 
                }
            }
        %>
    </select>
  
</p>
<p>
    <label class="form-label" for="localidad">Localidad</label>
    <select class="controls" id="localidad" name="txtLocalidad" required required onchange="this.form.submit()">
        <option value="">Seleccione</option>
        <%
            if (request.getAttribute("localidades") != null) {
                ArrayList<Localidad> localidades = (ArrayList<Localidad>) request.getAttribute("localidades");
                String localidadSeleccionada = request.getParameter("txtLocalidad"); // Obtener el valor de la localidad seleccionada

                for (Localidad localidad : localidades) {
        %>
        <option value="<%= localidad.getIdLocalidad() %>" <%= localidadSeleccionada != null && localidadSeleccionada.equals(String.valueOf(localidad.getIdLocalidad())) ? "selected" : "" %>>
            <%= localidad.getLocalidad() %>
        </option>
        <% 
                }
            }
        %>
    </select>
  
</p>
<fieldset>
                <legend>Datos del Cliente</legend>
                <p>
                    <label class="form-label" for="nombre">Nombre</label>
                    <input class="controls" id="nombre" type="text" placeholder="Ingrese el nombre" required name="txtNombre" onkeypress="validarSoloLetras(event)">
                    
                </p>
                <p>
                    <label class="form-label" for="apellido">Apellido</label>
                    <input class="controls" id="apellido" type="text" placeholder="Ingrese el apellido" required name="txtApellido" onkeypress="validarSoloLetras(event)">
                </p>
                <p>
                    <label class="form-label" for="dni">DNI</label>
                    <input class="controls" id="dni" type="number" placeholder="Ingrese el DNI" required name="txtDNI" onkeypress="validarSoloNumeros(event)" oninput="validarLongitudDni(this)">
                </p>
                <p>
                    <label class="form-label" for="cuil">CUIL</label>
                    <input class="controls" id="cuil" type="number" placeholder="Ingrese el CUIL" required name="txtCUIL" onkeypress="validarSoloNumeros(event)" oninput="validarLongitudCuil(this)">
                </p>
                <p>
                    <label class="form-label" for="sexo">Sexo</label>
                    <select class="controls" id="sexo" required name="txtSexo">
                        <option value="">Seleccione</option>
                        <option value="Masculino">Masculino</option>
                        <option value="Femenino">Femenino</option>
                        <option value="Otro">Otro</option>
                    </select>
                </p>
    <p>
    
                <p>
                    <label class="form-label" for="fechaNacimiento">Fecha de Nacimiento</label>
                    <input class="controls" id="fechaNacimiento" type="date" required name="txtFechaNacimiento">
                </p>
                <p>
                    <label class="form-label" for="direccion">Dirección</label>
                    <input class="controls" id="direccion" type="text" placeholder="Ingrese la dirección" required name="txtDireccion">
                </p>
                <p>
                    <label class="form-label" for="email">Correo Electrónico</label>
                    <input class="controls" id="email" type="email" placeholder="Ingrese el correo electrónico" required name="txtEmail">
                </p>
                <p>
                    <label class="form-label" for="telefono">Teléfono</label>
                    <input class="controls" id="telefono" type="text" placeholder="Ingrese el teléfono" required name="txtTelefono" onkeypress="validarSoloNumeros(event)">
                </p>
                <p>
                    <label class="form-label" for="usuario">Usuario</label>
                    <input class="controls" id="usuario" type="text" placeholder="Ingrese el nombre de usuario" required name="txtUsuario">
                </p>
                <p>
                    <label class="form-label" for="contrasena">Contraseña</label>
                    <input class="controls" id="contrasena" type="password" placeholder="Ingrese la contraseña" required name="txtContrasena1">
                </p>
                <p>
                    <label class="form-label" for="contrasena2">Confirmar Contraseña</label>
                    <input class="controls" id="contrasena2" type="password" placeholder="Confirme la contraseña" required name="txtContrasena2">
                </p>
                 <input type="hidden" name="action" value="ValidarContraseñas">
				<p>
				    <label class="form-label" for="TipoUsuario">Tipo Usuario</label>
				    <select class="controls" id="TipoUsu" required name="txtTipoUsuario">
				        <option value="">Seleccione</option>
				        <option value="1">Admin</option>
				        <option value="0">Cliente</option>
				    </select>
				</p>

                <!-- indica la acción -->
                <input type="hidden" name="action" value="ValidarContraseñas">

            </fieldset>
            
            <div class="botones-container">
                <input class="botons" id="btnAceptar" type="submit" value="Aceptar" required name="btnAltaCliente">
                <input class="botons" type="submit" value="Volver" id="btnVolver" onclick="window.location.href='Administrador.jsp'; return false;">
            </div>
            
        </form>
    </div>

<!-- Script para mostrar el modal -->
<script>
function confirmarAlta(form) 
{
    const confirmacion = confirm("¿Estás seguro de dar Alta este cliente?");
    if (confirmacion) {
        return true;
    } else {
        console.log("Alta cancelada.");
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


function validarContraseñas() {
    const contrasena1 = document.getElementById("contrasena").value;
    const contrasena2 = document.getElementById("contrasena2").value;

    if (contrasena1 !== contrasena2) {
        alert("Las contraseñas no coinciden. Por favor, intente nuevamente.");
        return false; 
    }

    return true; 
}

function validaYConfirma(form){
	const dni = form.txtDNI.value.trim();
    const cuil = form.txtCUIL.value.trim();

    if (dni.length !== 8) {
        alert("El DNI debe tener exactamente 8 dígitos.");
        return false;
    }

    if (cuil.length !== 11) {
        alert("El CUIL debe tener exactamente 11 dígitos.");
        return false;
    }
	
	
	if(!validarContraseñas()){
		return false;
	}
	if(!confirmarAlta(form)){
		return false;
	}
	return true;
}


</script>
    
    
    <jsp:include page="Footer.jsp"/>
</body>
</html>
