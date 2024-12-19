package daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entidades.Cuenta;
import Entidades.Cuota;
import Entidades.Movimiento;
import Entidades.Prestamo;
import dao.PrestamoDao;

public class PrestamoDaoImp implements PrestamoDao{
	private static final String InsertarPrestamo = "INSERT INTO prestamo (IdCliente, IdCuenta ,ImportePedidoCliente, FechaAlta, CantidadCuotas, confirmacion) VALUES (?,?, ?, NOW(), ?, ?)";	
	private static final String CargarPrestamoEnCuenta = "update cuenta set saldo = saldo + ? where Id = ? ";
	private static final String ListarPrestamosPedidosAutorizados = "SELECT Id, IdCliente,IdCuenta, ImportePedidoCliente,FechaAlta,CantidadCuotas,confirmacion FROM prestamo where confirmacion = 1 order by id desc";
	private static final String InsertarCuotasEnPrestamo = "INSERT INTO cuota (IdPrestamo, NumeroCuota, Monto, estaPagada, FechaPago) VALUES (?, ?, ?, ?, ?)";
	private static final String ListarTodosPrestamos = "SELECT Id, IdCliente,IdCuenta, ImportePedidoCliente,FechaAlta,CantidadCuotas,confirmacion FROM prestamo where confirmacion = 0 order by id desc";
	private static final String ObtenerImportePrestamo = "SELECT ImportePedidoCliente FROM prestamo WHERE id = ?";
	private static final String ObtenerIdCuentaPrestamo = "SELECT IdCuenta FROM prestamo WHERE id = ?";
	private static final String ObtenerPrestamosPendientes = "SELECT Id, IdCliente, ImportePedidoCliente, FechaAlta, CantidadCuotas, confirmacion FROM prestamo WHERE  IdCliente = ?";
	private static final String ObtenerPrestamosConfirmados = "SELECT p.Id, p.IdCliente, p.IdCuenta, p.ImportePedidoCliente AS ImporteCliente, p.FechaAlta, p.CantidadCuotas, p.confirmacion FROM prestamo p WHERE p.IdCliente = ? AND p.confirmacion = 1";
	private static final String AprobarPrestamo = "UPDATE prestamo SET confirmacion = ? WHERE Id = ?";
	private static final String DenegarPrestamo = "UPDATE prestamo SET confirmacion = ? WHERE id = ?";
	@Override
	public boolean insertarPrestamo(Prestamo prestamo, List<Cuota> cuotas) {
	    boolean isInsertExitoso = false;

	    try (Connection connection = Conexion.getConexion().getSQLConexion()) {
	        if (connection == null) {
	            System.out.println("No se pudo obtener la conexión a la base de datos.");
	            return false;
	        }

	        connection.setAutoCommit(false);

	        // Inserción del préstamo
	        try (PreparedStatement statement = connection.prepareStatement(InsertarPrestamo, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setInt(1, prestamo.getIdCliente());
	            statement.setInt(2, prestamo.getIdCuenta());
	            statement.setFloat(3, prestamo.getImporteCliente());
	            statement.setInt(4, prestamo.getCantCuo());
	            statement.setInt(5, 0); 

	            int rowsAffected = statement.executeUpdate();

	            if (rowsAffected > 0) {
	                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                    if (generatedKeys.next()) {
	                        int idPrestamo = generatedKeys.getInt(1);

	                        // Inserción de las cuotas
	                        try (PreparedStatement statementCuota = connection.prepareStatement(InsertarCuotasEnPrestamo)) {
	                            for (Cuota cuota : cuotas) {
	                                statementCuota.setInt(1, idPrestamo);
	                                statementCuota.setInt(2, cuota.getNumeroCuota());
	                                statementCuota.setDouble(3, cuota.getMonto());
	                                statementCuota.setInt(4, 0); // Esta cuota no está pagada
	                                statementCuota.setDate(5, cuota.getFechaPago()); 
	                                statementCuota.addBatch();
	                            }

	                            // Ejecutar las inserciones de cuotas en batch
	                            int[] cuotasAfectadas = statementCuota.executeBatch();

	                            // Verificar si todas las cuotas fueron insertadas
	                            if (cuotasAfectadas.length == cuotas.size()) {
	                                connection.commit(); // Confirmamos la transacción
	                                isInsertExitoso = true;
	                                System.out.println("Préstamo y cuotas insertados correctamente.");
	                            } else {
	                                connection.rollback(); // Rollback si alguna cuota falla
	                                System.out.println("Error al insertar las cuotas. Se realizó un rollback.");
	                            }
	                        }
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            // Si ocurre una excepción, realizamos el rollback
	            connection.rollback();
	            System.out.println("Error en la inserción del préstamo: " + e.getMessage());
	            e.printStackTrace();
	            return false;
	        }
	    } catch (SQLException e) {
	        // Manejo de excepciones a nivel de conexión o general
	        e.printStackTrace();
	        return false;
	    }

	    return isInsertExitoso;
	}

	@Override
	public ArrayList<Prestamo> ListPrestamosPedidos() {
		try {
	        Class.forName("com.mysql.jdbc.Driver");
	        System.out.println("Driver cargado exitosamente.");
	    } catch (ClassNotFoundException e) {
	        System.out.println("Error al cargar el driver: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    ArrayList<Prestamo> ListaPrestamos = new ArrayList<Prestamo>();
	    
	    Connection con = Conexion.getConexion().getSQLConexion();
	    
	    if (con == null) {
	        System.out.println("No se pudo obtener la conexión a la base de datos.");
	        return ListaPrestamos;
	    } else {
	        System.out.println("Conexión a la base de datos establecida.");
	    }
	    
	    try (PreparedStatement ps = con.prepareStatement(ListarTodosPrestamos);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            Prestamo pre = new Prestamo();
	            pre.setId(rs.getInt("id"));
	            pre.setIdCliente(rs.getInt("IdCliente"));
	            pre.setIdCuenta(rs.getInt("IdCuenta"));
	            pre.setImporteCliente(rs.getFloat("ImportePedidoCliente"));
	            pre.setFechaAlta(rs.getDate("FechaAlta"));
	           // pre.setImpxmes(rs.getFloat("ImportePagarXmes"));
	            pre.setCantCuo(rs.getInt("CantidadCuotas"));	     	          
	            ListaPrestamos.add(pre);            
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Error al ejecutar la consulta: " + e.getMessage());
	        e.printStackTrace();
	    }    
	    return ListaPrestamos;
	}

	@Override
	public boolean confirmacionPrestamo(int idPrestamo) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    boolean isUpdateExitoso = false;

	    try {
	        connection = Conexion.getConexion().getSQLConexion(); 
	        if (connection == null) {
	            System.out.println("No se pudo obtener la conexión a la base de datos.");
	            return false;
	        }
	        connection.setAutoCommit(false);

	        // Actualizar estado de confirmación del préstamo
	        statement = connection.prepareStatement(AprobarPrestamo);
	        statement.setInt(1, 1); // 1: Aprobado, 0: Rechazado
	        statement.setInt(2, idPrestamo);

	        int rowsAffected = statement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Confirmación del préstamo actualizada correctamente.");
	            isUpdateExitoso = true;

	        } else {
	            System.out.println("No se actualizó ninguna fila. Verifica si el ID del préstamo es válido.");
	            connection.rollback(); // Revertir si no se actualizó el préstamo
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error durante la actualización de la confirmación del préstamo.");
	        try {
	            if (connection != null) {
	                connection.rollback(); // Rollback en caso de error
	            }
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    } finally {
	        try {
	            if (statement != null) {
	                statement.close(); // Cerrar statement
	            }
	            if (connection != null) {
	                connection.setAutoCommit(true); // Restaurar el autocommit
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return isUpdateExitoso;
	}
	
	
	public boolean denegarPrestamo(int idPrestamo) {
		 Connection connection = null;
		    PreparedStatement statement = null;
		    boolean isUpdateExitoso = false;

		    try {
		        connection = Conexion.getConexion().getSQLConexion(); 
		        if (connection == null) {
		            System.out.println("No se pudo obtener la conexión a la base de datos.");
		            return false;
		        }
		        connection.setAutoCommit(false);

		        // Actualizar estado de negacion del préstamo
		        statement = connection.prepareStatement(DenegarPrestamo);
		        statement.setInt(1, 2); // 1: Aprobado, 2: Rechazado
		        statement.setInt(2, idPrestamo);
		        
		        
		        System.out.println("Ejecutando la consulta SQL...");

		        int rowsAffected = statement.executeUpdate();
		        
		        System.out.println("Filas afectadas: " + rowsAffected);


		        if (rowsAffected > 0) {
		            System.out.println("Negacion del préstamo actualizada correctamente.");
		            isUpdateExitoso = true;

		        } else {
		            System.out.println("No se actualizó ninguna fila. Verifica si el ID del préstamo es válido.");
		            connection.rollback(); // Revertir si no se actualizó el préstamo
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        System.out.println("Error durante la actualización de la confirmación del préstamo.");
		        try {
		            if (connection != null) {
		                connection.rollback(); // Rollback en caso de error
		            }
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		    } finally {
		        try {
		            if (statement != null) {
		                statement.close(); // Cerrar statement
		            }
		            if (connection != null) {
		                connection.setAutoCommit(true); // Restaurar el autocommit
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return isUpdateExitoso;

	}
	
	public float obtenerImportePrestamo(int idPrestamo) {
	    float importe = 0;
	    Connection connection = Conexion.getConexion().getSQLConexion();

	    try (PreparedStatement statement = connection.prepareStatement(ObtenerImportePrestamo)) {
	        statement.setInt(1, idPrestamo);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                importe = resultSet.getFloat("ImportePedidoCliente");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Se maneja el error de SQL aquí
	    }

	    return importe;
	}

	private int obtenerIdCuentaPrestamo(int idPrestamo, Connection connection) throws SQLException {
	    try (PreparedStatement statement = connection.prepareStatement(ObtenerIdCuentaPrestamo)) {
	        statement.setInt(1, idPrestamo);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getInt("IdCuenta");
	            }
	        }
	    }
	    return 0;
	}
	
	@Override
	public ArrayList<Prestamo> ListPrestamosPedidosAutorizados() {
		try {
	        Class.forName("com.mysql.jdbc.Driver");
	        System.out.println("Driver cargado exitosamente.");
	    } catch (ClassNotFoundException e) {
	        System.out.println("Error al cargar el driver: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    ArrayList<Prestamo> PretAut = new ArrayList<Prestamo>();
	    
	    Connection con = Conexion.getConexion().getSQLConexion();
	    
	    if (con == null) {
	        System.out.println("No se pudo obtener la conexión a la base de datos.");
	        return PretAut;
	    } else {
	        System.out.println("Conexión a la base de datos establecida.");
	    }
	    
	    try (PreparedStatement ps = con.prepareStatement(ListarPrestamosPedidosAutorizados);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            Prestamo pre = new Prestamo();
	            pre.setId(rs.getInt("id"));
	            pre.setIdCliente(rs.getInt("IdCliente"));
	            pre.setImporteCliente(rs.getFloat("ImportePedidoCliente"));
	            pre.setFechaAlta(rs.getDate("FechaAlta"));
	            pre.setCantCuo(rs.getInt("CantidadCuotas"));
	            pre.setconfimacion(rs.getInt("confirmacion"));          
	            PretAut.add(pre);            
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Error al ejecutar la consulta: " + e.getMessage());
	        e.printStackTrace();
	    }	    
	    return PretAut;
	}	
	
	@Override
	public ArrayList<Prestamo> obtenerPrestamosEnEspera(int idCliente) {
		ArrayList<Prestamo> PretAut = new ArrayList<>();
		
	    try (Connection con = Conexion.getConexion().getSQLConexion();
	         PreparedStatement ps = con.prepareStatement(ObtenerPrestamosPendientes)) {
	        
	        ps.setInt(1, idCliente); 
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Prestamo pre = new Prestamo();
	                pre.setId(rs.getInt("Id"));
	                pre.setIdCliente(rs.getInt("IdCliente"));
	                pre.setImporteCliente(rs.getFloat("ImportePedidoCliente"));
	                pre.setFechaAlta(rs.getDate("FechaAlta"));
	                pre.setCantCuo(rs.getInt("CantidadCuotas"));                
	                pre.setconfimacion(rs.getInt("confirmacion"));
	                PretAut.add(pre);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al ejecutar la consulta: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return PretAut;
	}	

	@Override
	public ArrayList<Prestamo> filtrarClienteXImporte(String orden) {
		ArrayList<Prestamo> lista = new ArrayList<>();
	    String query = "SELECT * FROM prestamo WHERE confirmacion = 0 ORDER BY ImportePedidoCliente " 
	        + (orden.equalsIgnoreCase("Mayor") ? "DESC" : "ASC");

	    Connection conexion = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;

	    try {
	        System.out.println("Consulta generada: " + query);
	        conexion = Conexion.getConexion().getSQLConexion();
	        if (conexion == null) {
	            System.err.println("No se pudo establecer la conexión con la base de datos.");
	            return lista;
	        }

	        statement = conexion.prepareStatement(query);
	        rs = statement.executeQuery();

	        while (rs.next()) {
	            Prestamo pre = new Prestamo();
	            pre.setId(rs.getInt("id"));
	            pre.setIdCliente(rs.getInt("IdCliente"));
	            pre.setImporteCliente(rs.getFloat("ImportePedidoCliente"));
	            pre.setFechaAlta(rs.getDate("FechaAlta"));
	            pre.setCantCuo(rs.getInt("CantidadCuotas"));
	            pre.setconfimacion(rs.getInt("confirmacion"));
	            lista.add(pre);
	        }

	        if (lista.isEmpty()) {
	            System.out.println("No se encontraron resultados para la consulta.");
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (statement != null) statement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return lista;
	}

	@Override
	public ArrayList<Prestamo> filtrarClienteXImporteConfirmado(String orden) {
		ArrayList<Prestamo> lista = new ArrayList<>();
	    String query = "SELECT * FROM prestamo where confirmacion = 1 ORDER BY ImportePedidoCliente " 
	        + (orden.equalsIgnoreCase("Mayor") ? "DESC" : "ASC");

	    Connection conexion = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;

	    try {
	        System.out.println("Consulta generada: " + query);
	        conexion = Conexion.getConexion().getSQLConexion();
	        if (conexion == null) {
	            System.err.println("No se pudo establecer la conexión con la base de datos.");
	            return lista;
	        }

	        statement = conexion.prepareStatement(query);
	        rs = statement.executeQuery();

	        while (rs.next()) {
	            Prestamo pre = new Prestamo();
	            pre.setId(rs.getInt("id"));
	            pre.setIdCliente(rs.getInt("IdCliente"));
	            pre.setImporteCliente(rs.getFloat("ImportePedidoCliente"));
	            pre.setFechaAlta(rs.getDate("FechaAlta"));
	          //  pre.setImpxmes(rs.getFloat("ImportePagarXmes"));
	            pre.setCantCuo(rs.getInt("CantidadCuotas"));
	            pre.setconfimacion(rs.getInt("confirmacion"));
	            lista.add(pre);
	        }

	        if (lista.isEmpty()) {
	            System.out.println("No se encontraron resultados para la consulta.");
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (statement != null) statement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return lista;		
	}

	@Override
	public boolean CargarPrestamoEnCuenta(int IdCuenta, float monto) {		 
			   Connection connection = null;
			   PreparedStatement statement = null;
			   boolean isInsertExitoso = false;
			   
			   try {
			       connection = Conexion.getConexion().getSQLConexion();
			       if (connection == null) {
			           System.out.println("No se pudo obtener la conexión a la base de datos.");
			           return false;
			       }
			       connection.setAutoCommit(false); 
			
			       statement = connection.prepareStatement(CargarPrestamoEnCuenta);
			       statement.setFloat(1, monto);
			       statement.setFloat(2, IdCuenta);
			    			
			       int rowsAffected = statement.executeUpdate();
			       
			       if (rowsAffected > 0) {
			           connection.commit(); 
			           System.out.println("El MONTO del prestamo se ha insertado correctamente. Filas afectadas: " + rowsAffected); 
			           isInsertExitoso = true;
			       }
			
			   } catch (SQLException e) {
			       e.printStackTrace();
			       System.out.println("Error durante la inserción.");
			       if (connection != null) {
			           try {
			               connection.rollback(); 
			           } catch (SQLException e1) {
			               e1.printStackTrace();
			           }
			       }
			   } finally {
			     
			       try {
			           if (statement != null) {
			               statement.close();
			           }
			           if (connection != null) {
			               connection.setAutoCommit(true); 
			               connection.close();
			           }
			       } catch (SQLException e) {
			           e.printStackTrace();
			       }
			   }
			   return isInsertExitoso;
		}
	
	@Override
	public double obtenerTotalPrestamosConfirmados(int idCliente) {
	    double totalPrestamos = 0;
	    Connection con = null;
	    
	    try {    	
	        con = Conexion.getConexion().getSQLConexion();

	        if (con == null || con.isClosed()) {
	            System.out.println("Conexión cerrada, intentando reconectar...");
	            con = Conexion.getConexion().getSQLConexion();  // Reconectar si está cerrada
	        }

	        String sql = "SELECT SUM(ImportePedidoCliente) AS TotalPrestamos FROM prestamo WHERE IdCliente = ? AND confirmacion = 1";
	        try (PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setInt(1, idCliente);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    totalPrestamos = rs.getDouble("TotalPrestamos");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al obtener el total de préstamos confirmados: " + e.getMessage());
	        e.printStackTrace();
	    } 

	    return totalPrestamos;
	}	
	
	@Override
	public List<Prestamo> obtenerPrestamosConfirmados(int idCliente) {
	    List<Prestamo> prestamos = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        con = Conexion.getConexion().getSQLConexion();
	        if (con == null || con.isClosed()) {
	            System.out.println("Conexión cerrada, intentando reconectar...");
	            con = Conexion.getConexion().getSQLConexion();
	        }

	        ps = con.prepareStatement(ObtenerPrestamosConfirmados);
	        ps.setInt(1, idCliente);

	        System.out.println("Ejecutando SQL: " + ObtenerPrestamosConfirmados); 
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Prestamo prestamo = new Prestamo();
	            prestamo.setId(rs.getInt("Id"));
	            prestamo.setIdCliente(rs.getInt("IdCliente"));
	            prestamo.setIdCuenta(rs.getInt("IdCuenta"));
	            prestamo.setImporteCliente(rs.getFloat("ImporteCliente"));
	            prestamo.setFechaAlta(rs.getDate("FechaAlta"));
	            prestamo.setCantCuo(rs.getInt("CantidadCuotas"));
	            prestamo.setconfimacion(rs.getInt("confirmacion"));

	            prestamos.add(prestamo);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error al obtener los préstamos.");
	    } finally {
	    	
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return prestamos;
	}

	@Override
	public List<Cuota> obtenerCuotas(int idCliente, int idPrestamo) {
	    List<Cuota> cuotas = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    // Construcción de la consulta SQL de forma estática
	    String ObtenerCuotasDePrestamo = "SELECT cu.Id, cu.IdPrestamo, cu.NumeroCuota, cu.Monto, cu.FechaPago, cu.estaPagada "
	                                     + "FROM cuota cu "
	                                     + "JOIN prestamo p ON cu.IdPrestamo = p.Id "
	                                     + "WHERE p.IdCliente = ? AND p.confirmacion = 1 ";

	    // Si idPrestamo es diferente de 0, añadimos la condición correspondiente
	    if (idPrestamo != 0) {
	        ObtenerCuotasDePrestamo += "AND p.Id = ? ";
	    }

	    // Agregamos la ordenación al final de la consulta
	    ObtenerCuotasDePrestamo += "ORDER BY cu.FechaPago, cu.NumeroCuota";

	    try {
	        con = Conexion.getConexion().getSQLConexion();

	        if (con == null || con.isClosed()) {
	            System.out.println("Conexión cerrada, intentando reconectar...");
	            con = Conexion.getConexion().getSQLConexion();
	        }

	        // Preparar la consulta
	        ps = con.prepareStatement(ObtenerCuotasDePrestamo);
	        ps.setInt(1, idCliente);  // Siempre se pasa el idCliente

	        // Si idPrestamo es diferente de 0, entonces agregamos el parámetro para idPrestamo
	        if (idPrestamo != 0) {
	            ps.setInt(2, idPrestamo);
	        }

	        System.out.println("Ejecutando SQL: " + ObtenerCuotasDePrestamo); // Log de la consulta
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Cuota cuota = new Cuota();
	            cuota.setId(rs.getInt("Id"));
	            cuota.setIdPrestamo(rs.getInt("IdPrestamo"));
	            cuota.setNumeroCuota(rs.getInt("NumeroCuota"));
	            cuota.setMonto(rs.getFloat("Monto"));
	            cuota.setFechaPago(rs.getDate("FechaPago"));
	            cuota.setPagada(rs.getBoolean("estaPagada"));

	            cuotas.add(cuota);

	            System.out.println("Cuota obtenida: " + cuota.getId() + ", " + cuota.getNumeroCuota() + ", Monto: " + cuota.getMonto());
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error al obtener las cuotas.");
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    if (cuotas.isEmpty()) {
	        System.out.println("No se encontraron cuotas para el cliente con ID: " + idCliente);
	    }

	    return cuotas;
	}

	@Override
	public boolean realizarPagoCuota(int cuotaId, int cuentaId, float monto) {
	    Connection conn = null;
	    PreparedStatement psSaldo = null;
	    PreparedStatement psUpdateCuenta = null;
	    PreparedStatement psInsertMovimiento = null;
	    PreparedStatement psUpdateCuota = null;

	    try {
	        conn = Conexion.getConexion().getSQLConexion();

	        if (conn == null || conn.isClosed()) {
	            System.out.println("Conexión cerrada, intentando reconectar...");
	            conn = Conexion.getConexion().getSQLConexion();
	        }

	        conn.setAutoCommit(false); 

	        String sqlSaldo = "SELECT Saldo FROM cuenta WHERE Id = ?";
	        psSaldo = conn.prepareStatement(sqlSaldo);
	        psSaldo.setInt(1, cuentaId);
	        ResultSet rsSaldo = psSaldo.executeQuery();

	        if (rsSaldo.next()) {
	            float saldoActual = rsSaldo.getFloat("Saldo");
	            if (saldoActual < monto) {
	                throw new Exception("Saldo insuficiente para realizar el pago.");
	            }
	        } else {
	            throw new Exception("La cuenta no existe.");
	        }

	        String sqlUpdateCuenta = "UPDATE cuenta SET Saldo = Saldo - ? WHERE Id = ?";
	        psUpdateCuenta = conn.prepareStatement(sqlUpdateCuenta);
	        psUpdateCuenta.setFloat(1, monto);
	        psUpdateCuenta.setInt(2, cuentaId);
	        psUpdateCuenta.executeUpdate();

	        String sqlInsertMovimiento = "INSERT INTO movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) " +
	                                      "VALUES (?, NOW(), ?, ?, ?)";
	        psInsertMovimiento = conn.prepareStatement(sqlInsertMovimiento);
	        psInsertMovimiento.setInt(1, 3); // 3 representa el pago de préstamo
	        psInsertMovimiento.setFloat(2, -monto); 
	        psInsertMovimiento.setInt(3, cuentaId);
	        psInsertMovimiento.setString(4, "Pago de préstamo (Cuota ID: " + cuotaId + ")");
	        psInsertMovimiento.executeUpdate();

	        String sqlUpdateCuota = "UPDATE cuota SET estaPagada = 1 WHERE Id = ?";
	        psUpdateCuota = conn.prepareStatement(sqlUpdateCuota);
	        psUpdateCuota.setInt(1, cuotaId);
	        psUpdateCuota.executeUpdate();

	        conn.commit(); 
	        return true;

	    } catch (Exception e) {
	        if (conn != null) {
	            try {
	                conn.rollback(); // 
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        }
	        e.printStackTrace();
	        return false;

	    } finally {
	        try {
	            if (psSaldo != null) psSaldo.close();
	            if (psUpdateCuenta != null) psUpdateCuenta.close();
	            if (psInsertMovimiento != null) psInsertMovimiento.close();
	            if (psUpdateCuota != null) psUpdateCuota.close();
	            if (conn != null) conn.close();
	        } catch (SQLException closeEx) {
	            closeEx.printStackTrace();
	        }
	    }
	}
	
	@Override
	public boolean actualizarEstadoCuota(int cuotaId, boolean estaPagada) {
	    boolean actualizado = false;
	    try (Connection conn = Conexion.getConexion().getSQLConexion();
	         PreparedStatement ps = conn.prepareStatement("UPDATE cuota SET estaPagada = ? WHERE Id = ?")) {

	        ps.setBoolean(1, estaPagada);
	        ps.setInt(2, cuotaId);

	        actualizado = ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error al actualizar el estado de la cuota.");
	    }
	    return actualizado;
	}


	@Override
	public double obtenerSumaCuotasPendientes(int idCliente) {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    double montoTotalPendiente = 0;

	    try {
	        conn = Conexion.getConexion().getSQLConexion();

	        if (conn == null || conn.isClosed()) {
	            System.out.println("Conexión cerrada, intentando reconectar...");
	            conn = Conexion.getConexion().getSQLConexion();
	        }

	        String sql = "SELECT SUM(cu.Monto) AS MontoTotalPendiente " +
	                     "FROM cuota cu " +
	                     "JOIN prestamo p ON cu.IdPrestamo = p.Id " +
	                     "WHERE p.IdCliente = ? AND p.confirmacion = 1 AND cu.estaPagada = 0";
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, idCliente);

	        rs = ps.executeQuery();
	        if (rs.next()) {
	            montoTotalPendiente = rs.getDouble("MontoTotalPendiente");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        montoTotalPendiente = 0;

	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException closeEx) {
	            closeEx.printStackTrace();
	        }
	    }
	    return montoTotalPendiente;
	}


	@Override
	public boolean actualizarConfirmacionPrestamo(int idPrestamo, int confirmacion) {
		// TODO Auto-generated method stub
		return false;
	}
}
