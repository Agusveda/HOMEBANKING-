package Servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Entidades.Cuenta;
import Entidades.Movimiento;
import dao.CuentaDao;
import daoImp.CuentaDaoImpl;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.MovimientoNegocioImpl;

@WebServlet("/ServletCuenta")
public class ServletCuenta extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	/// ALTA DE CUENTA
    	if (request.getParameter("btnAltaCuenta") != null) 
    	{
    		Movimiento movi = new Movimiento();
    		MovimientoNegocioImpl moviN = new MovimientoNegocioImpl();
    	    Cuenta cuenta = new Cuenta();
    	    CuentaNegocioImpl cuentaN = new CuentaNegocioImpl();
    	    int idCuenta = 0;
    	    
    	    /// Movimiento de Alta cuenta
    	    movi.setTipoMovimiento(1);
    	    movi.setImporte(10000);
    	    movi.setDetalle("alta de cuenta");
    	    idCuenta = cuentaN.ObtenerProximoIdCuenta();
    	    
    	    cuenta.setIdCliente(Integer.parseInt(request.getParameter("txtIdCliente")));
    	    cuenta.setTipoCuenta(Integer.parseInt(request.getParameter("txtTipoCuenta")));
    	    cuenta.setActivo(true);

    	    CuentaNegocioImpl cuentaNegocio = new CuentaNegocioImpl();
    	    
    	    boolean insertado = cuentaNegocio.insertCuenta(cuenta);
    	    moviN.insertarAltaCuenta(movi, idCuenta);
    	    String mensaje = "";
    	    
    	    if (insertado)
    	    {
    	    	mensaje = "Cuenta creada exitosamente.";
    	    	request.setAttribute("mensaje", mensaje);    	    	
    	    }
    	    else
    	    {
    	    	mensaje = "Error al crear cuenta mano";
    	    	
    	    	if (!cuentaNegocio.ExisteId(cuenta.getIdCliente()))
    	    	{
    	    		mensaje = "Ingrese un id de cliente valido porfavor.";
    	    	}
    	    	request.setAttribute("mensajeError", mensaje);    	    	
    	    }

    	    RequestDispatcher dispatcher = request.getRequestDispatcher("/AltaCuentas.jsp");
    	    dispatcher.forward(request, response);
    	    return; 
    	} 

    	if (request.getParameter("btnFiltrar") != null) {
    	    int DNI = Integer.parseInt(request.getParameter("txtBuscar"));
    	    
    	    //CuentaDaoImpl cuentadao = new CuentaDaoImpl();
    	    CuentaNegocioImpl cuentaNegocio = new CuentaNegocioImpl();
    	    ArrayList<Cuenta> lista = cuentaNegocio.ListarCuenta(DNI);
    	    request.setAttribute("listaCuentaFiltrada", lista);
    	    
    	    RequestDispatcher rd = request.getRequestDispatcher("/ListarCuenta.jsp");
    	    rd.forward(request, response);
    	    return; // Detener ejecución aquí
    	}
    	if (request.getParameter("btnEliminar") != null) {
    		 CuentaNegocioImpl cuentaNegocio = new CuentaNegocioImpl();
    	    int id = Integer.parseInt(request.getParameter("idCuenta"));
    	    boolean eliminada = cuentaNegocio.EliminarCuenta(id);

    	    if (eliminada) {
    	        System.out.println("Cuenta eliminada exitosamente.");
    	    } else {
    	        System.out.println("Error al eliminar la cuenta.");
    	    }

    	    ArrayList<Cuenta> lista = cuentaNegocio.ListarCuenta();
    	    System.out.println("Lista de cuentas después de eliminar: " + lista.size());
    	    request.setAttribute("listaCuenta", lista);

    	    RequestDispatcher rd = request.getRequestDispatcher("/ListarCuenta.jsp");
    	    rd.forward(request, response);
    	    return;
    	}


    	if (request.getParameter("btnModificarCuenta") != null) {
    	    try {
    	        int idCuenta = Integer.parseInt(request.getParameter("txtIdCuenta"));
    	        int tipoCuenta = Integer.parseInt(request.getParameter("txtTipoCuenta"));
    	        int numeroCuenta = Integer.parseInt(request.getParameter("txtNumeroCuenta"));
    	        int cbu = Integer.parseInt(request.getParameter("txtCBU"));
    	        float saldo = Float.parseFloat(request.getParameter("txtSaldo"));
    	        
    	        // Validaciones	        
    	        if(!(tipoCuenta == 1 || tipoCuenta == 2)) {
    	        	String mensajeError = "El tipo de cuenta no puede ser distinto a 1. Corriente, 2. Ahorro.";
    	        	request.setAttribute("mensajeError", mensajeError);
    	            RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarCuenta.jsp");
    	            dispatcher.forward(request, response);
    	            return; // Detener el procesamiento
    	        }
    	        
    	        if (saldo < 0) {
    	            String mensajeError = "El saldo no puede ser negativo.";
    	            request.setAttribute("mensajeError", mensajeError);
    	            RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarCuenta.jsp");
    	            dispatcher.forward(request, response);
    	            return; // Detener el procesamiento
    	        }
    	        
    	        if (cbu < 0) {
    	            String mensajeError = "El CBU no puede ser negativo.";
    	            request.setAttribute("mensajeError", mensajeError);
    	            RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarCuenta.jsp");
    	            dispatcher.forward(request, response);
    	            return; // Detener el procesamiento
    	        }

    	        // Proceder con la modificación si cumple las validaciones
    	        Cuenta cuenta = new Cuenta();
    	        cuenta.setId(idCuenta);
    	        cuenta.setTipoCuenta(tipoCuenta);
    	        cuenta.setNumeroCuenta(numeroCuenta);
    	        cuenta.setCbu(cbu);
    	        cuenta.setSaldo(saldo);

    	        CuentaNegocioImpl cuentaNegocio = new CuentaNegocioImpl();
    	        boolean modificada = cuentaNegocio.modificarCuenta(cuenta);

    	        String mensaje = modificada ? "Cuenta modificada exitosamente." : "Error al modificar la cuenta.";
    	        request.setAttribute("mensaje", mensaje);
    	        RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuenta.jsp");
    	        dispatcher.forward(request, response);

    	    } catch (NumberFormatException e) {
    	        request.setAttribute("mensajeError", "Datos invalidos.");
    	        RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarCuenta.jsp");
    	        dispatcher.forward(request, response);
    	    }
    	}

     }
}