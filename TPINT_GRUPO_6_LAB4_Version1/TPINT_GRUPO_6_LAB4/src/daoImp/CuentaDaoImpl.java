package daoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.mysql.jdbc.Statement;

import Entidades.Cliente;
import Entidades.Cuenta;
import Excepciones.ClienteExcedeCantCuentas;
import dao.CuentaDao;

public class CuentaDaoImpl implements CuentaDao {

    private static final String insertCuenta = "INSERT INTO cuenta ( IdCliente, TipoCuenta, FechaCreacion , NumeroCuenta, CBU, Saldo, Activo) VALUES ( ?, ?, CURDATE(), ?, ?, 10000, ?)";
    private static final String ListarCuenta = "SELECT cuenta.id AS ID, cliente.nombre AS Nombre, cliente.apellido AS Apellido, cliente.dni AS DNI, cuenta.tipocuenta AS TipoCuenta, cuenta.FechaCreacion AS FechaCreacion, cuenta.NumeroCuenta, cuenta.CBU AS CBU, cuenta.Saldo AS Saldo, cuenta.Activo AS Activo FROM cuenta INNER JOIN cliente ON cuenta.IdCliente = cliente.id WHERE cuenta.Activo = 1";
    private static final String ListarCuentaTodos = "SELECT cuenta.id AS ID, cliente.nombre AS Nombre, cliente.apellido AS Apellido, cliente.dni AS DNI, cuenta.tipocuenta AS TipoCuenta, cuenta.FechaCreacion AS FechaCreacion, cuenta.NumeroCuenta, cuenta.CBU AS CBU, cuenta.Saldo AS Saldo, cuenta.Activo AS Activo FROM cuenta INNER JOIN cliente ON cuenta.IdCliente = cliente.id AND cuenta.Activo = 1";
    private static final String EliminarCuenta = "UPDATE cuenta SET Activo = 0 WHERE id = ?";
    private static final String ModificarCuenta = "UPDATE cuenta SET TipoCuenta = ?, NumeroCuenta = ?, CBU = ?, Saldo = ? WHERE Id = ?";
    private static final String ObtenerCuentaPorId = "SELECT * FROM cuenta WHERE id = ?";
    private static final String ObtenerCuentaPorIdCliente = "SELECT * FROM cuenta WHERE IdCliente = ?";
    private static final String ObtenerProximoIdCuenta = "select * from cuenta ORDER BY Id DESC LIMIT 1;";
    private static final String ClienteInactivo = "select * from cliente where Id = ? and Activo = 0;";
    private static final String ReporteCuentas = "SELECT SUM(Saldo) AS saldo FROM cuenta";

