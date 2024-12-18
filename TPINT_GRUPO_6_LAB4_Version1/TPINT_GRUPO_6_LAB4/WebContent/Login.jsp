<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	<jsp:include page="css/Login.css"></jsp:include>
</style>
<title>Login</title>
<script src="css/passlog.js" defer></script>
</head>
<body>

<jsp:include page="Navbar.jsp"/>

<div class="contenedorPrincipal">
    <div class="imagenBienvenida">
      <img src="img/logoPrueba.png" alt="Imagen de bienvenida">
       <h1>CodeBank</h1>
    </div>
    
<div class="parteDer">
    <form method="post" action="ServletBanco">
      <h2 class="bienvenida">¡Hola! Te damos la bienvenida</h2>
      <p class="subtitulo">Completá tus datos y empezá a operar.</p>
      
      <fieldset>
        <legend>Login</legend>
        <c:if test="${not empty mensajeError}">
    <div style="color: red; font-weight: bold;">
        ${mensajeError}
    </div>
    
</c:if>
        <label for="username">Usuario:</label>
        <input type="text" id="username" placeholder="Username" required name="txtuser"><br><br>

        <label for="password">Contraseña:</label>
        
        <div class="password-container">
            <input type="password" id="password" placeholder="Password" required name="txtpass">
            
            <button type="button" class="toggle-password" onclick="togglePasswordVisibility()">
               	 👁️
            </button>
        </div>
        <br>
       	<a href="RecuperarContrasenia.jsp" class="password-link" > Olvide mi contraseña</a>

        <input type="submit" class="btnSec" value="Iniciar sesión">
      </fieldset>
    </form>
    
</div>
</div>
    <%
    String username = request.getParameter("txtuser");
    String password = request.getParameter("txtpass");

    System.out.println("Usuario: " + username);
    System.out.println("Contraseña: " + password);
    %>

<jsp:include page="Footer.jsp"/>
</body>
</html>