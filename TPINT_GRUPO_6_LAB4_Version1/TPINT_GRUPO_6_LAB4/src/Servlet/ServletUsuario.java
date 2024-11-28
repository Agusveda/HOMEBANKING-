package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entidades.Cliente;
import daoImp.UsuarioDaoImp;


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
		String idUsuarioStr = request.getParameter("IdCliente");
		int idUsuario = Integer.parseInt(idUsuarioStr);
		
		UsuarioDaoImp usuarioDao = new UsuarioDaoImp();
        Cliente cliente = usuarioDao.ObtenerDatosXidUsuario(idUsuario);
        
        if(cliente != null) {
        	HttpSession session = request.getSession();
            session.setAttribute("Cliente", cliente);

            // Redirigir al JSP que muestra los datos personales
            request.getRequestDispatcher("InformacionPersonal.jsp").forward(request, response);
        }else {
        	request.setAttribute("Mensaje", "No se encontraron datos para el usuario.");
        }
		
		doGet(request, response);
	}

}
