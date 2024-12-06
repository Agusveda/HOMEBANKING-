package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocioImpl.MovimientoNegocioImpl;


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
		
		
		
		/// DECLARAMOS OBJETOS
		MovimientoNegocioImpl MoviN = new MovimientoNegocioImpl();
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");
        String idStr = request.getSession().getAttribute("id").toString();
        int id = Integer.parseInt(idStr);
        float total = 0;
		
		///VALIDACIONES - FALTA CARTEL!!
        if (fechaInicio == null || fechaFin == null || fechaInicio.isEmpty() || fechaFin.isEmpty()) 
        {
        	
            return;
        }
        
        
        
        if (request.getParameter("btnReportes") != null)
        {
        	/// REPORTE DE MOVIMIENTOS
        	if (id == 1)
        	{
        		///Valido que TipoMovimiento no sea null dentro del ID = 1 porque si es distinto a 1, no deberia cargarse.
        		if (request.getParameter("TipoMovimiento") == null || request.getParameter("TipoMovimiento").isEmpty())
        		{
        			
        			return;
        		}
        		
        		int TipoMovimiento = Integer.parseInt(request.getParameter("TipoMovimiento"));
        		total = MoviN.ReporteMovimiento(TipoMovimiento, fechaInicio, fechaFin);
        	    request.getSession().setAttribute("total", total);
    			RequestDispatcher rd = request.getRequestDispatcher("/generarReporte.jsp");
    			rd.forward(request, response);
        	}
        	
        	///REPORTE DE CLIENTES
        	if (id == 2)
        	{
        		
        	}
        }
        
        
		
		
		doGet(request, response);
	}

}