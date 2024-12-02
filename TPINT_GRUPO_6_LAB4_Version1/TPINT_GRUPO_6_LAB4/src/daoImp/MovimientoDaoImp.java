package daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Cuenta;
import Entidades.Movimiento;
import Entidades.Nacionalidades;
import Entidades.Prestamo;
import dao.MovimientoDao;

public class MovimientoDaoImp implements MovimientoDao {
	private static final String ListarMovimientosPorCuenta = "Select * from movimiento where IdCuenta = ? and Activo=1";
	private static final String IngresarMovimientoPositivo = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 4 , CURDATE() , ? , ? , ?)";
	private static final String IngresarMovimientoNegativo = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 4 , CURDATE() , -? , ? , ?)";
	private static final String ModificarCuentaPositivo = "update cuenta SET Saldo = Saldo + ? where Id = ?";
	private static final String ModificarCuentaNegativo = "update cuenta SET Saldo = Saldo - ? where Id = ?";
	private static final String ObtenerIdCuentaPorCBU = "select Id from cuenta where CBU = ? and Activo = 1";
	private static final String ObtenerIdCuentaPorIdCliente = "select Id from cuenta where IdCliente = ? and Activo = 1";
	private static final String TraerCuentasPorIdCliente = "select * from cuenta where IdCliente = ? and Activo = 1 ";
	private static final String ObtenerSaldoPorIdCuenta = "select * from cuenta where Id = ? and Activo = 1 ";
	private static final String ExisteCBU = "SELECT * FROM cuenta WHERE CBU = ? and Activo = 1";
	private static final String InsertarPrestamo= "INSERT INTO prestamo (IdCliente, ImportePedidoCliente, FechaAlta, PlazoPago, ImportePagarXmes, CantidadCuotas,confirmacion) " + "VALUES (?, ?, NOW(), ?, ?, ?,?)";
	@Override
	public boolean insertar(Movimiento movi, int idCue) {
		System.out.println("Iniciando inserción de movimiento...");

		PreparedStatement statementMovimientoP = null;
		PreparedStatement statementMovimientoN = null;
		PreparedStatement statementCuenta = null;
		PreparedStatement statementBajaSueldo = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		if (conexion == null) {
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

			if (statementMovimientoP.executeUpdate() > 0) {
				System.out.println("Inserción en Movimiento Positivo exitoso.");

				// INSERCION NEGATIVA DE LA TABLA CLIENTE

				statementMovimientoN = conexion.prepareStatement(IngresarMovimientoNegativo);

				statementMovimientoN.setFloat(1, movi.getImporte());
				statementMovimientoN.setInt(2, idCue);
				statementMovimientoN.setString(3, movi.getDetalle());

				if (statementMovimientoN.executeUpdate() > 0) {
					System.out.println("Inserción en Movimiento negativo exitoso.");

					// Inserción en Cuenta
					System.out.println("Preparando declaración de inserción para cuenta.");


					statementBajaSueldo = conexion.prepareStatement(ModificarCuentaNegativo);
					statementBajaSueldo.setFloat(1, movi.getImporte());
					statementBajaSueldo.setInt(2, idCue);

					if (statementBajaSueldo.executeUpdate() > 0) {
						System.out.println("Inserción en Cuenta negativa exitosa.");

						statementCuenta = conexion.prepareStatement(ModificarCuentaPositivo);
						statementCuenta.setFloat(1, movi.getImporte());
						statementCuenta.setInt(2, movi.getIdCuenta());
						// Ejecutar la inserción de Usuario
						if (statementCuenta.executeUpdate() > 0) {
							System.out.println("Inserción en Cuenta exitosa.");

							conexion.commit();
							isInsertExitoso = true;
						}
					}
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
	public int ObtenerIdCuentaPorCBU(int CBU) {
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
// IMPOTANTE
	public float ObtenerSaldoPorIdCuenta(int idCue) {
		float saldo;
		Cuenta cuenta = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			if (conexion == null || conexion.isClosed()) {
				throw new SQLException("La conexión está cerrada.");
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
				throw new SQLException("La conexión está cerrada.");
			}

			statement = conexion.prepareStatement(ListarMovimientosPorCuenta);
			statement.setInt(1, idCue);
			rs = statement.executeQuery();

			if (rs.next()) {
				movi = new Movimiento();
				movi.setId(rs.getInt("Id"));
				movi.setTipoMovimiento(rs.getInt("TipoMovimiento"));
				movi.setFechaMovimiento(rs.getString("FechaMovimiento"));
				movi.setImporte(rs.getFloat("Importe"));
				movi.setIdCuenta(rs.getInt("IdCuenta"));
				movi.setDetalle(rs.getString("Dtealle"));
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
	            throw new SQLException("Conexión a la base de datos es nula");
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
	            // NO cierres la conexión aquí si usas un pool de conexiones
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return exists;
	}

	@Override
	public boolean insertarPrestamo(Prestamo prestamo) {
		 
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
		
		       statement = connection.prepareStatement(InsertarPrestamo);
		       statement.setInt(1, prestamo.getIdCliente());
		       statement.setFloat(2, prestamo.getImporteCliente());
		       statement.setInt(3, prestamo.getPlazoPago());
		       statement.setFloat(4, prestamo.getImpxmes());
		       statement.setInt(5, prestamo.getCantCuo());
		       statement.setInt(6,0);
		
		       int rowsAffected = statement.executeUpdate();
		       
		       if (rowsAffected > 0) {
		           connection.commit(); 
		           System.out.println("El préstamo se ha insertado correctamente. Filas afectadas: " + rowsAffected); 
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
	public ArrayList<Prestamo> ListPrestamosPedidos() {
		try {
	        Class.forName("com.mysql.jdbc.Driver");
	        System.out.println("Driver cargado exitosamente.");
	    } catch (ClassNotFoundException e) {
	        System.out.println("Error al cargar el driver: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    ArrayList<Prestamo> ListaPrestamos = new ArrayList<Prestamo>();
	    
	    String query = "SELECT Id, IdCliente, ImportePedidoCliente,FechaAlta,PlazoPago,ImportePagarXmes,CantidadCuotas,confirmacion FROM prestamo";
	    
	    Connection con = Conexion.getConexion().getSQLConexion();
	    
	    if (con == null) {
	        System.out.println("No se pudo obtener la conexión a la base de datos.");
	        return ListaPrestamos;
	    } else {
	        System.out.println("Conexión a la base de datos establecida.");
	    }
	    
	    try (PreparedStatement ps = con.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            Prestamo pre = new Prestamo();
	            pre.setId(rs.getInt("id"));
	            pre.setIdCliente(rs.getInt("IdCliente"));
	            pre.setImporteCliente(rs.getFloat("ImportePedidoCliente"));
	            pre.setFechaAlta(rs.getDate("FechaAlta"));
	            pre.setPlazoPago(rs.getInt("PlazoPago"));
	            pre.setImpxmes(rs.getFloat("ImportePagarXmes"));
	            pre.setCantCuo(rs.getInt("CantidadCuotas"));
	            pre.setconfimacion(rs.getBoolean("confirmacion"));
	          
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
		            System.out.println("No se pudo obtener la conexión a la base de datos.");
		            return false;
		        }

		        System.out.println("Actualizando préstamo con ID: " + idPrestamo);
		        System.out.println("Nuevo estado de confirmación: " + confirmacion);

		        String updateQuery = "UPDATE prestamo SET confirmacion = ? WHERE id = ?";
		        statement = connection.prepareStatement(updateQuery);
		        statement.setInt(1, confirmacion);
		        statement.setInt(2, idPrestamo);

		        int rowsAffected = statement.executeUpdate();

		        if (rowsAffected > 0) {
		            System.out.println("El estado del préstamo se ha actualizado correctamente. Filas afectadas: " + rowsAffected);
		            isUpdateExitoso = true;
		        } else {
		            System.out.println("No se actualizó ninguna fila. Verifica si el ID de préstamo es válido.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        System.out.println("Error durante la actualización.");
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
	    
	    String query = "SELECT Id, IdCliente, ImportePedidoCliente,FechaAlta,PlazoPago,ImportePagarXmes,CantidadCuotas,confirmacion FROM prestamo where confirmacion = 1 ";
	    
	    Connection con = Conexion.getConexion().getSQLConexion();
	    
	    if (con == null) {
	        System.out.println("No se pudo obtener la conexión a la base de datos.");
	        return PretAut;
	    } else {
	        System.out.println("Conexión a la base de datos establecida.");
	    }
	    
	    try (PreparedStatement ps = con.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            Prestamo pre = new Prestamo();
	            pre.setId(rs.getInt("id"));
	            pre.setIdCliente(rs.getInt("IdCliente"));
	            pre.setImporteCliente(rs.getFloat("ImportePedidoCliente"));
	            pre.setFechaAlta(rs.getDate("FechaAlta"));
	            pre.setPlazoPago(rs.getInt("PlazoPago"));
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
	

}
