<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Validar si el usuario está logueado y es admin
    String nombreCliente = (String) session.getAttribute("nombreCliente");
    Integer tipoUsuario = (Integer) session.getAttribute("tipoUsuario");

    if (nombreCliente == null || tipoUsuario == null || tipoUsuario != 1) {
        response.sendRedirect("Login.jsp");
        return; 
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Panel Administrador</title>
    <link rel="stylesheet" type="text/css" href="css/Administrador.css"> 
    <script src="css/script.js" defer></script>
</head>
<body>
<jsp:include page="Navbar.jsp"/>

<div class="encabezado">
    <h1>Panel de Administrador</h1>
</div>

<div class="contenedorPrincipal">
    <div class="parteIzq">
        <div class="menu">
            <ul>
         
                <li> 
                    <a href="#"> <span class="fa fa-users"></span> Gestión de Clientes</a> 
                    <ul>
                        <li><a href="AltaCliente.jsp" target="contenido">Agregar Cliente</a></li>
                        <li><a href="ListarCliente.jsp" target="contenido">Ver Clientes</a></li>
                    </ul>
                </li>
                <li> 
                    <a href="#"> <span class="fa fa-university"></span> Gestión de Cuentas</a>
                    <ul>
                        <li><a href="AltaCuentas.jsp" target="contenido">Agregar Cuenta</a></li>
                        <li><a href="ListarCuenta.jsp" target="contenido">Ver Cuentas</a></li>
                    </ul>
                </li>
                <li> 
                    <a href="#"> <span class="fa fa-hand-holding-usd"></span> Autorización de Préstamos</a>
                    <ul>
                        <li><a href="SolicitudesPrestamo.jsp" target="contenido">Solicitudes de Préstamo</a></li>
                        <li><a href="prestamosAprobados.jsp" target="contenido">Préstamos Aprobados</a></li>
                    </ul>
                </li>
                <li> 
                    <a href="#"> <span class="fa fa-chart-line"></span> Reportes</a>
                    <ul>
                        <li><a href="Reportes.jsp" target="contenido">Reportes</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>

    <div class="parteDer">
        <iframe id="contenido" name="contenido" src="AdminHome.jsp" width="100%" height="600px" frameborder="0"></iframe>
    </div>
</div>

<jsp:include page="Footer.jsp"/>

</body>
</html>
