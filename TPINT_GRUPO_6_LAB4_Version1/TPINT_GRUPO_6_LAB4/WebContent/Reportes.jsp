<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/Navbar.css">
    <link rel="stylesheet" type="text/css" href="css/Footer.css">
    
    <style>
        /* Contenedor de los botones */
        .botones-contenedor {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 20px;
        }

        /* Estilo general para todos los botones */
        .botones-contenedor input[type="submit"] {
            font-size: 24px; 
            padding: 15px 40px; 
            border-radius: 12px; 
            border: none;
            cursor: pointer;
            transition: transform 0.3s, background-color 0.3s;
            width: 300px;
            text-align: center; 
        }

        /* Botón Movimientos */
        .BtnMovimientos {
            background-color: #007bff; 
            color: white;
        }

        .BtnMovimientos:hover {
            background-color: #0056b3;
            transform: scale(1.05); 
        }

        /* Botón Transferencia */
        .BtnTransferencia {
            background-color: #28a745; 
            color: white;
        }

        .BtnTransferencia:hover {
            background-color: #1e7e34;
            transform: scale(1.05);
        }

        /* Botón Cuentas */
        .BtnCuentas {
            background-color: #ffc107;
            color: black;
        }

        .BtnCuentas:hover {
            background-color: #e0a800;
            transform: scale(1.05);
        }
    </style>
<title>Reportes</title>
</head>
<body>

<jsp:include page="Navbar.jsp"/>

<div class="encabezado">
    <h1>Seleccione el reporte que desee</h1>
</div>

    <form action="ServletReportes" method="get">
		
	<div class="botones-contenedor">
    	<input type="submit" class="BtnMovimientos" name="btnMovimientos" value="Movimientos" />
	</div>
	<br>
	<div class="botones-contenedor">
    	<input type="submit" class="BtnTransferencia" name="btnTransferencia" value="Transferencia" />
	</div>
	<div class="botones-contenedor">
    	<input type="submit" class="BtnCuentas" name="btnCuentas" value="Cuentas" />
	</div>
		
	
    </form>


 <jsp:include page="Footer.jsp"/>


</body>
</html>