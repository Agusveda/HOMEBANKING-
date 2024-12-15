package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.Prestamo;
import daoImp.MovimientoDaoImp;
import negocio.MovimientoNegocio;
import negocio.PrestamoNegocio;
import negocioImpl.MovimientoNegocioImpl;
import negocioImpl.PrestamoNegocioImp;

@WebServlet("/ServletPrestamo")
public class ServletPrestamo extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MovimientoNegocio movimientoNegocio;
    private PrestamoNegocioImp prestamoNegocio;

    public ServletPrestamo() {
        super();
        // Instancia de la capa de negocio
        this.movimientoNegocio = new MovimientoNegocioImpl();
        this.prestamoNegocio = new PrestamoNegocioImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Aquí podrías agregar lógica para manejar peticiones GET si es necesario.
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("monto") != null && request.getParameter("cuotas") != null && request.getParameter("cuenta") != null) {
            procesarSolicitudPrestamo(request, response);
        } else if (request.getParameter("idPrestamo") != null && request.getParameter("confirmacion") != null) {
            procesarAprobacionPrestamo(request, response);
        } else if (request.getParameter("cuotaId") != null && request.getParameter("cuentaId") != null && request.getParameter("montoPago") != null) {
            procesarPagoCuota(request, response);
        }
    }

    private void procesarSolicitudPrestamo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idCliente = (Integer) request.getSession().getAttribute("IdCliente");

        if (idCliente != null) {
            try {
                float importeCliente = Float.parseFloat(request.getParameter("monto"));
                int cantCuo = Integer.parseInt(request.getParameter("cuotas"));
                int idCuenta = Integer.parseInt(request.getParameter("cuenta"));

                if (importeCliente < 1000) {
                    request.setAttribute("mensajeError", "Monto mínimo de préstamo es $1000.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("prestamoCliente.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                float interes = (cantCuo <= 6) ? 0 : (cantCuo <= 12) ? 0.05f : 0.10f;
                float importeConInteres = importeCliente * (1 + interes);
                float impxmes = importeConInteres / cantCuo;

                java.sql.Date fechaAlta = new java.sql.Date(System.currentTimeMillis());

                Prestamo prestamo = new Prestamo();
                prestamo.setIdCliente(idCliente);
                prestamo.setIdCuenta(idCuenta);
                prestamo.setImporteCliente(importeCliente);
                prestamo.setFechaAlta(fechaAlta);
                prestamo.setPlazoPago(cantCuo);
                prestamo.setImpxmes(impxmes);
                prestamo.setCantCuo(cantCuo);
                prestamo.setconfimacion(false);

               // boolean exito = movimientoNegocio.insertarPrestamo(prestamo);
                boolean exito = prestamoNegocio.insertarPrestamo(prestamo);
                

                if (exito) {
                    request.setAttribute("mensaje", "Pedido de préstamo confirmado.");
                } else {
                    request.setAttribute("mensajeError", "No se pudo procesar la solicitud de préstamo. Intente nuevamente.");
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("prestamoCliente.jsp");
                dispatcher.forward(request, response);

            } catch (NumberFormatException e) {
                request.setAttribute("mensajeError", "Error en los datos ingresados. Asegúrese de que todos los campos sean correctos.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("prestamoCliente.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
    
    

    private void procesarAprobacionPrestamo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
            int confirmacion = Integer.parseInt(request.getParameter("confirmacion"));
            float monto = Float.parseFloat(request.getParameter("monto"));
            int idcuenta = Integer.parseInt(request.getParameter("cuenta"));

            System.out.println("idPrestamo: " + request.getParameter("idPrestamo"));
            System.out.println("confirmacion: " + request.getParameter("confirmacion"));
            System.out.println("monto: " + request.getParameter("monto"));
            System.out.println("cuenta: " + request.getParameter("cuenta"));

            //MovimientoDaoImp movimientoDao = new MovimientoDaoImp();
            //boolean exito = movimientoDao.actualizarConfirmacionPrestamo(idPrestamo, confirmacion);
            String confirmacionStr = request.getParameter("confirmacion");
            System.out.println("Confirmación recibida en el servlet: " + confirmacionStr);
            boolean exito = prestamoNegocio.actualizarConfirmacionPrestamo(idPrestamo, confirmacion);
            if (exito && confirmacion == 1) {
                boolean exito2 = prestamoNegocio.CargarPrestamoEnCuenta(idcuenta, monto);

             
                if (exito2) {
                    response.sendRedirect("SolicitudesPrestamo.jsp");
                    return; 
                }
            }
            else if (exito && confirmacion == 2) {
                
                response.sendRedirect("SolicitudesPrestamo.jsp");
                return;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void procesarPagoCuota(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int cuotaId = Integer.parseInt(request.getParameter("cuotaId"));
            int cuentaId = Integer.parseInt(request.getParameter("cuentaId"));
            float montoPago = Float.parseFloat(request.getParameter("montoPago"));

            boolean exito = prestamoNegocio.realizarPagoCuota(cuotaId, cuentaId, montoPago);

            if (exito) {
                response.sendRedirect("pagoExitoso.jsp");
            } else {
                response.sendRedirect("errorPago.jsp");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("errorPago.jsp");
        }
    }
    
    
    private void procesarPréstamosEnEspera(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	 try {
    		 String idClienteStr = request.getParameter("idClien");
    		    if (idClienteStr == null || idClienteStr.isEmpty()) {
    		        throw new NumberFormatException("El parámetro idCliente es obligatorio.");
    		    }

    		    // Parsear idCliente
    		    int idCliente = Integer.parseInt(idClienteStr);
    		    System.out.println("ID Cliente recibido: " + idCliente);  // Mensaje de depuración

    		    // Obtener los préstamos en espera utilizando la lógica del negocio
    		    PrestamoNegocio prestamoNegocio = new PrestamoNegocioImp();
    		    List<Prestamo> prestamosEnEspera = prestamoNegocio.obtenerPrestamosEnEspera(idCliente);

    		    // Comprobar si se encontraron préstamos en espera
    		    if (prestamosEnEspera == null || prestamosEnEspera.isEmpty()) {
    		        System.out.println("No se encontraron préstamos en espera para el cliente con ID: " + idCliente);
    		        // Puedes agregar una notificación para el usuario si lo deseas
    		        request.setAttribute("mensaje", "No tienes préstamos en espera.");
    		    } else {
    		        System.out.println("Préstamos en espera encontrados: " + prestamosEnEspera.size());  // Mostrar el tamaño de la lista
    		    }

    		    // Pasar los préstamos a la vista (JSP)
    		    request.setAttribute("prestamosEnEspera", prestamosEnEspera);
    		    RequestDispatcher dispatcher = request.getRequestDispatcher("ProcesoDePrestamo.jsp");
    		    dispatcher.forward(request, response);

    		} catch (NumberFormatException e) {
    		    // Manejo de error en caso de número inválido o parámetro faltante
    		    e.printStackTrace();
    		    request.setAttribute("error", "Error al procesar el ID de cliente: " + e.getMessage());
    		    response.sendRedirect("error.jsp"); // Redirigir a página de error

    		} catch (Exception e) {
    		    // Capturar cualquier otra excepción inesperada
    		    e.printStackTrace();
    		    request.setAttribute("error", "Ocurrió un error al obtener los préstamos en espera.");
    		    response.sendRedirect("error.jsp");
    		}
    }
}
