<%@page import="daoImp.PrestamoDaoImp"%>
<%@page import="dao.PrestamoDao"%>
<%@page import="java.util.List"%>
<%@page import="Entidades.Prestamo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <title>Préstamos en Espera</title>
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    <link rel="stylesheet" type="text/css" href="css/ProcesoDePrestamo.css">
    
    <style>
        .BtnAtras {
            background-color: #4fc3f7; 
            color: white; 
            font-size: 16px; 
            padding: 8px 20px; 
            border: none; 
            border-radius: 5px;
            cursor: pointer; 
            transition: background-color 0.3s, transform 0.3s;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1); 
            display: inline-block; 
            margin: 10px 0; 
            text-align: center;
        }

        .BtnAtras:hover {
            background-color: #29b6f6; 
            transform: scale(1.05); 
        }

        .BtnAtras:active {
            background-color: #0288d1; 
            transform: scale(0.95); 
        }

        .container {
            margin: 20px;
        }

        .prestamo-item {
            border: 1px solid #ddd;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
        }

        .prestamo-item h3 {
            margin: 0;
        }

        .prestamo-item p {
            margin: 5px 0;
        }
    </style>

</head>
<body>
	<div class="container">
 <h1>Préstamos en Espera</h1>

      <% 
        // Obtener la lista de préstamos en espera desde la solicitud
        List<Prestamo> prestamosEnEspera = (List<Prestamo>) request.getAttribute("prestamosEnEspera");
        if (prestamosEnEspera != null && !prestamosEnEspera.isEmpty()) {
            for (Prestamo prestamo : prestamosEnEspera) {
    %>
        <div class="prestamo-item">
            <h3>Préstamo N° <%= prestamo.getId() %></h3>
            <p><strong>Monto Solicitado:</strong> <%= prestamo.getImporteCliente() %> </p>
            <p><strong>Fecha de Alta:</strong> <%= prestamo.getFechaAlta() %> </p>
            <p><strong>Importe a Pagar por Mes:</strong> <%= prestamo.getImpxmes() %> </p>
            <p><strong>Cantidad de Cuotas:</strong> <%= prestamo.getCantCuo() %> </p>
        </div>
    <% 
            }
        } else {
    %>
        <p>No tienes préstamos en espera.</p>
    <% 
        }
    %>
    
    <a href="Cliente.jsp">
        <input class="BtnAtras" type="button" value="Atrás" name="btnAtras">
    </a>
</div>

<jsp:include page="Footer.jsp"/>
</body>
</html>