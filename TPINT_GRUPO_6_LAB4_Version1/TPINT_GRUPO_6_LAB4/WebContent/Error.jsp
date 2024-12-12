<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link rel="stylesheet" type="text/css" href="css/Ventana_Error.css">

<title>Insert title here</title>
</head>
<body>
    <jsp:include page="Navbar.jsp" />
    
    <%
    	String mensaje = "";
    	if (session.getAttribute("Error") != null)
    	{
    		mensaje = (String) session.getAttribute("Error");
    	}
    	session.removeAttribute("Error");
    %>

    
<div class="error-container">
    <h1>Error</h1>
    <p id="lblErrorMessage">Lo sentimos, ha ocurrido un error. Por favor, intenta nuevamente más tarde.</p>
    <p id="lblmenssaje"> Usuario o contraseña incorrectos </p>
    <p><a href="Login.jsp" class="button-back">Volver a la página principal</a></p>
</div>
</body>
</html>

