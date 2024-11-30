package Servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entidades.Cuenta;
import Entidades.Movimiento;
import negocio.MovimientoNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.MovimientoNegocioImpl;
import dao.MovimientoDao;
import daoImp.MovimientoDaoImp;

/**
 * Servlet implementation class ServletTransferencia
 */
@WebServlet("/ServletTransferencia")
public class ServletTransferencia extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public ServletTransferencia() {
        super();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idCuentaStr = request.getParameter("idCuenta");
        
        
        System.out.println("Parámetro idCuenta recibido: " + idCuentaStr);
        
        if (idCuentaStr != null && request.getParameter("CargarSaldo") != null) {
            int idCuenta = Integer.parseInt(idCuentaStr);

           
            request.getSession().setAttribute("idCuenta", idCuenta);
            System.out.println("idCuenta almacenado en sesión: " + idCuenta);

            MovimientoNegocioImpl movimientoNegocio = new MovimientoNegocioImpl();
            CuentaNegocioImpl cuentaNegocio = new CuentaNegocioImpl();
            Cuenta cuenta = cuentaNegocio.obtenerCuentaPorId(idCuenta); // Implementa este método
            
            if (cuenta != null) {
                float saldoActual = cuenta.getSaldo();
                request.setAttribute("saldoActual", saldoActual);

                System.out.println("Saldo actual obtenido: " + saldoActual);
            } else {
                System.out.println("No se encontró ninguna cuenta con id: " + idCuenta);
            }
        } else {
            System.out.println("Parámetro CargarSaldo o idCuentaStr es nulo.");
        }
        
   
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Trasferencias.jsp");
        dispatcher.forward(request, response);
    }




	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("btnAceptar") != null)
		{
			String idCli = request.getSession().getAttribute("IdCliente").toString();	
            			
			//declaracion de objetos
			Movimiento movimiento = new Movimiento();
			MovimientoNegocioImpl movimientoNegocio = new MovimientoNegocioImpl();
	    	    
			
			//  Traigo al cuenta de destino (donde envio el importe/transferencia)
		
			int CBUDestino = Integer.parseInt(request.getParameter("txtCbuDestino"));
			int CuentaDestino = movimientoNegocio.ObtenerIdCuentaPorCBU(CBUDestino);
			
			
			
			// Seteo los parametros para el insert de Movimiento.
			
			//movimiento.setTipoMovimiento(Integer.parseInt(request.getParameter("")));
			movimiento.setImporte(Float.parseFloat(request.getParameter("txtImporte")));
			movimiento.setDetalle(request.getParameter("txtDetalle"));
			movimiento.setIdCuenta(CuentaDestino);
				
			
			int idCue = movimientoNegocio.ObtenerIdCuentaPorIdCliente(Integer.parseInt(idCli));
	    	    
	   	    boolean insertado = movimientoNegocio.insertar(movimiento, idCue);
	    	    
	   	    String mensaje = insertado ? "Transferencia enviada exitosamente." : "Hubo un error al crear la cuenta.";
    	    request.setAttribute("mensaje", mensaje);

	   	    RequestDispatcher dispatcher = request.getRequestDispatcher("/Trasferencias.jsp");
	   	    dispatcher.forward(request, response);
	   	    return; 
			
		}
		doGet(request, response);
	}

}