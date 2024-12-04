package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletVerMovimiento")
public class ServletVerMovimiento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ServletVerMovimiento() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("btnVerMovimientos") != null)
		{
    	    int id = Integer.parseInt(request.getParameter("idCuenta"));
    	    request.getSession().setAttribute("idCuenta", id);
    	    RequestDispatcher rd = request.getRequestDispatcher("/VerMovimientos.jsp");
    	    rd.forward(request, response);
    	    
    	    /**
			request.setAttribute("idCuenta", id);
    	    return;
    	    **/
		}
		
		
		
		
		
		
		
		
		doGet(request, response);
	}

}
