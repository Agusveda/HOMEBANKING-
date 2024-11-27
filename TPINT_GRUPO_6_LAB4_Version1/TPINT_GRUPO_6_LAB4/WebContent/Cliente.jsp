<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="css/Cliente.css">

<title>VISTA DE CLIENTE</title>
</head>

<body>

<jsp:include page="Navbar.jsp"/>

<div class="Encabezado">
<span class="Saludo"> Bienvenido.. (cliente) </span> 
<input type = "submit" class="btn_movimientos" name="btninfo" value="perfil" onclick="window.location.href='InformacionPersonal.jsp'; return false;">
</div>


<div class="Estado">
   Estado de tu cuenta
</div>
<div class="Cuentas">
    <div class="Cuenta1">
        <div class="header">
            <h3>Cuentas</h3>
            <p>Saldos totales</p>
        </div>
        <div class="valores">
            <div class="valor">
                <span>$</span>
                <span >******</span>
            </div>
            <div class="valor2">
                <span>U$S</span>
                <span >*******</span>
            </div>
        
        </div>
        <div class="acciones">
             <div >
                
            <a href="VerMovimientos.jsp" > 
            <input   type="submit" class="btn_movimientos" name="btnVerMovimientos" value="Ver Movimientos">
            </a>    
              
            </div>
            <div >
		<input type="submit" class="btn_cbu" name="btnVercbu" value="CBU / ALIAS">
            </div>
        </div>
    </div>


<div class="Cuenta2">
<div class="header2">
            <h3>(VISA) Terminada en ****</h3>
            <p>Cierra el *** | vence el **** </p>
        </div>
        <div class="consumo">
        <div>
        	<span> Ultimos Consumos: </span>
        </div>
        <br>
            <div >
                <span>$</span>
                <span class="dots">******</span>
            </div>
            <div class="balance">
                <span>U$S</span>
                <span class="dots">*******</span>
            </div>
        </div>
         <div >
                
            <a href="VerMovimientos.jsp" > 
            <input   type="submit" class="btn_movimientos" name="btnVerMovimientos" value="Ver Movimientos">
            </a>    
              
            </div>
        
</div>
<div class="Cuenta3">
<div class="header2">
            <h3>(American) Terminada en ****</h3>
            <p>Cierra el *** | vence el **** </p>
        </div>
        <div class="consumo">
        <div>
        	<span> Ultimos Consumos: </span>
        </div>
        <br>
            <div >
                <span>$</span>
                <span class="dots">******</span>
            </div>
            <div class="balance">
                <span>U$S</span>
                <span class="dots">*******</span>
            </div>
        </div>
         <div >
                
            <a href="VerMovimientos.jsp" > 
            <input   type="submit" class="btn_movimientos" name="btnVerMovimientos" value="Ver Movimientos">
            </a>    
              
            </div>
        
</div>
</div>

<br>
<BR>
<div class="accesoRapido">
Accesos Rapidos
</div>


	<div class="Opciones">
	
	<a href="Trasferencias.jsp" > 
	<input type="submit" class="BtnOpciones"  name="btnTransferencias" value="Transferencias"/>
	</a>
	
	<a href="PagoDePrestamo.jsp">
	<input type="submit" class="BtnOpciones"   name="btnPagoServicios" value="Pago de Servicios"/>
	</a>
	
	<a href="prestamoCliente.jsp" > 
	<input type="submit" class="BtnOpciones"  name="btnPrestamos" value="Prestamos" />
	</a>
	
	</div>

 <jsp:include page="Footer.jsp"/>

</body>

</html>