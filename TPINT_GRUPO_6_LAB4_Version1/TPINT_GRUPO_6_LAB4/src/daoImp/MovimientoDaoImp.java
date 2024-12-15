package daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Entidades.Cuenta;
import Entidades.Movimiento;
import dao.MovimientoDao;

public class MovimientoDaoImp implements MovimientoDao {
	private static final String ListarMovimientosPorCuenta = "Select * from movimiento where IdCuenta = ?";
	private static final String IngresarMovimientoPositivo = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 4 , CURDATE() , ? , ? , ?)";
	private static final String IngresarMovimientoNegativo = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 4 , CURDATE() , -? , ? , ?)";
	private static final String IngresarMovimientoPositivoAlta = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 1 , CURDATE() , ? , ? , ?)";
	private static final String ModificarCuentaPositivo = "update cuenta SET Saldo = Saldo + ? where Id = ?";
	private static final String ModificarCuentaNegativo = "update cuenta SET Saldo = Saldo - ? where Id = ?";
	private static final String ObtenerIdCuentaPorCBU = "select Id from cuenta where CBU = ? and Activo = 1";
	private static final String ObtenerIdCuentaPorIdCliente = "select Id from cuenta where IdCliente = ? and Activo = 1";
	private static final String ObtenerSaldoPorIdCuenta = "select * from cuenta where Id = ? and Activo = 1 ";
	private static final String ExisteCBU = "SELECT * FROM cuenta WHERE CBU = ? and Activo = 1";
	private static final String ReporteMovimientos = "SELECT SUM(Importe) AS total FROM movimiento WHERE FechaMovimiento BETWEEN ? AND ? AND Importe > 0 and TipoMovimiento = ?";
	private static final String ReporteIngresoMovimiento = "SELECT SUM(m.Importe) AS total FROM movimiento m inner join cuenta c on c.Id = m.idCuenta inner join cliente cli on cli.Id = c.IdCliente WHERE cli.DNI = ? and m.Importe not like '%-%' and c.Activo = 1"; 
	private static final String ReporteEgresoMovimiento = "SELECT SUM(m.Importe) AS total FROM movimiento m inner join cuenta c on c.Id = m.idCuenta inner join cliente cli on cli.Id = c.IdCliente WHERE cli.DNI = ? and m.Importe  like '%-%' and c.Activo = 1";	
	private static final String TraerCuentasPorIdCliente = "select * from cuenta where IdCliente = ? and Activo = 1 ";
	
	@Override
	public int ObtenerIdCuentaPorIdCliente(int IdCliente) {
		int id;
		Cuenta cuenta = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			if (conexion == null || conexion.isClosed()) {
				throw new SQLException("La conexi�n est� cerrada.");
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
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		id = cuenta.getId();

		return id;
	}

	
	@Override
	public int ObtenerIdCuentaPorCBU(int CBU) {
		int id;
		Cuenta cuenta = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			if (conexion == null || conexion.isClosed()) {
				throw new SQLException("La conexi�n est� cerrada.");
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
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		id = cuenta.getId();

		return id;
	}
	
	@Override
	public boolean insertar(Movimiento movi, int idCue) {
		System.out.println("Iniciando inserci�n de movimiento...");
		PreparedStatement statementMovimientoP = null;
		PreparedStatement statementMovimientoN = null;
		PreparedStatement statementCuenta = null;
		PreparedStatement statementBajaSueldo = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		if (conexion == null) {
			System.out.println("No se pudo obtener la conexi�n a la base de datos.");
			return false;
		}
		boolean isInsertExitoso = false;

		try {
			System.out.println("Preparando declaraci�n de inserci�n para movimiento...");
			statementMovimientoP = conexion.prepareStatement(IngresarMovimientoPositivo);

			statementMovimientoP.setFloat(1, movi.getImporte());
			statementMovimientoP.setInt(2, movi.getIdCuenta()); // SE DEBERIA OBTENER ID DE CUENTA
			statementMovimientoP.setString(3, movi.getDetalle());

			if (statementMovimientoP.executeUpdate() > 0) {
				System.out.println("Inserci�n en Movimiento Positivo exitoso.");

				// INSERCION NEGATIVA DE LA TABLA CLIENTE
				statementMovimientoN = conexion.prepareStatement(IngresarMovimientoNegativo);
				statementMovimientoN.setFloat(1, movi.getImporte());
				statementMovimientoN.setInt(2, idCue);
				statementMovimientoN.setString(3, movi.getDetalle());

				if (statementMovimientoN.executeUpdate() > 0) {
					System.out.println("Inserci�n en Movimiento negativo exitoso.");

					System.out.println("Preparando declaraci�n de inserci�n para cuenta.");

					statementBajaSueldo = conexion.prepareStatement(ModificarCuentaNegativo);
					statementBajaSueldo.setFloat(1, movi.getImporte());
					statementBajaSueldo.setInt(2, idCue);

					if (statementBajaSueldo.executeUpdate() > 0) {
						System.out.println("Inserci�n en Cuenta negativa exitosa.");

						statementCuenta = conexion.prepareStatement(ModificarCuentaPositivo);
						statementCuenta.setFloat(1, movi.getImporte());
						statementCuenta.setInt(2, movi.getIdCuenta());
						if (statementCuenta.executeUpdate() > 0) {
							System.out.println("Inserci�n en Cuenta exitosa.");
							conexion.commit();
							isInsertExitoso = true;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error durante la inserci�n.");

			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return isInsertExitoso;
	}

	@Override
	public boolean insertarAltaCuenta(Movimiento movi, int idCue) {
		System.out.println("Iniciando inserci�n de movimiento...");

		PreparedStatement statementMovimientoP = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		if (conexion == null) {
			System.out.println("No se pudo obtener la conexi�n a la base de datos.");
			return false;
		}
		boolean isInsertExitoso = false;

		try {

			System.out.println("Preparando declaraci�n de inserci�n para movimiento...");

			statementMovimientoP = conexion.prepareStatement(IngresarMovimientoPositivoAlta);

			statementMovimientoP.setFloat(1, movi.getImporte());
			statementMovimientoP.setInt(2, idCue);
			statementMovimientoP.setString(3, movi.getDetalle());
			
			if (statementMovimientoP.executeUpdate() > 0) 
			{
				conexion.commit();
				System.out.println("Inserci�n en Movimiento Positivo exitoso.");
				isInsertExitoso = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error durante la inserci�n.");

			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return isInsertExitoso;
	}
	
	@Override
	public ArrayList<Movimiento> ListarMovimientosPorCuenta(int idCue) {
		ArrayList<Movimiento> ListaMovimiento = new ArrayList<Movimiento>();
		Movimiento movi = new Movimiento();
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			if (conexion == null || conexion.isClosed()) {
				throw new SQLException("La conexi�n est� cerrada.");
			}

			statement = conexion.prepareStatement(ListarMovimientosPorCuenta);
			statement.setInt(1, idCue);
			rs = statement.executeQuery();

			while (rs.next()) {
				movi = new Movimiento();
				movi.setId(rs.getInt("Id"));
				movi.setTipoMovimiento(rs.getInt("TipoMovimiento"));
				movi.setFechaMovimiento(rs.getString("FechaMovimiento"));
				movi.setImporte(rs.getFloat("Importe"));
				movi.setIdCuenta(rs.getInt("IdCuenta"));
				movi.setDetalle(rs.getString("Detalle"));
				ListaMovimiento.add(movi);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return ListaMovimiento;
	}
	
	@Override
	public float ObtenerSaldoPorIdCuenta(int idCue) {
		float saldo;
		Cuenta cuenta = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			if (conexion == null || conexion.isClosed()) {
				throw new SQLException("La conexi�n est� cerrada.");
			}

			statement = conexion.prepareStatement(ObtenerSaldoPorIdCuenta);
			statement.setInt(1, idCue);
			rs = statement.executeQuery();

			if (rs.next()) {
				cuenta = new Cuenta();
				cuenta.setSaldo(rs.getFloat("Saldo"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		saldo = cuenta.getId();

		return saldo;
	}
	
	///SECCION DE VALIDACIONES
	@Override
	public boolean ExisteCBU(int Cbu) 
	{
		
		boolean exists = false;
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        
	        connection = Conexion.getConexion().getSQLConexion();
	        if (connection == null) {
	            throw new SQLException("Conexi�n a la base de datos es nula");
	        }

	        
	        String query = ExisteCBU;
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, Cbu);

	        
	        resultSet = preparedStatement.executeQuery();

	        
	        if (resultSet.next()) {
	            exists = resultSet.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    } finally {
	        
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	            // NO cierres la conexi�n aqu� si usas un pool de conexiones
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return exists;
	}
	
	@Override
	public float ReporteMovimiento(int TipoMovimiento, String FechaInicio, String FechaFinal) 
	{
		float total = 0;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try 
		{
			if (conexion == null || conexion.isClosed()) 
			{
				throw new SQLException("La conexi�n est� cerrada.");
			}

			statement = conexion.prepareStatement(ReporteMovimientos);
			statement.setString(1, FechaInicio);
			statement.setString(2, FechaFinal);
			statement.setInt(3, TipoMovimiento);
			rs = statement.executeQuery();

			if (rs.next()) 
			{
				total = rs.getFloat("Total");
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		return total;
	}

	@Override
	public ArrayList<Cuenta> TraeCuentasPorIdCliente(int idCliente) {
	    ArrayList<Cuenta> CuentasCliente = new ArrayList<>();
	    System.out.println("Buscando cuentas para IdCliente: " + idCliente);

	    String query = TraerCuentasPorIdCliente;
	    Connection con = Conexion.getConexion().getSQLConexion();

	    try {
	        // Verifica si la conexi�n est� cerrada y reconecta si es necesario
	        if (con == null || con.isClosed()) {
	            System.out.println("Conexi�n cerrada, intentando reconectar...");
	            con = Conexion.getConexion().getSQLConexion();  // Vuelve a obtener la conexi�n si est� cerrada
	        }

	        // Prepara la consulta
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setInt(1, idCliente);
	        ResultSet rs = ps.executeQuery();

	        // Procesa los resultados
	        while (rs.next()) {
	            Cuenta cue = new Cuenta();
	            cue.setId(rs.getInt("Id"));
	            cue.setNumeroCuenta(rs.getInt("NumeroCuenta"));
	            cue.setTipoCuenta(rs.getInt("TipoCuenta"));
	            cue.setCbu(rs.getInt("CBU"));
	            cue.setSaldo(rs.getFloat("Saldo"));

	            System.out.println("Cuenta encontrada: Id=" + cue.getId() + ", NumeroCuenta=" + cue.getNumeroCuenta()
	                    + ", Saldo=" + cue.getSaldo());

	            CuentasCliente.add(cue);
	        }

	        // Verifica si no se encontraron cuentas
	        if (CuentasCliente.isEmpty()) {
	            System.out.println("No se encontraron cuentas para IdCliente: " + idCliente);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error durante la consulta de cuentas.");
	    }

	    return CuentasCliente;
	}

	@Override
	public float IngresoDeCliente(int DNICLIENTE) 
	{
		float total = 0;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try 
		{
			if (conexion == null || conexion.isClosed()) 
			{
				throw new SQLException("La conexi�n est� cerrada.");
			}

			statement = conexion.prepareStatement(ReporteIngresoMovimiento);
			statement.setInt(1, DNICLIENTE);
			
			rs = statement.executeQuery();

			if (rs.next()) 
			{
				total = rs.getFloat("total");
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}

		return total;
	}
	
	@Override
	public float EgresoDeCliente(int DNICLIENTE) {
	    float total = 0;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    Connection conexion = Conexion.getConexion().getSQLConexion();

	    try {
	        if (conexion == null || conexion.isClosed()) {
	            throw new SQLException("La conexi�n est� cerrada.");
	        }

	    
	        statement = conexion.prepareStatement(ReporteEgresoMovimiento);
	        statement.setInt(1, DNICLIENTE);

	        rs = statement.executeQuery();

	        if (rs.next()) {
	            total = rs.getFloat("total");
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

	    return total;
	}
}