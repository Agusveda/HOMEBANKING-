package daoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entidades.Cliente;
import Entidades.Cuenta;
import Entidades.Cuota;
import Entidades.Movimiento;
import Entidades.Nacionalidades;
import Entidades.Prestamo;
import dao.MovimientoDao;

public class MovimientoDaoImp implements MovimientoDao {
	private static final String ListarMovimientosPorCuenta = "Select * from movimiento where IdCuenta = ?";
	private static final String IngresarMovimientoPositivo = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 4 , CURDATE() , ? , ? , ?)";
	private static final String IngresarMovimientoNegativo = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 4 , CURDATE() , -? , ? , ?)";
	private static final String ModificarCuentaPositivo = "update cuenta SET Saldo = Saldo + ? where Id = ?";
	private static final String ModificarCuentaNegativo = "update cuenta SET Saldo = Saldo - ? where Id = ?";
	private static final String ObtenerIdCuentaPorCBU = "select Id from cuenta where CBU = ? and Activo = 1";
	private static final String ObtenerIdCuentaPorIdCliente = "select Id from cuenta where IdCliente = ? and Activo = 1";
	private static final String TraerCuentasPorIdCliente = "select * from cuenta where IdCliente = ? and Activo = 1 ";
	private static final String ObtenerSaldoPorIdCuenta = "select * from cuenta where Id = ? and Activo = 1 ";
	private static final String ExisteCBU = "SELECT * FROM cuenta WHERE CBU = ? and Activo = 1";
	// BASE - private static final String InsertarPrestamo= "INSERT INTO prestamo (IdCliente, IdCuenta ,ImportePedidoCliente, FechaAlta, PlazoPago, ImportePagarXmes, CantidadCuotas,confirmacion) " + "VALUES (?,?, ?, NOW(), ?, ?, ?,?)";
	private static final String InsertarPrestamo = "INSERT INTO prestamo (IdCliente, IdCuenta ,ImportePedidoCliente, FechaAlta, PlazoPago, ImportePagarXmes, CantidadCuotas, confirmacion) " + 
            "VALUES (?,?, ?, NOW(), ?, ?, ?, ?)";
	
	private static final String CargarPrestamoEnCuenta = "update cuenta set saldo = saldo + ? where Id = ? ";
	
	///REPORTES
	private static final String ReporteMovimientos = "SELECT SUM(Importe) AS total FROM movimiento WHERE FechaMovimiento BETWEEN ? AND ? AND Importe > 0 GROUP BY TipoMovimiento = ?";
	private static final String ReporteIngresoMovimiento = "SELECT SUM(m.Importe) AS total FROM movimiento m inner join cuenta c on c.Id = m.idCuenta inner join cliente cli on cli.Id = c.IdCliente WHERE cli.DNI = ? and m.Importe not like '%-%' and c.Activo = 1"; 
	private static final String ReporteEgresoMovimiento = "SELECT SUM(m.Importe) AS total FROM movimiento m inner join cuenta c on c.Id = m.idCuenta inner join cliente cli on cli.Id = c.IdCliente WHERE cli.DNI = ? and m.Importe  like '%-%' and c.Activo = 1";
		

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

			// Inserci�n en la tabla Cliente con generaci�n de ID
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

					// Inserci�n en Cuenta
					System.out.println("Preparando declaraci�n de inserci�n para cuenta.");


					statementBajaSueldo = conexion.prepareStatement(ModificarCuentaNegativo);
					statementBajaSueldo.setFloat(1, movi.getImporte());
					statementBajaSueldo.setInt(2, idCue);

					if (statementBajaSueldo.executeUpdate() > 0) {
						System.out.println("Inserci�n en Cuenta negativa exitosa.");

						statementCuenta = conexion.prepareStatement(ModificarCuentaPositivo);
						statementCuenta.setFloat(1, movi.getImporte());
						statementCuenta.setInt(2, movi.getIdCuenta());
						// Ejecutar la inserci�n de Usuario
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
/*
	@Override
	public ArrayList<Cuenta> TraeCuentasPorIdCliente(int idCliente) {
		ArrayList<Cuenta> CuentasCliente = new ArrayList<>();
		System.out.println("Buscando cuentas para IdCliente: " + idCliente);

		String query = TraerCuentasPorIdCliente;
		Connection con = Conexion.getConexion().getSQLConexion();

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idCliente);
			ResultSet rs = ps.executeQuery();

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

			if (CuentasCliente.isEmpty()) {
				System.out.println("No se encontraron cuentas para IdCliente: " + idCliente);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error durante la consulta de cuentas.");
		}
		return CuentasCliente;
	}
*/
	
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
	
	
	///SECCION DE VALIDACIONES
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
/* sin cuotas
	@Override
	public boolean insertarPrestamo(Prestamo prestamo) {
		 
		   Connection connection = null;
		   PreparedStatement statement = null;
		   boolean isInsertExitoso = false;
		   
		   try {
		       connection = Conexion.getConexion().getSQLConexion();
		       if (connection == null) {
		           System.out.println("No se pudo obtener la conexi�n a la base de datos.");
		           return false;
		       }
		       connection.setAutoCommit(false); 
		
		       statement = connection.prepareStatement(InsertarPrestamo);
		       statement.setInt(1, prestamo.getIdCliente());
		       statement.setInt(2,prestamo.getIdCuenta());
		       statement.setFloat(3, prestamo.getImporteCliente());
		       statement.setInt(4, prestamo.getPlazoPago());
		       statement.setFloat(5, prestamo.getImpxmes());
		       statement.setInt(6, prestamo.getCantCuo());
		       statement.setInt(7,0);
		   
		
		       int rowsAffected = statement.executeUpdate();
		       
		       if (rowsAffected > 0) {
		           connection.commit(); 
		           System.out.println("El pr�stamo se ha insertado correctamente. Filas afectadas: " + rowsAffected); 
		           isInsertExitoso = true;
		       }
		
		   } catch (SQLException e) {
		       e.printStackTrace();
		       System.out.println("Error durante la inserci�n.");
		       if (connection != null) {
		           try {
		               connection.rollback(); 
		           } catch (SQLException e1) {
		               e1.printStackTrace();
		           }
		       }
		   } 
		   return isInsertExitoso;
	}
	
	*/
	@Override
	public boolean insertarPrestamo(Prestamo prestamo) {
	    boolean isInsertExitoso = false;

	    try (Connection connection = Conexion.getConexion().getSQLConexion()) {

	        if (connection == null) {
	            System.out.println("No se pudo obtener la conexi�n a la base de datos.");
	            return false;
	        }

	        connection.setAutoCommit(false); // Deshabilitar el auto commit para control manual de la transacci�n

	        // Utilizamos la consulta definida para insertar el pr�stamo
	        try (PreparedStatement statement = connection.prepareStatement(InsertarPrestamo, Statement.RETURN_GENERATED_KEYS)) {
	            // Asignamos los valores del objeto prestamo a los par�metros del PreparedStatement
	            statement.setInt(1, prestamo.getIdCliente());
	            statement.setInt(2, prestamo.getIdCuenta());
	            statement.setFloat(3, prestamo.getImporteCliente());
	            statement.setInt(4, prestamo.getPlazoPago());
	            statement.setFloat(5, prestamo.getImpxmes());
	            statement.setInt(6, prestamo.getCantCuo());
	            statement.setInt(7, 0); // Asignamos el valor de confirmacion (0 = pendiente, o el valor que corresponda)

	            int rowsAffected = statement.executeUpdate(); // Ejecutar la inserci�n del pr�stamo

	            if (rowsAffected > 0) {
	                ResultSet generatedKeys = statement.getGeneratedKeys();
	                if (generatedKeys.next()) {
	                    int idPrestamo = generatedKeys.getInt(1); // ID del pr�stamo reci�n insertado

	                    // Insertar las cuotas asociadas
	                    String insertarCuotaSQL = "INSERT INTO cuota (IdPrestamo, NumeroCuota, Monto, estaPagada, FechaPago) VALUES (?, ?, ?, ?, ?)";
	                    try (PreparedStatement statementCuota = connection.prepareStatement(insertarCuotaSQL)) {
	                        for (int i = 1; i <= prestamo.getCantCuo(); i++) {
	                            statementCuota.setInt(1, idPrestamo); // idPrestamo
	                            statementCuota.setInt(2, i); // NumeroCuota
	                            statementCuota.setFloat(3, prestamo.getImporteCliente() / prestamo.getCantCuo()); // Monto
	                            statementCuota.setInt(4, 0); // estaPagada (0: pendiente)
	                            statementCuota.setDate(5, java.sql.Date.valueOf(LocalDate.now().plusMonths(i))); // FechaPago (fecha de vencimiento)

	                            statementCuota.addBatch(); // A�adir la cuota al batch
	                        }

	                        int[] cuotasAfectadas = statementCuota.executeBatch();
	                        if (cuotasAfectadas.length == prestamo.getCantCuo()) {
	                            connection.commit(); // Si todo se insert� correctamente, realizar commit
	                            System.out.println("El pr�stamo y sus cuotas se han insertado correctamente.");
	                            isInsertExitoso = true;
	                        } else {
	                            connection.rollback(); // Si hubo un error en las cuotas, hacer rollback
	                            System.out.println("Error al insertar las cuotas. Se realiz� un rollback.");
	                        }
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            connection.rollback();
	            System.out.println("Error durante la inserci�n del pr�stamo o las cuotas. Se realiz� un rollback.");
	            e.printStackTrace();
	        }

	    } catch (SQLException e) {
	        System.out.println("Error al obtener la conexi�n a la base de datos o al cerrar los recursos.");
	        e.printStackTrace();
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
	    
	    String query = "SELECT Id, IdCliente,IdCuenta, ImportePedidoCliente,FechaAlta,ImportePagarXmes,CantidadCuotas,confirmacion FROM prestamo where confirmacion = 0 order by id desc";
	    
	    Connection con = Conexion.getConexion().getSQLConexion();
	    
	    if (con == null) {
	        System.out.println("No se pudo obtener la conexi�n a la base de datos.");
	        return ListaPrestamos;
	    } else {
	        System.out.println("Conexi�n a la base de datos establecida.");
	    }
	    
	    try (PreparedStatement ps = con.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            Prestamo pre = new Prestamo();
	            pre.setId(rs.getInt("id"));
	            pre.setIdCliente(rs.getInt("IdCliente"));
	            pre.setIdCuenta(rs.getInt("IdCuenta"));
	            pre.setImporteCliente(rs.getFloat("ImportePedidoCliente"));
	            pre.setFechaAlta(rs.getDate("FechaAlta"));
	            pre.setImpxmes(rs.getFloat("ImportePagarXmes"));
	            pre.setCantCuo(rs.getInt("CantidadCuotas"));
	        
	          
	            ListaPrestamos.add(pre);
	            
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Error al ejecutar la consulta: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    
	    return ListaPrestamos;
	}
	
	
	public boolean actualizarConfirmacionPrestamo(int idPrestamo, int confirmacion) {
		   Connection connection = null;
		    PreparedStatement statement = null;
		    boolean isUpdateExitoso = false;
		    
		    try {
		        connection = Conexion.getConexion().getSQLConexion(); 
		        if (connection == null) {
		            System.out.println("No se pudo obtener la conexi�n a la base de datos.");
		            return false;
		        }

		        System.out.println("Actualizando pr�stamo con ID: " + idPrestamo);
		        System.out.println("Nuevo estado de confirmaci�n: " + confirmacion);

		        String updateQuery = "UPDATE prestamo SET confirmacion = ? WHERE id = ?";
		        statement = connection.prepareStatement(updateQuery);
		        statement.setInt(1, confirmacion);
		        statement.setInt(2, idPrestamo);

		        int rowsAffected = statement.executeUpdate();

		        if (rowsAffected > 0) {
		            System.out.println("El estado del pr�stamo se ha actualizado correctamente. Filas afectadas: " + rowsAffected);
		            isUpdateExitoso = true;
		        } else {
		            System.out.println("No se actualiz� ninguna fila. Verifica si el ID de pr�stamo es v�lido.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        System.out.println("Error durante la actualizaci�n.");
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
		               
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return isUpdateExitoso;
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
	    
	    String query = "SELECT Id, IdCliente, ImportePedidoCliente,FechaAlta,ImportePagarXmes,CantidadCuotas,confirmacion FROM prestamo where confirmacion = 1 ";
	    
	    Connection con = Conexion.getConexion().getSQLConexion();
	    
	    if (con == null) {
	        System.out.println("No se pudo obtener la conexi�n a la base de datos.");
	        return PretAut;
	    } else {
	        System.out.println("Conexi�n a la base de datos establecida.");
	    }
	    
	    try (PreparedStatement ps = con.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            Prestamo pre = new Prestamo();
	            pre.setId(rs.getInt("id"));
	            pre.setIdCliente(rs.getInt("IdCliente"));
	            pre.setImporteCliente(rs.getFloat("ImportePedidoCliente"));
	            pre.setFechaAlta(rs.getDate("FechaAlta"));
	            pre.setImpxmes(rs.getFloat("ImportePagarXmes"));
	            pre.setCantCuo(rs.getInt("CantidadCuotas"));
	            pre.setconfimacion(rs.getBoolean("confirmacion"));
	          
	            PretAut.add(pre);
	            
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
	    String query = "SELECT * FROM prestamo ORDER BY ImportePedidoCliente " 
	        + (orden.equalsIgnoreCase("Mayor") ? "DESC" : "ASC");

	    Connection conexion = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;

	    try {
	        System.out.println("Consulta generada: " + query);
	        conexion = Conexion.getConexion().getSQLConexion();
	        if (conexion == null) {
	            System.err.println("No se pudo establecer la conexi�n con la base de datos.");
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
	            pre.setImpxmes(rs.getFloat("ImportePagarXmes"));
	            pre.setCantCuo(rs.getInt("CantidadCuotas"));
	            pre.setconfimacion(rs.getBoolean("confirmacion"));
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
	            System.err.println("No se pudo establecer la conexi�n con la base de datos.");
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
	            pre.setImpxmes(rs.getFloat("ImportePagarXmes"));
	            pre.setCantCuo(rs.getInt("CantidadCuotas"));
	            pre.setconfimacion(rs.getBoolean("confirmacion"));
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
			           System.out.println("No se pudo obtener la conexi�n a la base de datos.");
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
			       System.out.println("Error durante la inserci�n.");
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
	
	@Override
	public double obtenerTotalPrestamosConfirmados(int idCliente) {
	    double totalPrestamos = 0;
	    Connection con = null;
	    
	    try {
	    	
	        con = Conexion.getConexion().getSQLConexion();

	        if (con == null || con.isClosed()) {
	            System.out.println("Conexi�n cerrada, intentando reconectar...");
	            con = Conexion.getConexion().getSQLConexion();  // Reconectar si est� cerrada
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
	        System.err.println("Error al obtener el total de pr�stamos confirmados: " + e.getMessage());
	        e.printStackTrace();
	    } 

	    return totalPrestamos;
	}	
	
	/*
	@Override
	public List<Prestamo> obtenerPrestamosConfirmados(int idCliente) {
	    List<Prestamo> prestamos = new ArrayList<>();
	    Connection con = null;

	    try {

	        con = Conexion.getConexion().getSQLConexion();


	        if (con == null || con.isClosed()) {
	            System.out.println("Conexi�n cerrada, intentando reconectar...");
	            con = Conexion.getConexion().getSQLConexion(); 
	        }

	        String sql = "SELECT Id, IdCliente, IdCuenta, ImportePedidoCliente AS ImporteCliente, FechaAlta, PlazoPago, ImportePagarXmes AS Impxmes, CantidadCuotas AS cantCuo, confirmacion AS confimacion "
	                   + "FROM prestamo WHERE IdCliente = ? AND confirmacion = 1";

	        try (PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setInt(1, idCliente);

	            // Ejecuta la consulta y obtiene los resultados
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Prestamo prestamo = new Prestamo();
	                    prestamo.setId(rs.getInt("Id"));
	                    prestamo.setIdCliente(rs.getInt("IdCliente"));
	                    prestamo.setIdCuenta(rs.getInt("IdCuenta"));
	                    prestamo.setImporteCliente(rs.getFloat("ImporteCliente"));
	                    prestamo.setFechaAlta(rs.getDate("FechaAlta"));
	                    prestamo.setPlazoPago(rs.getInt("PlazoPago"));
	                    prestamo.setImpxmes(rs.getFloat("Impxmes"));
	                    prestamo.setCantCuo(rs.getInt("cantCuo"));
	                    prestamo.setconfimacion(rs.getBoolean("confimacion"));

	                    prestamos.add(prestamo);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al obtener los pr�stamos confirmados: " + e.getMessage());
	        e.printStackTrace();
	    } 

	    return prestamos;
	}

	*/
	@Override
	public List<Prestamo> obtenerPrestamosConfirmados(int idCliente) {
	    List<Prestamo> prestamos = new ArrayList<>();
	    StringBuilder sql = new StringBuilder();

	
	    sql.append("SELECT p.Id, p.IdCliente, p.IdCuenta, p.ImportePedidoCliente AS ImporteCliente, p.FechaAlta, p.PlazoPago, ")
	       .append("p.ImportePagarXmes, p.CantidadCuotas, p.confirmacion ")
	       .append("FROM prestamo p ")
	       .append("WHERE p.IdCliente = ? AND p.confirmacion = 1");

	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        // Obtener la conexi�n a la base de datos
	        con = Conexion.getConexion().getSQLConexion();

	        // Verificar si la conexi�n est� cerrada y reconectar si es necesario
	        if (con == null || con.isClosed()) {
	            System.out.println("Conexi�n cerrada, intentando reconectar...");
	            con = Conexion.getConexion().getSQLConexion();
	        }

	        // Preparar la consulta con los par�metros
	        ps = con.prepareStatement(sql.toString());
	        ps.setInt(1, idCliente);  // Configurar el idCliente

	        // Ejecutar la consulta
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            // Crear objeto Prestamo y agregarlo a la lista
	            Prestamo prestamo = new Prestamo();
	            prestamo.setId(rs.getInt("Id"));
	            prestamo.setIdCliente(rs.getInt("IdCliente"));
	            prestamo.setIdCuenta(rs.getInt("IdCuenta"));
	            prestamo.setImporteCliente(rs.getFloat("ImporteCliente")); 
	            prestamo.setFechaAlta(rs.getDate("FechaAlta"));
	            prestamo.setPlazoPago(rs.getInt("PlazoPago"));
	            prestamo.setImpxmes(rs.getFloat("ImportePagarXmes"));
	            prestamo.setCantCuo(rs.getInt("CantidadCuotas"));
	            prestamo.setconfimacion(rs.getBoolean("confirmacion"));

	            prestamos.add(prestamo);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error al obtener los pr�stamos.");
	    } finally {
	        // Asegurarse de cerrar los recursos
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
	    StringBuilder sql = new StringBuilder();
	    
	    // Base de la consulta SQL
	    sql.append("SELECT cu.Id, cu.IdPrestamo, cu.NumeroCuota, cu.Monto, cu.FechaPago, cu.estaPagada ")
	       .append("FROM cuota cu ")
	       .append("JOIN prestamo p ON cu.IdPrestamo = p.Id ")
	       .append("WHERE p.IdCliente = ? AND p.confirmacion = 1 ");

	    // Si se especifica un idPrestamo, filtrar por ese pr�stamo
	    if (idPrestamo != 0) { 
	        sql.append("AND p.Id = ? ");
	    }

	    sql.append("ORDER BY cu.FechaPago, cu.NumeroCuota");  

	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        // Obtener la conexi�n a la base de datos
	        con = Conexion.getConexion().getSQLConexion();

	        // Verificar si la conexi�n est� cerrada y reconectar si es necesario
	        if (con == null || con.isClosed()) {
	            System.out.println("Conexi�n cerrada, intentando reconectar...");
	            con = Conexion.getConexion().getSQLConexion();
	        }

	        // Preparar la consulta con los par�metros
	        ps = con.prepareStatement(sql.toString());
	        ps.setInt(1, idCliente);  // Configurar el idCliente

	        if (idPrestamo != 0) {
	            ps.setInt(2, idPrestamo);  // Configurar el idPrestamo solo si se pasa
	        }

	        // Ejecutar la consulta
	        System.out.println("Ejecutando SQL: " + sql.toString()); // Log de la consulta
	        rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            // Crear objeto Cuota y agregarlo a la lista
	            Cuota cuota = new Cuota();
	            cuota.setId(rs.getInt("Id"));
	            cuota.setIdPrestamo(rs.getInt("IdPrestamo"));
	            cuota.setNumeroCuota(rs.getInt("NumeroCuota"));
	            cuota.setMonto(rs.getDouble("Monto"));
	            cuota.setFechaPago(rs.getDate("FechaPago"));
	            cuota.setPagada(rs.getBoolean("estaPagada"));

	            cuotas.add(cuota);

	            // Depuraci�n: Mostrar cuota obtenida
	            System.out.println("Cuota obtenida: " + cuota.getId() + ", " + cuota.getNumeroCuota() + ", Monto: " + cuota.getMonto());
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error al obtener las cuotas.");
	    } finally {
	        // Asegurarse de cerrar los recursos
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Si no se encontraron cuotas, log de la situaci�n
	    if (cuotas.isEmpty()) {
	        System.out.println("No se encontraron cuotas para el cliente con ID: " + idCliente);
	    }

	    return cuotas;
	}	
	
}

