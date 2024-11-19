<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="daoImp.BancodaoImp"%>
<%@page import="Entidades.Cliente"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/altclien.css">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="Navbar.jsp"/>

<% 
	String clienteId = request.getParameter("id"); // Recibe el par�metro 'id' desde la URL
	int id = 0;
    Cliente cli = new Cliente(); 
    	
	if (clienteId != null) {
    id = Integer.parseInt(clienteId); // Convierte el id a entero
    BancodaoImp ban = new BancodaoImp();
    cli = ban.ObtenerDatosXid(id);
    	// Aqu� puedes usar el id para cargar los datos del cliente o realizar alguna operaci�n
	} else 
	{
    out.println("No se ha proporcionado un ID v�lido.");
	}
%>

    <div class="form-register">
        <h4>Modificar Cliente</h4>
        
        <form method="post" action="ServletBanco">
        <fieldset>
            <legend>Datos del Cliente</legend>

	        <p>
                <label class="form-label" for="id">Id</label>
                <input class="controls" id="id" readOnly="true" type="text" value="<%= id %>" required name="txtId">
            </p>
            <p>
                <label class="form-label" for="nombre">Nombre</label>
                <input class="controls" id="nombre" type="text" placeholder="Ingrese el nombre" value="<%= cli.getNombre() %>" required name="txtNombre">
            </p>
            <p>
                <label class="form-label" for="apellido">Apellido</label>
                <input class="controls" id="apellido" type="text" placeholder="Ingrese el apellido" value="<%= cli.getApellido() %>" required name="txtApellido">
            </p>
            <p>
                <label class="form-label" for="dni">DNI</label>
                <input class="controls" id="dni" type="number" placeholder="Ingrese el DNI" Value="<%= cli.getDni() %>" required name="txtDNI">
            </p>
            <p>
                <label class="form-label" for="cuil">CUIL</label>
                <input class="controls" id="cuil" type="number" placeholder="Ingrese el CUIL" Value="<%= cli.getCuil() %>" required name="txtCUIL">
            </p>
            <p>
                <label class="form-label" for="sexo">Sexo</label>
                <select class="controls" id="sexo" Value="<%= cli.getSexo() %>" required name="txtSexo">
                    <option value="">Seleccione</option>
                    <option value="Masculino">Masculino</option>
                    <option value="Femenino">Femenino</option>
                    <option value="Otro">Otro</option>
                </select>
            </p>
            <p>
                <label class="form-label" for="nacionalidad">Nacionalidad</label>
                <input class="controls" id="nacionalidad" type="text" placeholder="Ingrese la nacionalidad" Value="<%= cli.getNacionalidad() %>" required name="txtNacionalidad">
            </p>
            <p>
                <label class="form-label" for="fechaNacimiento">Fecha de Nacimiento</label>
                <input class="controls" id="fechaNacimiento" type="date" Value="<%= cli.getFechaNacimiento() %>" required name="txtFechaNacimiento">
                
            </p>
            <p>
                <label class="form-label" for="direccion">Direcci�n</label>
                <input class="controls" id="direccion" type="text" placeholder="Ingrese la direcci�n" Value="<%= cli.getDireccion() %>" required name="txtDireccion">
            </p>
            <p>
                <label class="form-label" for="localidad">Localidad</label>
                <input class="controls" id="localidad" type="text" placeholder="Ingrese la localidad" Value="<%= cli.getLocalidad() %>" required name="txtLocalidad">
            </p>
            <p>
                <label class="form-label" for="provincia">Provincia</label>
                <input class="controls" id="provincia" type="text" placeholder="Ingrese la provincia" Value="<%= cli.getProvincia() %>" required name="txtProvincia">
            </p>
            <p>
                <label class="form-label" for="email">Correo Electr�nico</label>
                <input class="controls" id="email" type="email" placeholder="Ingrese el correo electr�nico" Value="<%= cli.getCorreoElectronico() %>" required name="txtEmail">
            </p>
            <p>
                <label class="form-label" for="telefono">Tel�fono</label>
                <input class="controls" id="telefono" type="text" placeholder="Ingrese el tel�fono" Value="<%= cli.getTelefono() %>" required name="txtTelefono">
            </p>
                        <p>
                <label class="form-label" for="usuario">Usuario</label>
                <input class="controls" id="usuario" type="text" placeholder="Ingrese el nombre de usuario" value="<%= cli.getUsuario() %>" required name="txtUsuario">
            </p>
            <p>
                <label class="form-label" for="contrasena">Contrase�a</label>
                <input class="controls" id="contrasena" type="password" placeholder="Ingrese la contrase�a" value="<%= cli.getContrasenia() %>" required name="txtContrasena">
            </p>
            
        </fieldset>
        <p>
            <input class="botons" id="btnAceptar" type="submit" value="Aceptar" name="btnModificarCliente">
        </p>
    </form>
    </div>
    

<jsp:include page="Footer.jsp"/>

</body>
</html>