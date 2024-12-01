package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImp.MovimientoDaoImp;

/**
 * Servlet implementation class ServletPrestamo
 */
@WebServlet("/ServletPrestamo")
public class ServletPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPrestamo() {
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
		
		 Integer idCliente = (Integer) request.getSession().getAttribute("IdCliente");

	        if (idCliente != null) {
	            System.out.println("Cliente autenticado. ID del cliente: " + idCliente);

	            String monto = request.getParameter("monto");
	            String cuotas = request.getParameter("cuotas");
	            String cuenta = request.getParameter("cuenta");

	            System.out.println("Monto solicitado: " + monto);
	            System.out.println("Cuotas seleccionadas: " + cuotas);
	            System.out.println("Cuenta de depósito: " + cuenta);

	         
	            float importePedido = Float.parseFloat(monto);
	            int cantidadCuotas = Integer.parseInt(cuotas);
	            int plazoPago = 12;  
	            float importePagarXmes = importePedido / cantidadCuotas;  

	            
	            MovimientoDaoImp prestamoDao = new MovimientoDaoImp();
	            boolean exito = prestamoDao.insertarPrestamo(idCliente, importePedido, plazoPago, importePagarXmes, cantidadCuotas);

	            if (exito) {
	                System.out.println("Préstamo registrado exitosamente.");
	                response.sendRedirect("PrestamoCliente.jsp");  
	            } else {
	                System.out.println("Error al registrar el préstamo.");
	                response.sendRedirect("error.jsp"); 
	            }
	        } else {
	            System.out.println("No se encontró sesión activa para el cliente.");
	            response.sendRedirect("prestamoCliente.jsp");
	        }
	        //PRUEVA
	}

}
