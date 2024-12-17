package Servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Entidades.Movimiento;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("monto") != null && request.getParameter("cuotas") != null && request.getParameter("cuenta") != null) {
            procesarSolicitudPrestamo(request, response);
        } else if (request.getParameter("idPrestamo") != null && request.getParameter("confirmacion") != null) {
            procesarAprobacionPrestamo(request, response);
        } else if (request.getParameter("cuotaId") != null && request.getParameter("cuentaId") != null && request.getParameter("montoPago") != null) {
            procesarPagoCuota(request, response);
        } else if (request.getParameter("idCliente") != null) {
            procesarPréstamosEnEspera(request, response);
        }
    }

    // 1. Solicitar Prestamo
    private void procesarSolicitudPrestamo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idCliente = (Integer) request.getSession().getAttribute("IdCliente");

        // Validar que el usuario esté logueado
        if (idCliente != null) {
            try {
                // Obtener los parámetros del formulario
                float importeCliente = Float.parseFloat(request.getParameter("monto"));
                int cantCuo = Integer.parseInt(request.getParameter("cuotas"));
                int idCuenta = Integer.parseInt(request.getParameter("cuenta"));

                // Validación del monto mínimo
                if (importeCliente < 1000) {
                    request.setAttribute("mensajeError", "Monto mínimo de préstamo es $1.000");
                    forwardToPrestamoPage(request, response);
                    return;
                }
                
                // Validación del monto mínimo
                if (importeCliente > 1000000) {
                    request.setAttribute("mensajeError", "Monto maximo por transaccion es $1.000.000");
                    forwardToPrestamoPage(request, response);
                    return;
                }
                
                // Crear la fecha de alta con la fecha actual
                java.sql.Date fechaAlta = new java.sql.Date(System.currentTimeMillis());

                // Crear objeto Prestamo con los datos obtenidos
                Prestamo prestamo = new Prestamo();
                prestamo.setIdCliente(idCliente);
                prestamo.setIdCuenta(idCuenta);
                prestamo.setImporteCliente(importeCliente);
                prestamo.setFechaAlta(fechaAlta);
                prestamo.setCantCuo(cantCuo);

                // Intentar insertar el préstamo
                boolean exito = prestamoNegocio.solicitarPrestamo(prestamo);

                // Responder al usuario
                if (exito) {
                    request.setAttribute("mensaje", "Pedido de préstamo confirmado.");
                } else {
                    request.setAttribute("mensajeError", "No se pudo procesar la solicitud de préstamo. Intente nuevamente.");
                }

                // Redirigir a la página de préstamo con el mensaje correspondiente
                forwardToPrestamoPage(request, response);

            } catch (NumberFormatException e) {
                request.setAttribute("mensajeError", "Error en los datos ingresados. Asegúrese de que todos los campos sean correctos.");
                forwardToPrestamoPage(request, response);
            }
        } else {
            // Si el cliente no está logueado, redirigir al login
            response.sendRedirect("Login.jsp");
        }
    }

    // Método auxiliar para redirigir a la página de préstamo
    private void forwardToPrestamoPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("prestamoCliente.jsp");
        dispatcher.forward(request, response);
    }

    private void procesarAprobacionPrestamo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Obtención de parámetros
            int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
            int confirmacion = Integer.parseInt(request.getParameter("confirmacion"));
            float monto = Float.parseFloat(request.getParameter("monto"));
            int idcuenta = Integer.parseInt(request.getParameter("cuenta"));

            System.out.println("idPrestamo: " + idPrestamo);
            System.out.println("confirmacion: " + confirmacion);
            System.out.println("monto: " + monto);
            System.out.println("cuenta: " + idcuenta);

            boolean exito = false;
            if (confirmacion == 1) { // Aprobación del préstamo
                exito = prestamoNegocio.confirmarPrestamoConMovimiento(idPrestamo, idcuenta);
            } else if (confirmacion == 2) { // Denegación del préstamo
                exito = prestamoNegocio.denegarPrestamo(idPrestamo);
            }

            // Manejo de la respuesta
            if (exito) {
                response.sendRedirect("SolicitudesPrestamo.jsp?mensaje=Operación realizada correctamente.");
            } else {
                response.sendRedirect("SolicitudesPrestamo.jsp?mensaje=Error al procesar la solicitud.");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocurrió un error al procesar la solicitud.");
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
            String idClienteStr = request.getParameter("idCliente");
            if (idClienteStr == null || idClienteStr.isEmpty()) {
                throw new NumberFormatException("El parámetro idCliente es obligatorio.");
            }

            // Parsear idCliente
            int idCliente = Integer.parseInt(idClienteStr);
            System.out.println("ID Cliente recibido: " + idCliente);  // Mensaje de depuración

            // Obtener los préstamos en espera utilizando la lógica del negocio
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
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirigir a página de error general
        }
        
    }
}
