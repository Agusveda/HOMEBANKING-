package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocioImpl.CuentaNegocioImpl;
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
		
		if (request.getParameter("btnTransferencia") != null)
		{
			id=2;
    	    request.getSession().setAttribute("id", id);
			RequestDispatcher rd = request.getRequestDispatcher("/generarReporte.jsp");
			rd.forward(request, response);
		}
		
		if (request.getParameter("btnCuentas") != null)
		{
			id=3;
    	    request.getSession().setAttribute("id", id);
			RequestDispatcher rd = request.getRequestDispatcher("/generarReporte.jsp");
			rd.forward(request, response);
		}
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		/// DECLARAMOS OBJETOS
		CuentaNegocioImpl CuentaN = new CuentaNegocioImpl();
		MovimientoNegocioImpl MoviN = new MovimientoNegocioImpl();
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");
        String idStr = request.getSession().getAttribute("id").toString();
        int id = Integer.parseInt(idStr);
        float total = 0;
        float saldo = 0;
		String TipoMovimientoStr = "";
        
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
        	    
        	    //OBTENEMOS EL NOMBRE DEL MOVIMIENTO PARA MOSTRARLO EN EL REPORTE
        	    switch (TipoMovimiento) {
				case 1:
					TipoMovimientoStr = "Alta de Cuenta";
					break;
				case 2:
					TipoMovimientoStr = "Alta de Prestamo";
					break;
				case 3:
					TipoMovimientoStr = "Pago de Prestamo";
					break;

				default:
					break;
				}
        	    
        	    //Lo ponemos en una Session para pasarlo al JSP
        	    request.getSession().setAttribute("TipoMovimiento", TipoMovimientoStr);
        	    
        	    
        	    request.getSession().setAttribute("total", total);
    			RequestDispatcher rd = request.getRequestDispatcher("/generarReporte.jsp");
    			rd.forward(request, response);
        	}
        	
        	///REPORTE DE TRANSFERENCIAS
        	if (id == 2)
        	{
        		
        	}
        	
        	///REPORTE DE CUENTAS
        	if (id == 3)
        	{
        		//Traemos el reporte
        		saldo = CuentaN.ReporteCuentas();
        		
        	    request.getSession().setAttribute("saldo", saldo);
    			RequestDispatcher rd = request.getRequestDispatcher("/generarReporte.jsp");
    			rd.forward(request, response);
        	}
        }
        
        
		
		
		doGet(request, response);
	}

}