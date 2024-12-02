package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.Prestamo;
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
		        boolean confirmacion = false; 

		        System.out.println("Monto solicitado: " + monto);
		        System.out.println("Cuotas seleccionadas: " + cuotas);
		        System.out.println("Cuenta de depósito: " + cuenta);

		        try {
		            
		            float importeCliente = Float.parseFloat(monto);
		            int cantCuo = Integer.parseInt(cuotas);
		            int plazoPago = 12; 
		            float impxmes = importeCliente / cantCuo;

		           
		            java.sql.Date fechaAlta = new java.sql.Date(System.currentTimeMillis());

		       
		            Prestamo prestamo = new Prestamo();
		            prestamo.setIdCliente(idCliente);
		            prestamo.setImporteCliente(importeCliente);
		            prestamo.setFechaAlta(fechaAlta);
		            prestamo.setPlazoPago(plazoPago);
		            prestamo.setImpxmes(impxmes);
		            prestamo.setCantCuo(cantCuo);
		            prestamo.setconfimacion(confirmacion);

		         
		            MovimientoDaoImp prestamoDao = new MovimientoDaoImp();
		            boolean exito = prestamoDao.insertarPrestamo(prestamo);

		            if (exito) {
		                System.out.println("Préstamo registrado exitosamente.");
		                response.sendRedirect("PrestamoCliente.jsp");
		            } else {
		                System.out.println("Error al registrar el préstamo.");
		                response.sendRedirect("error.jsp");
		            }
		        } catch (NumberFormatException e) {
		            System.out.println("Error al convertir parámetros: " + e.getMessage());
		            response.sendRedirect("error.jsp");
		        }
		    } else {
		        System.out.println("Cliente no autenticado.");
		        response.sendRedirect("login.jsp");
		    }

}
}