    @Override
    public boolean insertCuenta(Cuenta cuenta) {
        PreparedStatement statement = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        boolean isInsertExitoso = false;

        try {
            // Asegurarse de que la conexión no esté cerrada
            if (conexion == null || conexion.isClosed()) {
                throw new SQLException("La conexión está cerrada.");
            }

            conexion.setAutoCommit(false);

            statement = conexion.prepareStatement(insertCuenta, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, cuenta.getIdCliente());
            statement.setInt(2, cuenta.getTipoCuenta());
      
            int numcuenta = GenerarNumeroCuenta();
            statement.setInt(3, numcuenta);
            
            int cbu = GenerarCBU();
            statement.setInt(4, cbu);
      
            statement.setBoolean(5, cuenta.isActivo());
            if (statement.executeUpdate() > 0) {
                // Obtener el ID generado
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cuenta.setId(generatedKeys.getInt(1));  // Asignar el ID generado
                        conexion.commit();
                        isInsertExitoso = true;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) 
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isInsertExitoso;
    }

    public int GenerarNumeroCuenta() {
        int numCuenta = 10000;  
        PreparedStatement statement = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        ResultSet rs = null;

        try {
            String query = "SELECT NumeroCuenta FROM cuenta ORDER BY NumeroCuenta DESC LIMIT 1";
            statement = conexion.prepareStatement(query);
            rs = statement.executeQuery();
            
            if (rs.next()) {
                numCuenta = rs.getInt("NumeroCuenta") + 1;
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

        return numCuenta;
    }


    public int GenerarCBU() {
        int cbu = 0;
        Random random = new Random();
        cbu = 1000000000 + random.nextInt(900000000);
        return cbu;
    }
 
    
    public ArrayList<Cuenta> ListarCuenta(int DNI) {
        ArrayList<Cuenta> ListaCuenta = new ArrayList<>();
        
        String query = ListarCuenta; 
        
        Connection con = Conexion.getConexion().getSQLConexion();
                
        try {
        	PreparedStatement ps = con.prepareStatement(query); 
        	ps.setInt(1, DNI );
        	ResultSet rs = ps.executeQuery();
        	
            while (rs.next()) {
            	  Cliente cli = new Cliente();
                  Cuenta cue = new Cuenta();

                  cue.setId(rs.getInt("ID"));
                  cli.setNombre(rs.getString("Nombre"));
                  cli.setApellido(rs.getString("Apellido"));
                  cli.setDni(rs.getInt("DNI"));
                  cli.setNombre(rs.getString("Nombre"));
                  cli.setApellido(rs.getString("Apellido"));
                  cli.setDni(rs.getInt("DNI"));
                  cue.setTipoCuenta(rs.getInt("TipoCuenta"));
                  cue.setFechaCreacion(rs.getString("FechaCreacion"));
                  cue.setNumeroCuenta(rs.getInt("NumeroCuenta"));
                  cue.setCbu(rs.getInt("CBU"));
                  cue.setSaldo(rs.getFloat("Saldo"));
                  cue.setActivo(rs.getBoolean("Activo"));
                  cue.setCliente(cli);
                  
                  ListaCuenta.add(cue);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            
        }
        return ListaCuenta;
    }    
    
    public ArrayList<Cuenta> ListarCuenta() {
        ArrayList<Cuenta> ListaCuenta = new ArrayList<>();
        
        
        String query = ListarCuentaTodos; 
        
        Connection con = Conexion.getConexion().getSQLConexion();
        
        
        try {
        	PreparedStatement ps = con.prepareStatement(query); 
        	
        
        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Cliente cli = new Cliente();
                Cuenta cue = new Cuenta();
                cue.setId(rs.getInt("ID"));
                cli.setNombre(rs.getString("Nombre"));
                cli.setApellido(rs.getString("Apellido"));
                cli.setDni(rs.getInt("DNI"));
                cue.setTipoCuenta(rs.getInt("TipoCuenta"));
                cue.setFechaCreacion(rs.getString("FechaCreacion"));
                cue.setNumeroCuenta(rs.getInt("NumeroCuenta"));
                cue.setCbu(rs.getInt("CBU"));
                cue.setSaldo(rs.getFloat("Saldo"));
                cue.setActivo(rs.getBoolean("Activo"));
                cue.setCliente(cli);
                
                ListaCuenta.add(cue);
            }
        	
        }
        catch (SQLException e) {
            e.printStackTrace();
            
        }
        
        return ListaCuenta;
    }    
 
    public boolean EliminarCuenta(int id) {
    	Connection conexion = null;
    PreparedStatement stmt = null;
    boolean success = false;

    try {
      
        System.out.println("Intentando conectar a la base de datos...");
        conexion = Conexion.getConexion().getSQLConexion();

   
        if (conexion != null) {
            System.out.println("Conexión a la base de datos establecida.");
        } else {
            System.out.println("Error al conectar con la base de datos.");
            return false;
        } 
        
        conexion.setAutoCommit(false);
        
        String query = EliminarCuenta;
        stmt = conexion.prepareStatement(query);
        stmt.setInt(1, id);

        System.out.println("Ejecutando actualización para eliminar cuenta con ID: " + id);
        int rowsAffected = stmt.executeUpdate();

   
 
        if (rowsAffected > 0) {
            success = true;
            System.out.println("cuenta eliminada exitosamente. Filas afectadas: " + rowsAffected);
            conexion.commit(); 
        } else {
            System.out.println("No se encontró ningún cliente con ID: " + id);
            conexion.rollback(); 
        }

    } catch (SQLException e) {
        System.out.println("Error de SQL al intentar eliminar el cliente.");
        e.printStackTrace();
        try {
            if (conexion != null) {
                conexion.rollback(); 
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    } finally {
        
        if (stmt != null) {
            try {
                stmt.close();
                System.out.println("PreparedStatement cerrado.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar PreparedStatement.");
                e.printStackTrace();
            }
        }      
    }
    return success;
}

    public boolean modificarCuenta(Cuenta cuenta) {
        boolean exitoso = false;
        PreparedStatement statement = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();

        try {
            if (conexion == null || conexion.isClosed()) {
                throw new SQLException("La conexi�n est� cerrada.");
            }

            conexion.setAutoCommit(false);
            statement = conexion.prepareStatement(ModificarCuenta);
            
            statement.setInt(1, cuenta.getTipoCuenta());
            statement.setInt(2, cuenta.getNumeroCuenta());
            statement.setInt(3, cuenta.getCbu());
            statement.setFloat(4, cuenta.getSaldo());
          //  statement.setBoolean(5, cuenta.isActivo());
            statement.setInt(5, cuenta.getId());  // Usamos el ID para identificar la cuenta a modificar

            if (statement.executeUpdate() > 0) {
                conexion.commit();
                exitoso = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exitoso;
    }
    
    public Cuenta obtenerCuentaPorId(int id) {
        Cuenta cuenta = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();

        try {
            if (conexion == null || conexion.isClosed()) {
                throw new SQLException("La conexión está cerrada.");
            }

            statement = conexion.prepareStatement(ObtenerCuentaPorId);
            statement.setInt(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                cuenta = new Cuenta();
                cuenta.setId(rs.getInt("Id"));
                cuenta.setIdCliente(rs.getInt("IdCliente"));
                cuenta.setTipoCuenta(rs.getInt("TipoCuenta"));
                cuenta.setFechaCreacion(rs.getString("FechaCreacion"));
                cuenta.setNumeroCuenta(rs.getInt("NumeroCuenta"));
                cuenta.setCbu(rs.getInt("CBU"));
                cuenta.setSaldo(rs.getFloat("Saldo"));
                cuenta.setActivo(rs.getBoolean("Activo"));
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

        return cuenta;
    }
    
    public Cuenta obtenerCuentaPorIdCliente(int id) {
        Cuenta cuenta = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();

        try {
            if (conexion == null || conexion.isClosed()) {
                throw new SQLException("La conexión está cerrada.");
            }

            statement = conexion.prepareStatement(ObtenerCuentaPorIdCliente);
            statement.setInt(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                cuenta = new Cuenta();
                cuenta.setId(rs.getInt("Id"));
                cuenta.setIdCliente(rs.getInt("IdCliente"));
                cuenta.setTipoCuenta(rs.getInt("TipoCuenta"));
                cuenta.setFechaCreacion(rs.getString("FechaCreacion"));
                cuenta.setNumeroCuenta(rs.getInt("NumeroCuenta"));
                cuenta.setCbu(rs.getInt("CBU"));
                cuenta.setSaldo(rs.getFloat("Saldo"));
                cuenta.setActivo(rs.getBoolean("Activo"));
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

        return cuenta;
    }
    
    
    public ArrayList<Cuenta> filtrarCuentaXTipoCuenta(int tipoCuenta) {
        ArrayList<Cuenta> listaCuentas = new ArrayList<>();
        
        String query = "SELECT cuenta.id AS ID, cliente.nombre AS Nombre, cliente.apellido AS Apellido, " +
                       "cuenta.tipocuenta AS TipoCuenta, cuenta.FechaCreacion AS FechaCreacion, cuenta.NumeroCuenta, " +
                       "cuenta.CBU AS CBU, cuenta.Saldo AS Saldo, cuenta.Activo AS Activo " +
                       "FROM cuenta " +
                       "INNER JOIN cliente ON cuenta.IdCliente = cliente.id " +
                       "WHERE cuenta.TipoCuenta = ? AND cuenta.Activo = 1"; 
        
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conexion = Conexion.getConexion().getSQLConexion(); 
            statement = conexion.prepareStatement(query);
            statement.setInt(1, tipoCuenta); 
            rs = statement.executeQuery();

            while (rs.next()) {
                Cliente cli = new Cliente();
                Cuenta cue = new Cuenta();

                // Mapear datos de la tabla cliente
                cli.setNombre(rs.getString("Nombre"));
                cli.setApellido(rs.getString("Apellido"));

                // Mapear datos de la tabla cuenta
                cue.setId(rs.getInt("ID"));
                cue.setTipoCuenta(rs.getInt("TipoCuenta"));
                cue.setFechaCreacion(rs.getString("FechaCreacion"));
                cue.setNumeroCuenta(rs.getInt("NumeroCuenta"));
                cue.setCbu(rs.getInt("CBU"));
                cue.setSaldo(rs.getFloat("Saldo"));
                cue.setActivo(rs.getBoolean("Activo"));
                cue.setCliente(cli); // Asociar cliente a la cuenta

                listaCuentas.add(cue);
            }

            System.out.println("Cuentas encontradas para el tipo de cuenta " + tipoCuenta + ": " + listaCuentas.size());
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listaCuentas;
    }


	@Override
	public float ReporteCuentas() {
		float saldo = 0;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try 
		{
			if (conexion == null || conexion.isClosed()) 
			{
				throw new SQLException("La conexión está cerrada.");
			}

			statement = conexion.prepareStatement(ReporteCuentas);
			rs = statement.executeQuery();

			if (rs.next()) 
			{
				saldo = rs.getFloat("saldo");
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

		return saldo;
	}

	@Override
	public boolean ExisteId(int dni) {
		boolean exists = false;
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        
	        connection = Conexion.getConexion().getSQLConexion();
	        if (connection == null) {
	            throw new SQLException("Conexión a la base de datos es nula");
	        }
	        
	        String query = "SELECT COUNT(*) FROM cliente WHERE Id = ? and Activo = 1";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, dni);
	        
	        resultSet = preparedStatement.executeQuery();

	        
	        if (resultSet.next()) 
	        {
	            exists = resultSet.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    } finally {
	        
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return exists;
	}

	@Override
	public int ObtenerProximoIdCuenta() 
	{
		int id = 0;
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        
	        connection = Conexion.getConexion().getSQLConexion();
	        if (connection == null) {
	            throw new SQLException("Conexión a la base de datos es nula");
	        }
	        
	        String query = ObtenerProximoIdCuenta;
	        preparedStatement = connection.prepareStatement(query);

	        
	        resultSet = preparedStatement.executeQuery();

	        
	        if (resultSet.next()) 
	        {
	            id = resultSet.getInt("Id");
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    } finally {
	        
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return id;
	}

	
	@Override
	public void verificarCuentasPorCliente(int idCliente) throws ClienteExcedeCantCuentas
	{
		int cuentas = 0;
	    try (Connection conexion = Conexion.getConexion().getSQLConexion();
	         PreparedStatement statement = conexion.prepareStatement("SELECT COUNT(*) FROM Cuenta WHERE idCliente = ? and Activo = 1")) {

	        statement.setInt(1, idCliente);
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                cuentas = rs.getInt(1);
	            }
	        }

	        if (cuentas >= 3) {
	            throw new ClienteExcedeCantCuentas("Los clientes no pueden tener más de 3 cuentas.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Error al verificar las cuentas del cliente.", e);
	    }
	}
	
	@Override
	public int ClienteInactivo(int idCliente) 
	{
        int cliente = 0;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();

        try {
            if (conexion == null || conexion.isClosed()) {
                throw new SQLException("La conexión está cerrada.");
            }

            statement = conexion.prepareStatement(ClienteInactivo);
            statement.setInt(1, idCliente);
            rs = statement.executeQuery();

            if (rs.next()) 
            {
            	cliente++;
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

        return cliente;
	}        
}
