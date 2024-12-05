package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ServletReportes")
public class ServletReportes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ServletReportes() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		///REDIRECCION DEPENDIENDO DEL REPORTE QUE ELIJA
		int id=0;
		if (request.getParameter("btnMovimientos") != null)
		{
			id=1;
    	    request.getSession().setAttribute("id", id);
    	    request.getSession().setAttribute("id", id);
			RequestDispatcher rd = request.getRequestDispatcher("/generarReporte.jsp");
			rd.forward(request, response);
		}
		
		if (request.getParameter("btnClientes") != null)
		{
			id=2;
    	    request.getSession().setAttribute("id", id);
			RequestDispatcher rd = request.getRequestDispatcher("/generarReporte.jsp");
			rd.forward(request, response);
		}
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");
		
		///VALIDACIONES - FALTA CARTEL!!
        if (fechaInicio == null || fechaFin == null || fechaInicio.isEmpty() || fechaFin.isEmpty()) {

            return;
        }
        
        
		
		
		doGet(request, response);
	}

}
