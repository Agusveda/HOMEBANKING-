<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CodeBank - Sistema Bancario</title>
    <link rel="stylesheet" href="css/Navbar.css">
</head>
<body>
    <!-- Navbar -->
    <div class="navegador">
        <div class="nav-brand-container">
            <a class="nav-brand" href="Cliente.jsp">CodeBank</a>
            
            <% 
                String nombreCliente = (String) session.getAttribute("nombreCliente");
                if (nombreCliente != null) { // Si el usuario est� logueado
            %>
            
			<a class="logoNav" href="Cliente.jsp" title="Ir a la p�gina de inicio">
			    <img src="img/icoHome.png" alt="�cono de la p�gina de inicio" />
			</a>
			
			<a href="InformacionPersonal.jsp" title="Ir a mi perfil">
			    <img src="img/icoInfo.png" alt="�cono de informaci�n" />
			</a>
			
			<div class="nav-welcome">
			    <span class="welcome-message">�Bienvenido, <%= nombreCliente %>!</span>
			</div>
			
			<!-- Modificaci�n para que el logout est� a la derecha -->
			<a href="Logout.jsp" class="logout">
			    <img src="img/icoLogout.png" alt="�cono de logout" />
			</a>

            <% 
                } else { 
            %>
                <!-- Navbar si el usuario no esta logueado -->
                             
                <div class="nav-welcome">
                    <span class="welcome-message">�Bienvenido!</span>
                </div>
            <% 
                }
            %>
        </div>

        <span class="time" id="time"></span>
    </div>

    <script src="js/scripts.js"></script>
</body>
</html>
