package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTransferencia() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("btnAceptar") != null)
		{
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

			
	    	    
	    	    boolean insertado = movimientoNegocio.insertar(movimiento);
	    	    

	    	    String mensaje = insertado ? "Transferencia enviada exitosamente." : "Hubo un error al crear la cuenta.";
	    	    request.setAttribute("mensaje", mensaje);

	    	    RequestDispatcher dispatcher = request.getRequestDispatcher("/Trasferencias.jsp");
	    	    dispatcher.forward(request, response);
	    	    return; 
			
		}
		doGet(request, response);
	}

}
