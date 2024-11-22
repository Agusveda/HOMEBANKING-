<%@page import="Entidades.Provincia"%>
<%@page import="negocioImpl.ClienteNegocioImpl"%>
<%@page import="negocio.ClienteNegocio"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="Entidades.Nacionalidades"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/altclien.css">
    <title>Alta de Cliente</title>
</head>
<body>
<jsp:include page="Navbar.jsp"/>

  <div class="form-register">
        <h4>Alta de Cliente</h4>
        
        <% 
	    String mensaje = (String) request.getAttribute("mensaje");
	    String mensajeError = (String) request.getAttribute("mensajeError");
	    %>
        
        <!-- Mensajes de éxito o error -->
	    <% if (mensaje != null) { %>
	    <div class="mensaje mensaje-exito"><%= mensaje %></div>
		<% } %>
		<% if (mensajeError != null) { %>
		    <div class="mensaje mensaje-error"><%= mensajeError %></div>
		<% } %>

        <!-- Formulario para registrar cliente -->
        <form method="post" action="ServletBanco">
            <fieldset>
                <legend>Datos del Cliente</legend>

                <p>
                    <label class="form-label" for="nombre">Nombre</label>
                    <input class="controls" id="nombre" type="text" placeholder="Ingrese el nombre" required name="txtNombre">
                    
                </p>
                <p>
                    <label class="form-label" for="apellido">Apellido</label>
                    <input class="controls" id="apellido" type="text" placeholder="Ingrese el apellido" required name="txtApellido">
                </p>
                <p>
                    <label class="form-label" for="dni">DNI</label>
                    <input class="controls" id="dni" type="number" placeholder="Ingrese el DNI" required name="txtDNI">
                </p>
                <p>
                    <label class="form-label" for="cuil">CUIL</label>
                    <input class="controls" id="cuil" type="number" placeholder="Ingrese el CUIL" required name="txtCUIL">
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
                    <label class="form-label" for="nacionalidad">Nacionalidad</label>
                    <select class="controls" id="nacionalidad" required name="txtNacionalidad" onchange="this.form.submit()">
                        <option value="">Seleccione</option>
                        <%
                        	ClienteNegocioImpl CliNeg = new ClienteNegocioImpl();
                    		
                        	ArrayList<Nacionalidades> listaNac = CliNeg.ListNacionaliadaes(); 
                    		
                    		String NacionalidadSeleccionada = request.getParameter("txtNacionalidad");
                    		
                    		if (listaNac != null){
                    			for (Nacionalidades nac : listaNac){
                    				boolean seleccionado = NacionalidadSeleccionada != null && NacionalidadSeleccionada.equals(String.valueOf(nac.getId()));
                    	%>		
                    			<option value="<%= nac.getId() %>" <%= seleccionado ? "selected" : "" %>><%= nac.getNombre() %></option>
                    	<%
                    			}
                    		}
                        %>
                       
                    </select>
                </p>
               
                <label class="form-label" for="provincia">Provincia</label>
				<select class="controls" id="provincia" required name="txtProvincia">
				    <option value="">Seleccione</option>
				    <%
				        ClienteNegocioImpl CliNe = new ClienteNegocioImpl();
				        ArrayList<Provincia> listaPro = CliNe.listProvincias(); 
				        if (listaPro != null) {
				            for (Provincia Pro : listaPro) {
				    %>		
				        <option value="<%= Pro.getId() %>">
				            <%= Pro.getProvincia() %> (Nacionalidad ID: <%= Pro.getIdNacionalidad() %>)
				        </option>
				    <%
				            }
				        }
				    %>
				</select>


                <p>
                    <label class="form-label" for="localidad">Localidad</label>
                    <input class="controls" id="localidad" type="text" placeholder="Ingrese la localidad" required name="txtLocalidad" onchange="cargarLocalidades()" >
                </p>
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
                    <input class="controls" id="telefono" type="text" placeholder="Ingrese el teléfono" required name="txtTelefono">
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
    
    <jsp:include page="Footer.jsp"/>
</body>
</html>
