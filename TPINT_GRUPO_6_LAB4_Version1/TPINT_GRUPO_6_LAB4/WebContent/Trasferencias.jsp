<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>

	<jsp:include page="css/Transferencias.css"></jsp:include>
	
    #inputSaldo 
    {
    	border: none; 
    	outline: none; 
    }
</style>

</head>
<body>
<jsp:include page="Navbar.jsp"/>
<div class="encabezado">
	
<h1>Cuentas</h1>

</div>



<form method="post" action="ServletTransferencia">

    <fieldset>
      <legend>Transferencias</legend>
      
      
    
   	
      <p>
        <label for="CbuDestino">Igresar CBU del destinatario </label>
        <input id="CbuDestino" type="number" placeholder="CbuDestino" required name="txtCbuDestino">
      </p>
      
      <p>
        <label for="Importe">Importe</label>
        <input id="Importe" type="number" placeholder="Ingrese su Importe" required name="txtImporte">
      </p>
       <p>
        <label for="Importe">Detalle</label>
        <input id="Importe" type="text" placeholder="Ingrese El detalle " required name="txtDetalle">
      </p>
      
      <p>
        <label for="Saldo">Saldo Actual</label>
        <input id="inputSaldo" readonly="true" type="number" required name="txtSaldo" value="40.000">
      </p>
      
      <p>
        <input id="btnAceptar" type="submit" value="Transferir" required name="btnAceptar">
      </p>
      
     
    </fieldset>
</form>
      <a href="Cliente.jsp">
    
    <input class="btnAtras" type="submit" value="Atras" name="btnAtras" >
    </a>
 <jsp:include page="Footer.jsp"/>

</body>

</html>