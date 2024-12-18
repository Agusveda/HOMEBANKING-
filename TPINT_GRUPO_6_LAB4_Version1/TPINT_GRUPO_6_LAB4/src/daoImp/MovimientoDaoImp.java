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
	private static final String IngresarMovimientoPositivo = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 4 , NOW() , ? , ? , ?)";
	private static final String IngresarMovimientoNegativo = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 4 , NOW() , -? , ? , ?)";
	private static final String IngresarMovimientoPositivoAlta = "insert into movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) values ( 1 , NOW() , ? , ? , ?)";
	private static final String ModificarCuentaPositivo = "update cuenta SET Saldo = Saldo + ? where Id = ?";
	private static final String ModificarCuentaNegativo = "update cuenta SET Saldo = Saldo - ? where Id = ?";
	private static final String ObtenerIdCuentaPorCBU = "select Id from cuenta where CBU = ? and Activo = 1";
	private static final String ObtenerIdCuentaPorIdCliente = "select Id from cuenta where IdCliente = ? and Activo = 1";
	private static final String ObtenerSaldoPorIdCuenta = "select * from cuenta where Id = ? and Activo = 1 ";
	private static final String ExisteCBU = "SELECT * FROM cuenta WHERE CBU = ? and Activo = 1";
	private static final String ReporteMovimientos = "SELECT SUM(Importe) AS total FROM movimiento WHERE DATE(FechaMovimiento) BETWEEN ? AND ? AND Importe > 0 and TipoMovimiento = ?"; 
	private static final String ReporteMovimiento3 = "SELECT SUM(Importe) AS total FROM movimiento WHERE DATE(FechaMovimiento) BETWEEN ? AND ? and TipoMovimiento = ?";
	private static final String ReporteIngresoMovimiento = "SELECT SUM(m.Importe) AS total FROM movimiento m inner join cuenta c on c.Id = m.idCuenta inner join cliente cli on cli.Id = c.IdCliente WHERE cli.DNI = ? and m.Importe not like '%-%' and m.TipoMovimiento = 4 and c.Activo = 1"; 
	private static final String ReporteEgresoMovimiento = "SELECT SUM(m.Importe) AS total FROM movimiento m inner join cuenta c on c.Id = m.idCuenta inner join cliente cli on cli.Id = c.IdCliente WHERE cli.DNI = ? and m.Importe  like '%-%' and m.TipoMovimiento = 4 and c.Activo = 1";	
	private static final String TraerCuentasPorIdCliente = "select * from cuenta where IdCliente = ? and Activo = 1 ";
    private static final String InsertarMovimientoAltaPrestamo = "INSERT INTO movimiento (TipoMovimiento, FechaMovimiento, Importe, IdCuenta, Detalle) VALUES (?, NOW(), ?, ?, ?)";
    private static final String ActualizarSaldoCuenta = "UPDATE cuenta SET Saldo = Saldo - ? WHERE Id = ?";
	
	@Override
	public boolean insertarMovimientoAltaCuenta(Movimiento movimiento, int idCuenta) {
		System.out.println("Iniciando inserción de movimiento...");
		PreparedStatement statementMovimientoPositivo = null;
		
		Connection conexion = Conexion.getConexion().getSQLConexion();
		if (conexion == null) {
			System.out.println("No se pudo obtener la conexión a la base de datos.");
			return false;
		}
		boolean isInsertExitoso = false;

		try {
			System.out.println("Preparando declaración de inserción para movimiento...");
			statementMovimientoPositivo = conexion.prepareStatement(IngresarMovimientoPositivoAlta);

			statementMovimientoPositivo.setFloat(1, movimiento.getImporte());
			statementMovimientoPositivo.setInt(2, idCuenta);
			statementMovimientoPositivo.setString(3, movimiento.getDetalle());
			
			if (statementMovimientoPositivo.executeUpdate() > 0) 
			{
				conexion.commit();
				System.out.println("Inserción en Movimiento Positivo exitoso.");
				isInsertExitoso = true;
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
	public boolean insertarMovimientoAltaPrestamoConfirmado(Movimiento movimiento, int idCuenta) {
	    System.out.println("Iniciando inserción de movimiento para alta de préstamo...");
	    Connection connection = null;
	    PreparedStatement statement = null;
	    boolean altaPrestamoExitoso = false;

	    try {
	        connection = Conexion.getConexion().getSQLConexion(); 
	        if (connection == null) {
	            System.out.println("No se pudo obtener la conexión a la base de datos.");
	            return false;
	        }
	        connection.setAutoCommit(false);
	        
	        // Registrar el movimiento de alta de préstamo   
	        statement = connection.prepareStatement(InsertarMovimientoAltaPrestamo);       
	       
            // Establecer los parámetros para el movimiento
            statement.setInt(1, 2); // Tipo de movimiento: 2 - Alta de préstamo
            statement.setFloat(2, movimiento.getImporte()); // Importe del préstamo
            statement.setInt(3, idCuenta); 
           // statement.setInt(3, movimiento.getIdCuenta()); 
            statement.setString(4, movimiento.getDetalle()); // Detalle del movimiento (se maneja en la capa de negocio)

	        int rowsAffected = statement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Movimiento de alta de préstamo registrado exitosamente.");
	            altaPrestamoExitoso = true;
	            connection.commit(); // Confirmar transacción
	        } else {
	            System.out.println("No se insertó el movimiento de alta de préstamo. Verifica los datos.");
	            connection.rollback(); // Revertir si el movimiento no fue insertado
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error durante la inserción del movimiento de alta de préstamo.");
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

	    return altaPrestamoExitoso;
	}
	/*funciona 11.55
	@Override
	public boolean insertarMovimientoPagoCuota(Movimiento movimiento, int idCuenta) {
	    System.out.println("Iniciando inserción de movimiento para pago de cuota...");
	    PreparedStatement statementMovimientoNegativo = null;
	    PreparedStatement statementCuenta = null;
	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    if (conexion == null) {
	        System.out.println("No se pudo obtener la conexión a la base de datos.");
	        return false;
	    }
	    boolean pagoCuotaExitoso = false;

	    try {
	        // Preparando declaración para insertar movimiento negativo (Pago de cuota)
	        statementMovimientoNegativo = conexion.prepareStatement(IngresarMovimientoNegativo);
	        statementMovimientoNegativo.setFloat(1, movimiento.getImporte());  // El importe del pago (negativo)
	        statementMovimientoNegativo.setInt(2, idCuenta);  // ID de la cuenta
	        statementMovimientoNegativo.setString(3, "Pago de cuota");

	        if (statementMovimientoNegativo.executeUpdate() > 0) {
	            System.out.println("Inserción en Movimiento negativo (pago de cuota) exitosa.");

	            // Actualizar saldo de la cuenta (restar el pago)
	            String sqlActualizarCuenta = "UPDATE cuenta SET Saldo = Saldo - ? WHERE Id = ?";
	            statementCuenta = conexion.prepareStatement(sqlActualizarCuenta);
	            statementCuenta.setFloat(1, movimiento.getImporte());  // El importe a restar
	            statementCuenta.setInt(2, idCuenta);  // ID de la cuenta

	            if (statementCuenta.executeUpdate() > 0) {
	                System.out.println("Actualización de saldo de cuenta exitosa.");
	                conexion.commit();
	                pagoCuotaExitoso = true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Error durante la inserción del movimiento de pago de cuota.");

	        try {
	            conexion.rollback();  // Rollback en caso de error
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    return pagoCuotaExitoso;
	}
	*/
	@Override
	public boolean insertarMovimientoPagoCuota(Movimiento movimiento, int idCuenta) {
	    System.out.println("Iniciando inserción de movimiento para pago de cuota...");

	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    if (conexion == null) {
	        System.out.println("No se pudo obtener la conexión a la base de datos.");
	        return false;
	    }

	    boolean exito = false;

	    try (PreparedStatement psMovimiento = conexion.prepareStatement(IngresarMovimientoNegativo);
	         PreparedStatement psActualizarSaldo = conexion.prepareStatement(ActualizarSaldoCuenta)) {

	        conexion.setAutoCommit(false); // Iniciar la transacción

	        // Configurar y ejecutar inserción del movimiento negativo
	        psMovimiento.setFloat(1, -movimiento.getImporte());  // Monto negativo del pago
	        psMovimiento.setInt(2, idCuenta);                  // ID de la cuenta asociada
	        psMovimiento.setString(3, "Pago de cuota");        // Detalle del movimiento

	        if (psMovimiento.executeUpdate() > 0) {
	            System.out.println("Inserción en movimiento negativa exitosa.");

	            // Configurar y ejecutar actualización de saldo
	            psActualizarSaldo.setFloat(1, -movimiento.getImporte()); // Monto a restar
	            psActualizarSaldo.setInt(2, idCuenta);                 // ID de la cuenta

	            if (psActualizarSaldo.executeUpdate() > 0) {
	                System.out.println("Actualización de saldo exitosa.");
	                conexion.commit(); // Confirmar la transacción
	                exito = true;
	            }
	        }

	        if (!exito) {
	            conexion.rollback(); // Revertir cambios si no se completaron ambas operaciones
	            System.out.println("Transacción revertida por error en alguna operación.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            conexion.rollback();
	            System.out.println("Se realizó un rollback debido a una excepción.");
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	    }

	    return exito;
	}

	@Override
	public boolean insertarMovimientosTransferencia(Movimiento movimiento, int idCuenta) {
		System.out.println("Iniciando inserción de movimiento...");
		PreparedStatement statementMovimientoPositivo = null;
		PreparedStatement statementMovimientoNegativo = null;
		PreparedStatement statementCuenta = null;
		PreparedStatement statementBajaSaldo = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		if (conexion == null) {
			System.out.println("No se pudo obtener la conexión a la base de datos.");
			return false;
		}
		boolean transferenciaExitosa = false;

		try {
			System.out.println("Preparando declaración de inserción para movimiento...");
			statementMovimientoPositivo = conexion.prepareStatement(IngresarMovimientoPositivo);

			statementMovimientoPositivo.setFloat(1, movimiento.getImporte());
			statementMovimientoPositivo.setInt(2, movimiento.getIdCuenta()); // SE DEBERIA OBTENER ID DE CUENTA
			statementMovimientoPositivo.setString(3, movimiento.getDetalle());

			if (statementMovimientoPositivo.executeUpdate() > 0) {
				System.out.println("Inserción en Movimiento Positivo exitoso.");

				// DEBITO EN CUENTA DE CLIENTE
				statementMovimientoNegativo = conexion.prepareStatement(IngresarMovimientoNegativo);
				statementMovimientoNegativo.setFloat(1, movimiento.getImporte());
				statementMovimientoNegativo.setInt(2, idCuenta);
				statementMovimientoNegativo.setString(3, movimiento.getDetalle());

				if (statementMovimientoNegativo.executeUpdate() > 0) {
					System.out.println("Inserción en Movimiento negativo exitoso.");

					System.out.println("Preparando declaración de inserción para cuenta.");
					
					statementBajaSaldo = conexion.prepareStatement(ModificarCuentaNegativo);
					statementBajaSaldo.setFloat(1, movimiento.getImporte());
					statementBajaSaldo.setInt(2, idCuenta);

					if (statementBajaSaldo.executeUpdate() > 0) {
						System.out.println("Inserción en Cuenta negativa exitosa.");
						
						// ACREDITACION EN CUENTA DE CLIENTE DESTINO
						statementCuenta = conexion.prepareStatement(ModificarCuentaPositivo);
						statementCuenta.setFloat(1, movimiento.getImporte());
						statementCuenta.setInt(2, movimiento.getIdCuenta());
						if (statementCuenta.executeUpdate() > 0) {
							System.out.println("Inserción en Cuenta exitosa.");
							conexion.commit();
							transferenciaExitosa = true;
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
		return transferenciaExitosa;
	}

	
	/////////////////////////
	
	@Override
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
	/*
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

					System.out.println("Preparando declaración de inserción para cuenta.");

					statementBajaSueldo = conexion.prepareStatement(ModificarCuentaNegativo);
					statementBajaSueldo.setFloat(1, movi.getImporte());
					statementBajaSueldo.setInt(2, idCue);

					if (statementBajaSueldo.executeUpdate() > 0) {
						System.out.println("Inserción en Cuenta negativa exitosa.");

						statementCuenta = conexion.prepareStatement(ModificarCuentaPositivo);
						statementCuenta.setFloat(1, movi.getImporte());
						statementCuenta.setInt(2, movi.getIdCuenta());
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
	public boolean insertarAltaCuenta(Movimiento movi, int idCue) { //borrar
		System.out.println("Iniciando inserción de movimiento...");

		PreparedStatement statementMovimientoP = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		if (conexion == null) {
			System.out.println("No se pudo obtener la conexión a la base de datos.");
			return false;
		}
		boolean isInsertExitoso = false;

		try {

			System.out.println("Preparando declaración de inserción para movimiento...");

			statementMovimientoP = conexion.prepareStatement(IngresarMovimientoPositivoAlta);

			statementMovimientoP.setFloat(1, movi.getImporte());
			statementMovimientoP.setInt(2, idCue);
			statementMovimientoP.setString(3, movi.getDetalle());
			
			if (statementMovimientoP.executeUpdate() > 0) 
			{
				conexion.commit();
				System.out.println("Inserción en Movimiento Positivo exitoso.");
				isInsertExitoso = true;
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
	*/
	@Override
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

		//saldo = cuenta.getId();
		saldo = cuenta.getSaldo();

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
	public float ReporteMovimiento(int TipoMovimiento, String FechaInicio, String FechaFinal) 
	{
		float total = 0;
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try 
		{
			if (conexion == null || conexion.isClosed()) 
			{
				throw new SQLException("La conexión está cerrada.");
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
			
			/// PARA EL MOVIMIENTO DE PAGAR PRESTAMO(EL 3)
			if (TipoMovimiento == 3)
			{
				statement2 = conexion.prepareStatement(ReporteMovimiento3);
				statement2.setString(1, FechaInicio);
				statement2.setString(2, FechaFinal);
				statement2.setInt(3, TipoMovimiento);
				rs2 = statement2.executeQuery();
				
				if (rs2.next()) 
				{
					total = rs2.getFloat("Total");
				}
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
	        // Verifica si la conexión está cerrada y reconecta si es necesario
	        if (con == null || con.isClosed()) {
	            System.out.println("Conexión cerrada, intentando reconectar...");
	            con = Conexion.getConexion().getSQLConexion();  // Vuelve a obtener la conexión si está cerrada
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
				throw new SQLException("La conexión está cerrada.");
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
	            throw new SQLException("La conexión está cerrada.");
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