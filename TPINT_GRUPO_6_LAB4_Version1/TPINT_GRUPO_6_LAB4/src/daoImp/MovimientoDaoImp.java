package daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Entidades.Cuenta;
import Entidades.Movimiento;
import dao.MovimientoDao;

public class MovimientoDaoImp implements MovimientoDao
{
	private static final String IngresarMovimientoPositivo = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 4 , CURDATE() , ? , ? , ?)";
	private static final String IngresarMovimientoNegativo = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 4 , CURDATE() , -? , ? , ?)";
	private static final String ModificarCuentaPositivo = "update cuenta SET Saldo = Saldo + ? where Id = ?";
	private static final String ModificarCuentaNegativo = "update cuenta SET Saldo = Saldo - ? where Id = ?";
	private static final String ObtenerIdCuentaPorCBU = "select Id from cuenta where CBU = ?";
	private static final String ObtenerIdCuentaPorIdCliente = "select Id from cuenta where IdCliente = ?";
	
	
	@Override
	public boolean insertar(Movimiento movi, int idCue) 
	{		
	    System.out.println("Iniciando inserción de movimiento...");

	    PreparedStatement statementMovimientoP = null;
	    PreparedStatement statementMovimientoN = null;
	    PreparedStatement statementCuenta = null;
	    PreparedStatement statementBajaSueldo = null;
	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    if (conexion == null) 
	    {
	        System.out.println("No se pudo obtener la conexión a la base de datos.");
	        return false;
	    }
	    boolean isInsertExitoso = false;
	    
	    try {
	    	
	        // Inserción en la tabla Cliente con generación de ID
	        System.out.println("Preparando declaración de inserción para movimiento...");

	        statementMovimientoP = conexion.prepareStatement(IngresarMovimientoPositivo);
	       
	        statementMovimientoP.setFloat(1, movi.getImporte());
	        statementMovimientoP.setInt(2, movi.getIdCuenta()); // SE DEBERIA OBTENER ID DE CUENTA
	        statementMovimientoP.setString(3, movi.getDetalle());

	        if (statementMovimientoP.executeUpdate() > 0) 
	        {
	            System.out.println("Inserción en Movimiento Positivo exitoso.");
	            
	            //INSERCION NEGATIVA DE LA TABLA CLIENTE
	            
	            statementMovimientoN = conexion.prepareStatement(IngresarMovimientoNegativo);
			       
	            statementMovimientoN.setFloat(1, movi.getImporte());
	            statementMovimientoN.setInt(2, idCue); 
	            statementMovimientoN.setString(3, movi.getDetalle());

		        if (statementMovimientoN.executeUpdate() > 0) {
		            System.out.println("Inserción en Movimiento negativo exitoso.");
		        }
	            

	                // Inserción en Cuenta
	            System.out.println("Preparando declaración de inserción para cuenta.");

	                statementCuenta = conexion.prepareStatement(ModificarCuentaPositivo);
	                statementCuenta.setFloat(1, movi.getImporte());
	                statementCuenta.setInt(2, movi.getIdCuenta());

	                statementBajaSueldo = conexion.prepareStatement(ModificarCuentaNegativo);
	                statementBajaSueldo.setFloat(1, movi.getImporte());
	                statementBajaSueldo.setInt(2, idCue);
	                
			        if (statementBajaSueldo.executeUpdate() > 0) {
			            System.out.println("Inserción en Cuenta negativa exitosa.");
			        }


	                // Ejecutar la inserción de Usuario
	                if (statementCuenta.executeUpdate() > 0) 
	                {
	                    System.out.println("Inserción en Cuenta exitosa.");

	                    conexion.commit();
	                    isInsertExitoso = true;
	                }
	            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error durante la inserción.");

	        try {
	            conexion.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	    return isInsertExitoso;
	}



	@Override
	public int ObtenerIdCuentaPorCBU(int CBU) 
	{
				int id;
		        Cuenta cuenta = null;
		        PreparedStatement statement = null;
		        ResultSet rs = null;
		        Connection conexion = Conexion.getConexion().getSQLConexion();

		        try {
		            if (conexion == null || conexion.isClosed()) {
		                throw new SQLException("La conexión está cerrada.");
		            }

		            statement = conexion.prepareStatement(ObtenerIdCuentaPorCBU);
		            statement.setInt(1, CBU);
		            rs = statement.executeQuery();

		            if (rs.next()) {
		                cuenta = new Cuenta();
		                cuenta.setId(rs.getInt("Id"));
		            }

		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                if (rs != null) rs.close();
		                if (statement != null) statement.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }

		        id = cuenta.getId();
		        
		        return id;
	}
		
		
	public int ObtenerIdCuentaPorIdCliente(int IdCliente) 
	{
				int id;
		        Cuenta cuenta = null;
		        PreparedStatement statement = null;
		        ResultSet rs = null;
		        Connection conexion = Conexion.getConexion().getSQLConexion();

		        try {
		            if (conexion == null || conexion.isClosed()) {
		                throw new SQLException("La conexión está cerrada.");
		            }

		            statement = conexion.prepareStatement(ObtenerIdCuentaPorIdCliente);
		            statement.setInt(1, IdCliente);
		            rs = statement.executeQuery();

		            if (rs.next()) {
		                cuenta = new Cuenta();
		                cuenta.setId(rs.getInt("Id"));
		            }

		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                if (rs != null) rs.close();
		                if (statement != null) statement.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }

		        id = cuenta.getId();
		        
		        return id;
	}		
		
		
		
		
		
	
}