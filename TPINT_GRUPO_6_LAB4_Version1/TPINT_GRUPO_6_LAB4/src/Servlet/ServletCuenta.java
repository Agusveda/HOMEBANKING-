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
import dao.CuentaDao;
import daoImp.CuentaDaoImpl;
import negocioImpl.CuentaNegocioImpl;

@WebServlet("/ServletCuenta")
public class ServletCuenta extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if (request.getParameter("btnAltaCuenta") != null) {
    	    Cuenta cuenta = new Cuenta();
    	    
    	    cuenta.setIdCliente(Integer.parseInt(request.getParameter("txtIdCliente")));
    	    cuenta.setTipoCuenta(Integer.parseInt(request.getParameter("txtTipoCuenta")));
    	    cuenta.setActivo(true);

    	    //CuentaDaoImpl cuentadao = new CuentaDaoImpl();
    	    CuentaNegocioImpl cuentaNegocio = new CuentaNegocioImpl();
    	    
    	    boolean insertado = cuentaNegocio.insertCuenta(cuenta);
    	    

    	    String mensaje = insertado ? "Cuenta creada exitosamente." : "Hubo un error al crear la cuenta.";
    	    request.setAttribute("mensaje", mensaje);

    	    RequestDispatcher dispatcher = request.getRequestDispatcher("/Administrador.jsp");
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
    	    int id = Integer.parseInt(request.getParameter("idCuenta"));
    	    
    	   // CuentaDaoImpl cuentadao = new CuentaDaoImpl();
    	    CuentaNegocioImpl cuentaNegocio = new CuentaNegocioImpl();
    	    boolean eliminada = cuentaNegocio.EliminarCuenta(id);
    	    
    	    String mensaje = eliminada ? "Cuenta eliminada exitosamente." : "Hubo un error al eliminar la cuenta.";
    	    request.setAttribute("mensaje", mensaje);
    	    
    	    RequestDispatcher rd = request.getRequestDispatcher("/ListarCuenta.jsp");
    	    rd.forward(request, response);
    	    return; // Detener ejecución aquí
    	}

    	if (request.getParameter("btnModificarCuenta") != null) {
    	    int idCuenta = Integer.parseInt(request.getParameter("txtIdCuenta"));
    	    int tipoCuenta = Integer.parseInt(request.getParameter("txtTipoCuenta"));
    	    int numeroCuenta = Integer.parseInt(request.getParameter("txtNumeroCuenta"));
    	    int cbu = Integer.parseInt(request.getParameter("txtCBU"));
    	    float saldo = Float.parseFloat(request.getParameter("txtSaldo"));
    	  //  boolean activo = Boolean.parseBoolean(request.getParameter("txtActivo"));

    	    Cuenta cuenta = new Cuenta();
    	    cuenta.setId(idCuenta);
    	    cuenta.setTipoCuenta(tipoCuenta);
    	    cuenta.setNumeroCuenta(numeroCuenta);
    	    cuenta.setCbu(cbu);
    	    cuenta.setSaldo(saldo);
    	   // cuenta.setActivo(activo);

    	   //CuentaDaoImpl cuentadao = new CuentaDaoImpl();
    	    CuentaNegocioImpl cuentaNegocio = new CuentaNegocioImpl();
    	    boolean modificada = cuentaNegocio.modificarCuenta(cuenta);
    	    
    	    String mensaje = modificada ? "Cuenta modificada exitosamente." : "Hubo un error al modificar la cuenta.";
    	    request.setAttribute("mensaje", mensaje);

    	    RequestDispatcher dispatcher = request.getRequestDispatcher("/ListarCuenta.jsp");
    	    dispatcher.forward(request, response);
    	    return; // Detener ejecución aquí
    	}

     }
}