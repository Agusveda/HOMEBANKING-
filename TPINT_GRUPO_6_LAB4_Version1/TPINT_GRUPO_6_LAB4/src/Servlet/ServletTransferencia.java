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
        
   
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Transferencias.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("btnAceptar") != null) {
            // Declaración de objetos
            Movimiento movimiento = new Movimiento();
            MovimientoNegocioImpl movimientoNegocio = new MovimientoNegocioImpl();
            CuentaNegocioImpl cuentaNegocio = new CuentaNegocioImpl();
            
            // Validaciones
            try {
                // Validación para evitar transferir a la misma cuenta (por el CBU)
                String idCuentaStrCBU = request.getSession().getAttribute("idCuenta").toString();
                int idCuentaOrigen = Integer.parseInt(idCuentaStrCBU);
                Cuenta cuentaOrigen = cuentaNegocio.obtenerCuentaPorId(idCuentaOrigen);

                if (cuentaOrigen.getCbu() == Integer.parseInt(request.getParameter("txtCbuDestino"))) {
                    request.setAttribute("mensajeError", "No puede transferirse a la misma cuenta.");
                    request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
                    return;
                }

                // Validación para evitar transferir montos menores o iguales a 0
                float importe = Float.parseFloat(request.getParameter("txtImporte"));
                if (importe <= 0) {
                    request.setAttribute("mensajeError", "El importe debe ser mayor a 0.");
                    request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
                    return;
                }

                // Validación para evitar transferir montos mayores al saldo disponible
                float saldoDisponible = Float.parseFloat(request.getParameter("txtSaldo"));
                if (importe > saldoDisponible) {
                    request.setAttribute("mensajeError", "El importe es mayor a su saldo disponible.");
                    request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
                    return;
                }

                // Validación para comprobar que el CBU destino existe
                int cbuDestino = Integer.parseInt(request.getParameter("txtCbuDestino"));
                if (!movimientoNegocio.ExisteCBU(cbuDestino)) {
                    request.setAttribute("mensajeError", "El CBU destino no existe.");
                    request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
                    return;
                }

                // Obtener ID de la cuenta destino
                int idCuentaDestino = movimientoNegocio.ObtenerIdCuentaPorCBU(cbuDestino);

                // Configuración del movimiento
                movimiento.setImporte(importe);
                movimiento.setDetalle(request.getParameter("txtDetalle"));
                movimiento.setIdCuenta(idCuentaDestino); // Cuenta destino

                // Llamada al método de negocio para realizar la transferencia
                boolean transferenciaExitosa = movimientoNegocio.insertarMovimientosTransferencia(movimiento, idCuentaOrigen);
                
                // Manejo de la respuesta
                if (transferenciaExitosa) {
                    request.setAttribute("mensaje", "Transferencia realizada exitosamente.");
                    request.getSession().removeAttribute("idCuenta");
                } else {
                    request.setAttribute("mensajeError", "Hubo un error al realizar la transferencia.");
                    request.getSession().removeAttribute("idCuenta");
                }
                request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("mensajeError", "Datos inválidos. Verifique los campos e intente nuevamente.");
                request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("mensajeError", "Ocurrió un error inesperado. Intente nuevamente.");
                request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
            }
        }
        doGet(request, response);
    }
}