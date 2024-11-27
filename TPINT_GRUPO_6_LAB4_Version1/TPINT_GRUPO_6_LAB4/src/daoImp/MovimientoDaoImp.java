package daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Entidades.Movimiento;
import dao.MovimientoDao;

public class MovimientoDaoImp implements MovimientoDao
{
	private static final String Insert = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( ? , CURDATE() , ? , ? , ?)";
	private static final String Modificar = "update Cuenta SET Saldo = ? where Id = ?)";
	
	@Override
	public boolean insertar(Movimiento movi) 
	{		
	    System.out.println("Iniciando inserción de movimiento...");

	    PreparedStatement statementMovimiento = null;
	    PreparedStatement statementCuenta = null;
	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    if (conexion == null) {
	        System.out.println("No se pudo obtener la conexión a la base de datos.");
	        return false;
	    }
	    boolean isInsertExitoso = false;
	    
	    try {
	        // Inserción en la tabla Cliente con generación de ID
	        System.out.println("Preparando declaración de inserción para Cliente...");

	        statementMovimiento = conexion.prepareStatement(Insert, Statement.RETURN_GENERATED_KEYS);
	        statementMovimiento.setInt(1, movi.getTipoMovimiento());
	        statementMovimiento.setFloat(2, movi.getImporte());
	        statementMovimiento.setInt(3, movi.getIdCuenta()); // SE DEBERIA OBTENER ID DE CUENTA
	        statementMovimiento.setString(4, movi.getDetalle());

	        if (statementMovimiento.executeUpdate() > 0) {
	            System.out.println("Inserción en Movimiento exitosa.");

	                // Inserción en Cuenta
	                
	                statementCuenta = conexion.prepareStatement(Modificar);
	                statementCuenta.setFloat(1, movi.getImporte());
	                statementCuenta.setInt(2, movi.getIdCuenta());


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
}
