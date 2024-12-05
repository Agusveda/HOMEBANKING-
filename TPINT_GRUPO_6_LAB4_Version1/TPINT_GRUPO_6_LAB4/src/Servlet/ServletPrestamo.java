package Servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.Prestamo;
import daoImp.MovimientoDaoImp;


@WebServlet("/ServletPrestamo")
public class ServletPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletPrestamo() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  
	        if (request.getParameter("monto") != null && request.getParameter("cuotas") != null && request.getParameter("cuenta") != null) {
	            procesarSolicitudPrestamo(request, response);
	        } 
	        else if (request.getParameter("idPrestamo") != null && request.getParameter("confirmacion") != null) {
	            procesarAprobacionPrestamo(request, response);
	    
	        }
	     
	    }

	    private void procesarSolicitudPrestamo(HttpServletRequest request, HttpServletResponse response) throws IOException {
	        Integer idCliente = (Integer) request.getSession().getAttribute("IdCliente");

	        //El cliente pide el prestamo
	        if (idCliente != null) {
	            String monto = request.getParameter("monto");
	            String cuotas = request.getParameter("cuotas");
	            String idcuenta = request.getParameter("cuenta");
	            boolean confirmacion = false; 

	            try {
	                float importeCliente = Float.parseFloat(monto);
	                int cantCuo = Integer.parseInt(cuotas);
	                int plazoPago = 12; 
	                float impxmes = importeCliente / cantCuo;

	                java.sql.Date fechaAlta = new java.sql.Date(System.currentTimeMillis());

	                Prestamo prestamo = new Prestamo();
	                prestamo.setIdCliente(idCliente);
	                prestamo.setIdCuenta(Integer.parseInt(idcuenta));
	                prestamo.setImporteCliente(importeCliente);
	                prestamo.setFechaAlta(fechaAlta);
	                prestamo.setPlazoPago(plazoPago);
	                prestamo.setImpxmes(impxmes);
	                prestamo.setCantCuo(cantCuo);
	                prestamo.setconfimacion(confirmacion);

	                MovimientoDaoImp prestamoDao = new MovimientoDaoImp();
	                boolean exito = prestamoDao.insertarPrestamo(prestamo);

	                if (exito) {
	                    response.sendRedirect("prestamoCliente.jsp"); 
	                } else {
	                    response.sendRedirect("error.jsp"); 
	                }
	            } catch (NumberFormatException e) {
	                response.sendRedirect("error.jsp"); 
	            }
	        } else {
	            response.sendRedirect("login.jsp");
	        }
	    }

	    //Solicitudes para el administrador para Aceptar o denegar
	    private void procesarAprobacionPrestamo(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	   
	        try {
	            int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
	            int confirmacion = Integer.parseInt(request.getParameter("confirmacion"));
	            float monto = Float.parseFloat( request.getParameter("monto"));
	            int idcuenta = Integer.parseInt(request.getParameter("cuenta"));
	          
	            System.out.println("idPrestamo: " + request.getParameter("idPrestamo"));
	            System.out.println("confirmacion: " + request.getParameter("confirmacion"));
	            System.out.println("monto: " + request.getParameter("monto"));
	            System.out.println("cuenta: " + request.getParameter("cuenta"));
	            
	            // actualizo en prestamo
	            MovimientoDaoImp movimientoDao = new MovimientoDaoImp();
	            boolean exito = movimientoDao.actualizarConfirmacionPrestamo(idPrestamo, confirmacion);
	            
	            if (exito && confirmacion == 1) {
	                boolean exito2 = movimientoDao.CargarPrestamoEnCuenta(idcuenta, monto);
	               
	                // Verifico si se cargó el monto correctamente
	                if (exito2) {
	                    response.sendRedirect("SolicitudesPrestamo.jsp");
	                    return; // Finaliza el método después de redirigir
	                }
	            }

	            // Si no se aprueba o hay algún error, redirijo a error.jsp
	            response.sendRedirect("error.jsp");

	        } catch (NumberFormatException e) {
	            response.sendRedirect("error.jsp");
	        }
	    }
	    
	    
	  
	}


