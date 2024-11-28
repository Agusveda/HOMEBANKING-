<%@page import="negocioImpl.ClienteNegocioImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="daoImp.ClienteDaoImp"%>
<%@page import="Entidades.Cliente"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>

<jsp:include page="css/Informacionpersonal.css"></jsp:include>
<jsp:include page="css/Cliente.css"></jsp:include>
	
	        .footer {
		    background-color: #408080;
		    color: #fff;
		    text-align: center;
		    padding: 10px;
		    position: fixed;
		    bottom: 0;
		    width: 100%;
		    font-size: 0.9em;
		    border-radius: 0px 0px 10px 10px; 
		}
</style>

</head>
<body>
<jsp:include page="Navbar.jsp"/>

<%
	String clie = session.getAttribute("IdCliente").toString();
	ClienteNegocioImpl cn = new ClienteNegocioImpl();
	Cliente cli = cn.ObtenerDatosXid(Integer.parseInt(clie));
	
	if(cli==null){
		System.out.println(" No se encontro informacion del cliente " );
	}else{
%>

<h1>INFORMACION DEL USUARIO</h1>
<form method="post" action="ServletUsuario">

    <fieldset>
      <legend>Informacion Personal</legend>
      <p>
        <label class="form-label" for="nombre">Nombre:</label>
        <input class="" id="nombre" readOnly="true" type="text" value="<%= cli.getNombre()%>" required name="txtNombre">
      </p>
      <p>
        <label class="form-label" for="apellido">Apellido:</label>
        <input class="" id="apellido" readOnly="true" type="text" value="<%= cli.getApellido()%>" required name="txtApellido">
      </p>
      <p>
      	<label class="form-label" for="DNI">DNI:</label>
        <input class="" id="dni" readOnly="true" type="text" value="<%= cli.getDni()%>" required name="txtdni">
      </p>
      <p>
      	<label class="form-label" for="CUIL">CUIL:</label>
        <input class="" id="cuil" readOnly="true" type="text" value="<%= cli.getCuil()%>" required name="txtcuil">
      </p>
      <p>
      	<label class="form-label" for="Sexo">Sexo:</label>
        <input class="" id="sexo" readOnly="true" type="text" value="<%= cli.getSexo()%>" required name="txtsexo">
      </p>
      <p>
      	<label class="form-label" for="Nacionalidad">Nacionalidad:</label>
        <input class="" id="nacionalidad" readOnly="true" type="text" value="<%= cli.getNacionalidad()%>" required name="txtnacionalidad">
      </p>
      
	  <p>
	  	<label class="form-label" for="Fecha">Fecha de nacimiento:</label>
        <input class="" id="fecha" readOnly="true" type="text" value="<%= cli.getFechaNacimiento()%>" required name="txtfechaNac">
  	  </p>
  	  <p>
  	  	<label class="form-label" for="Direccion">Direccion:</label>
        <input class="" id="direccion" readOnly="true" type="text" value="<%= cli.getDireccion()%>" required name="txtDireccion">
      </p>
      <p>
      	<label class="form-label" for="Localidad">Localidad:</label>
        <input class="" id="Localidad" readOnly="true" type="text" value="<%= cli.getLocalidad()%>" required name="txtlocalidad">
      </p>
      <p>
      	<label class="form-label" for="Provincia">Provincia:</label>
        <input class="" id="Provincia" readOnly="true" type="text" value="<%= cli.getProvincia()%>" required name="txtprovincia">
      </p>
      <p>
      	<label class="form-label" for="Correo">Correo Electronico:</label>
        <input class="" id="correoElectronico" readOnly="true" type="text" value="<%= cli.getCorreoElectronico()%>" required name="txtCorreoElectronico">
      </p>
      <p>
      	<label class="form-label" for="Telefono">Telefono:</label>
        <input class="" id="Telefono" readOnly="true" type="text" value="<%= cli.getTelefono()%>" required name="txtTelefono">
      </p>
            
    </fieldset>
    <input type = "submit" class="btn-volver" name="btnVolver" value="volver" onclick="window.location.href='Cliente.jsp'; return false;">
    
</form>
	<%
        }
    %>

 <jsp:include page="Footer.jsp"/>

</body>

</html>