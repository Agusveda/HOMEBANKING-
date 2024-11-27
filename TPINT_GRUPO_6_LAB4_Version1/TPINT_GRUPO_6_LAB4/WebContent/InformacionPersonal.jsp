<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>

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

<div class="encabezado">
	

</div>


<h1>INFORMACION DEL USUARIO</h1>
<form method="get" action="ServletHTML">

    <fieldset>
      <legend>Informacion personal</legend>
      <p>
        <label for="nombres" style="font-weight: bold">Nombre: </label>
        <label for="lblNombre"></label>
      </p>
      <p>
        <label for="apellido" style="font-weight: bold">Apellido: </label>
        <label for="lblApellido"></label>
      </p>
      <p>
        <label for="DNI" style="font-weight: bold">DNI: </label>
        <label for="lblDni"></label>
      </p>
      <p>
        <label for="CUIL" style="font-weight: bold">CUIL: </label>
        <label for="lblCUIL"></label>
      </p>
      <p>
        <label for="Sexo" style="font-weight: bold">Sexo: </label>
        <label for="lblSexo"></label>
      </p>
      <p>
        <label for="Nacionalidad" style="font-weight: bold">Nacionalidad: </label>
        <label for="lblNacionalidad"></label>
      </p>
      
	  <p>
    	<label for="Fecha" style="font-weight: bold">Fecha de nacimiento: </label>
	    <label for="lblFecha"></label>
  	  </p>
  	  <p>
        <label for="Direccion" style="font-weight: bold">Direccion: </label>
        <label for="lblDireccion"></label>
      </p>
      <p>
        <label for="Localidad" style="font-weight: bold">Localidad: </label>
        <label for="lblLocalidad"></label>
      </p>
      <p>
        <label for="Provincia" style="font-weight: bold">Provincia: </label>
        <label for="lblProvincia"></label>
      </p>
      <p>
        <label for="Correo" style="font-weight: bold">Correo: </label>
        <label for="lblCorreo"></label>
      </p>
      <p>
        <label for="Telefono" style="font-weight: bold">Telefono: </label>
        <label for="lblTelefono"></label>
      </p>
            
    </fieldset>
</form>

 <jsp:include page="Footer.jsp"/>

</body>

</html>