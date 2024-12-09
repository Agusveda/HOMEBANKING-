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
        
        
        System.out.println("Par�metro idCuenta recibido: " + idCuentaStr);
        
        if (idCuentaStr != null && request.getParameter("CargarSaldo") != null) {
            int idCuenta = Integer.parseInt(idCuentaStr);

           
            request.getSession().setAttribute("idCuenta", idCuenta);
            System.out.println("idCuenta almacenado en sesi�n: " + idCuenta);

            MovimientoNegocioImpl movimientoNegocio = new MovimientoNegocioImpl();
            CuentaNegocioImpl cuentaNegocio = new CuentaNegocioImpl();
            Cuenta cuenta = cuentaNegocio.obtenerCuentaPorId(idCuenta); // Implementa este m�todo
            
            if (cuenta != null) {
                float saldoActual = cuenta.getSaldo();
                request.setAttribute("saldoActual", saldoActual);

                System.out.println("Saldo actual obtenido: " + saldoActual);
            } else {
                System.out.println("No se encontr� ninguna cuenta con id: " + idCuenta);
            }
        } else {
            System.out.println("Par�metro CargarSaldo o idCuentaStr es nulo.");
        }
        
   
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Transferencias.jsp");
        dispatcher.forward(request, response);
    }




	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("btnAceptar") != null)
		{
			//declaracion de objetos
			Movimiento movimiento = new Movimiento();
			MovimientoNegocioImpl movimientoNegocio = new MovimientoNegocioImpl();
			Cuenta cuen = new Cuenta();
			CuentaNegocioImpl cuentaN = new CuentaNegocioImpl();
			
			
			
			
			///VALIDACIONES
			
			/// PARA VERIFICAR QUE NO SE TRANSFIERA A LA MISMA CUENTA (POR EL CBU)
			String idCuentaStrCBU = request.getSession().getAttribute("idCuenta").toString();
			int idCueCBU = Integer.parseInt(idCuentaStrCBU);
			cuen = cuentaN.obtenerCuentaPorId(idCueCBU);
			if (cuen.getCbu() == Integer.parseInt(request.getParameter("txtCbuDestino")))
			{
                request.setAttribute("mensajeError", "No puede transferirse a esta misma cuenta.");
                request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
                return;
			}
			
			///PARA VERIFICAR QUE SE INGRESE UN MONTO MAYOR A 0 PARA TRANSFERIR
			if (Float.parseFloat(request.getParameter("txtImporte")) <= 0)
			{
                request.setAttribute("mensajeError", "El importe debe ser mayor a 0.");
                request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
                return;
			}
			
			/// PARA VERIFICAR QUE EL SALDO SEA MAYOR AL IMPORTE A TRANSFERIR
            if (Float.parseFloat(request.getParameter("txtImporte")) > Float.parseFloat(request.getParameter("txtSaldo")) ) 
            {
                request.setAttribute("mensajeError", "El importe es mayor a su sueldo.");
                request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
                return;
            }
            
            /// PARA VERIFICAR SI EXISTE EL CBU A TRANSFERIR
            if (movimientoNegocio.ExisteCBU(Integer.parseInt(request.getParameter("txtCbuDestino"))) == false)
            {
                request.setAttribute("mensajeError", "El CBU no existe.");
                request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
                return;
            }

            
            
            /// -------------------------------------------------------------------
			String idCli = request.getSession().getAttribute("IdCliente").toString();	
            			
	    	    
			
			//  Traigo al cuenta de destino (donde envio el importe/transferencia)
		
			int CBUDestino = Integer.parseInt(request.getParameter("txtCbuDestino"));
			int CuentaDestino = movimientoNegocio.ObtenerIdCuentaPorCBU(CBUDestino);
			
			
			
			// Seteo los parametros para el insert de Movimiento.
			
			//movimiento.setTipoMovimiento(Integer.parseInt(request.getParameter("")));
			movimiento.setImporte(Float.parseFloat(request.getParameter("txtImporte")));
			movimiento.setDetalle(request.getParameter("txtDetalle"));
			movimiento.setIdCuenta(CuentaDestino);
				
			
			int idCue = 0;
			String idCuentaStr = request.getSession().getAttribute("idCuenta").toString();
			idCue = Integer.parseInt(idCuentaStr);
	    	    
	   	    boolean insertado = movimientoNegocio.insertar(movimiento, idCue);
	   	    
            if (insertado) {
                request.setAttribute("mensaje", "Transferencia realizada exitosamente.");
            } else {
                request.setAttribute("mensajeError", "Hubo un error en la transferencia");
            }
            request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
            
	   	    return; 
			
		}
		doGet(request, response);
	}

}