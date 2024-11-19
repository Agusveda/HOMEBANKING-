<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>

	<jsp:include page="css/Transferencias.css"></jsp:include>
</style>

</head>
<body>
<jsp:include page="Navbar.jsp"/>
<div class="encabezado">
	
<h1>Cuentas</h1>

</div>



<form method="get">

    <fieldset>
      <legend>Transferencias</legend>
      
      <p>
              Cuenta: 
		<select name="Cuentas" id="Cuentas">
            <option value="1">Cuenta 1(Debito)</option>
            <option value="2">Cuenta 2(Credito)</option>
            <option value="3">Cuenta 3(opcional)</option>
        </select> <br><br>
      </p>
   
   	
      <p>
        <label for="CbuDestino">Igresar CBU del destinatario </label>
        <input id="CbuDestino" type="number" placeholder="CbuDestino" required name="txtCbuDestino">
      </p>
            <p>
        <label for="Importe">Importe</label>
        <input id="Importe" type="number" placeholder="Ingrese su Importe" required name="txtImporte">
      </p>
            <p>
        <label for="Saldo">Saldo Actual</label>
        <span> $ 40.000</span>
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