package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entidades.Cliente;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.UsuarioNagocionImp;

/**
 * Servlet implementation class ServletUsuario
 */
@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletUsuario() {
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
		/*
		String idCli = request.getSession().getAttribute("IdCliente").toString();
	    System.out.println("ID Cliente obtenido de la sesión: " + idCli);
	    
	    if(idCli == null) {
	    	response.sendRedirect("Login.jsp");
	    	return;
	    }
	    
	    
	    ClienteNegocioImpl usuarioNegocio = new ClienteNegocioImpl();
	    Cliente cliente = usuarioNegocio.ObtenerDatosXid(Integer.parseInt(idCli));
        
        if(cliente != null) {
        	request.setAttribute("cliente", cliente);
            request.getRequestDispatcher("InformacionPersonal.jsp").forward(request, response);
        
        }else {
        	request.setAttribute("Mensaje", "No se encontraron datos para el usuario.");
        }
		
		doGet(request, response);
		*/
	}

}
