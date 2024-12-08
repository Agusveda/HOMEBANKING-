<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style>
body {
    margin: 0;
    padding: 0;
    font-family: Arial, sans-serif;
    background: linear-gradient(rgba(0, 0, 0, .7), rgba(0, 0, 0, .7)), url("https://martinbrainon.com/inicio/wp-content/uploads/2018/01/lead-nuclear-power-human-error-homer-simpson-1-600x398.jpg");
    background-size: cover;
    background-color: #f5f0f0b6; 
    color: #721c24; 
    min-height: 100vh; 
    position: relative; 
}

#wrapper {
    min-height: 100vh; 
    display: flex;
    flex-direction: column;
}

.error-container {
    text-align: center;
    padding: 50px;
    margin: auto;
    max-width: 600px;
    background-color: #f8d7da;
    border-radius: 10px;
    margin-top: 50px; 
}

.button-back {
    display: inline-block;
    padding: 10px 20px;
    font-size: 16px;
    color: white;
    background: linear-gradient(90deg, #ff5722, #ff8a00);
    text-decoration: none;
    border: none;
    border-radius: 5px;
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s ease, box-shadow 0.2s ease;
    cursor: pointer;
}

.button-back:hover {
    transform: scale(1.05);
    box-shadow: 0px 6px 10px rgba(0, 0, 0, 0.2);
    background: linear-gradient(90deg, #ff8a00, #ff5722);
}

.button-back:active {
    transform: scale(0.95);
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
}

.button-back:focus {
    outline: none;
    box-shadow: 0px 0px 0px 4px rgba(255, 87, 34, 0.3);
}
</style>

<title>Insert title here</title>
</head>
<body>
    <jsp:include page="Navbar.jsp" />
    
    <%
    	String mensaje = "isma";
    	if (session.getAttribute("Error") != null)
    	{
    		mensaje = (String) session.getAttribute("Error");
    	}
    	session.removeAttribute("Error");
    %>

    
<div class="error-container">
    <h1>Error</h1>
    <p id="lblErrorMessage">Lo sentimos, ha ocurrido un error. Por favor, intenta nuevamente más tarde.</p>
    <p id="lblmenssaje"> <% if(mensaje != "isma")
    { %>
    	mensaje
    
    <%}
    	
    	%></p>
    <p><a href="Login.jsp" class="button-back">Volver a la página principal</a></p>
</div>
</body>
</html>