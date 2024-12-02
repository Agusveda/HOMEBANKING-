<%@page import="daoImp.MovimientoDaoImp"%>
<%@page import="dao.MovimientoDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Cuenta"%><%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Solicitud de Pr�stamo</title>
<link rel="stylesheet" type="text/css" href="">
</head>
<body>
<div class="navegador">
<jsp:include page="Navbar.jsp"/>
</div>

<div class="container">
    <h2>Solicitud de Pr�stamos</h2>
    
    <% 
        
        Integer idCliente = (Integer) session.getAttribute("IdCliente");
        
  
        if (idCliente != null) {
            MovimientoDao movimientoDao = new MovimientoDaoImp();
            ArrayList<Cuenta> cuentas = movimientoDao.TraeCuentasPorIdCliente(idCliente);
    %>
    
    <form method="post" action="ServletPrestamo">
        <label for="monto">Monto del Pr�stamo:</label>
        <input type="number" id="monto" name="monto" placeholder="Ingrese el monto" required>
        <br>
        
        <label for="cuotas">Cantidad de Cuotas:</label>
        <select id="cuotas" name="cuotas" required>
            <option value="">Seleccione...</option>
            <option value="6">6 cuotas</option>
            <option value="12">12 cuotas</option>
            <option value="24">24 cuotas</option>
            <option value="36">36 cuotas</option>
        </select>
        
        <label for="cuenta">Cuenta de Dep�sito:</label>
        <select id="cuenta" name="cuenta" required>
            <option value="">Seleccione...</option>
            <% 
                // Llenar din�micamente las cuentas del cliente
                if (cuentas != null && !cuentas.isEmpty()) {
                    for (Cuenta cuenta : cuentas) {
                        String tipoCuenta = cuenta.getTipoCuenta() == 1 ? "Caja de Ahorro" : "Cuenta Corriente";
            %>
                        <option value="<%= cuenta.getId() %>">
                            <%= cuenta.getNumeroCuenta() + " - " + tipoCuenta %>
                        </option>
            <% 
                    }
                } else { 
            %>
                <option value="">No hay cuentas disponibles</option>
            <% 
                }
            %>
        </select>
        
        <button type="submit" class="btn-pedir">Solicitar Pr�stamo</button>
    </form>
    
    <% 
        } else { 
    %>
        <div class="mensaje-error">Debe iniciar sesi�n para solicitar un pr�stamo.</div>
    <% 
        }
    %>
</div>

</body>
<jsp:include page="Footer.jsp"/>
</html>