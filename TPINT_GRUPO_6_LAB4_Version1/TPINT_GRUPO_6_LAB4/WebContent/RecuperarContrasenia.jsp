<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="negocioImpl.ClienteNegocioImpl" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	<jsp:include page="css/recuperarPassword.css"></jsp:include>
	
</style>
<title>Recuperar contraseña</title>
</head>
<body>
<jsp:include page="Navbar.jsp"/>

	

</div>

    
<div class="form-register">

<% 
		//Declaración de variables para mensajes
	    String mensaje = (String) request.getAttribute("mensaje");
	    String mensajeError = (String) request.getAttribute("mensajeError");
	    
	 // Verificar si el formulario fue enviado
	    String mail = request.getParameter("txtEmail");
	    String nuevaContraseña = request.getParameter("txtPassNue");
	    
	    System.out.println("Email recibido: " + mail);
	    System.out.println("Nueva contraseña recibida: " + nuevaContraseña);

	    if (mail != null && nuevaContraseña != null) {
	        ClienteNegocioImpl clientNegocio = new ClienteNegocioImpl();

	        // Verificar si el email existe
	        if (clientNegocio.existeEmail(mail)) {
	            boolean result = clientNegocio.actualizarContrasenaPorEmail(mail, nuevaContraseña);

	            if (result) {
	                mensaje = "Contraseña actualizada exitosamente.";
	            } else {
	                mensajeError = "Error al actualizar la contraseña. Intente nuevamente.";
	            }
	        } else {
	            mensajeError = "No se encontró un usuario con ese correo.";
	        }
	    }
	      
%>
        
        <!-- Mensajes de éxito o error -->
	    <% if (mensaje != null) { %>
	    <div class="mensaje mensaje-exito"><%= mensaje %></div>
		<% } %>
		<% if (mensajeError != null) { %>
		    <div class="mensaje mensaje-error"><%= mensajeError %></div>
		<% } %>

    <form method="post" action="">
      <h2>¡Hola! ¿quieres recuperar la contraseña?</h2>
      <p class="subtitulo">Complete con el mail.</p>
      
      <fieldset>
      	<div class="mb-3">
        <label for="">Mail:</label>
        <input type="text" id="valida_Mail" class = "controls" required name="txtEmail"><br><br>
        
        <label for="">Contraseña nueva:</label>
        <input type="text" id="nuevacontrseña" class = "controls" required name="txtPassNue"><br><br>
        
        <br>

        <input type="submit" class="btnCambiar" value="Cambiar">
        <input type="submit" class="btnVolver" value="Volver" id="btnVolver" onclick="window.location.href='Login.jsp'; return false;">
        </div>
      </fieldset>
    </form>
</div>

 <jsp:include page="Footer.jsp"/>

</body>

</html>