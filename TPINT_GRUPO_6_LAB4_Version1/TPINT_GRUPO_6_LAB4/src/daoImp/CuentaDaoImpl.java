package daoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import Entidades.Cliente;
import Entidades.Cuenta;
import dao.CuentaDao;

public class CuentaDaoImpl implements CuentaDao {

    private static final String insertCuenta = "INSERT INTO cuenta ( IdCliente, TipoCuenta, FechaCreacion , NumeroCuenta, CBU, Saldo, Activo) VALUES ( ?, ?, CURDATE(), ?, ?, 10000, ?)";
    private static final String ListarCuenta = "select cuenta.id as id,  cuenta.idcliente as IdCliente , cuenta.tipocuenta as TipoCuenta, cuenta.FechaCreacion as FechaCreacion, cuenta.NumeroCuenta , cuenta.CBU as CBU, cuenta.Saldo as Saldo, cuenta.Activo as Activo from cuenta inner join cliente on cuenta.IdCliente = cliente.id where cliente.DNI = ?";
    private static final String ListarCuentaTodos = "select cuenta.id as id,  cuenta.idcliente as IdCliente , cuenta.tipocuenta as TipoCuenta, cuenta.FechaCreacion as FechaCreacion, cuenta.NumeroCuenta , cuenta.CBU as CBU, cuenta.Saldo as Saldo, cuenta.Activo as Activo from cuenta inner join cliente on cuenta.IdCliente = cliente.id";
    private static final String EliminarCuenta = "UPDATE cuenta SET Activo = 0 WHERE id = ?";
    private static final String ModificarCuenta = "UPDATE cuenta SET TipoCuenta = ?, NumeroCuenta = ?, CBU = ?, Saldo = ?, Activo = ? WHERE Id = ?";
    private static final String ObtenerCuentaPorId = "SELECT * FROM cuenta WHERE id = ?";

    @Override
    public boolean insertCuenta(Cuenta cuenta) {
        PreparedStatement statement = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        boolean isInsertExitoso = false;

        try {
            // Asegurarse de que la conexi�n no est� cerrada
            if (conexion == null || conexion.isClosed()) {
                throw new SQLException("La conexi�n est� cerrada.");
            }

            conexion.setAutoCommit(false);

            statement = conexion.prepareStatement(insertCuenta);
            statement.setInt(1, cuenta.getIdCliente());
            statement.setInt(2, cuenta.getTipoCuenta());
      
            int numcuenta = GenerarNumeroCuenta();
            statement.setInt(3, numcuenta);
            
            int cbu = GenerarCBU();
            statement.setInt(4, cbu);
      
            statement.setBoolean(5, cuenta.isActivo());

            if (statement.executeUpdate() > 0) {
                conexion.commit();
                isInsertExitoso = true;
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
        int numCuenta = 0;
        PreparedStatement statement = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        ResultSet rs = null;

        try {
            String query = "SELECT NumeroCuenta FROM cuenta ORDER BY NumeroCuenta DESC LIMIT 1";
            statement = conexion.prepareStatement(query);
            rs = statement.executeQuery();
            
            if (rs.next()) {
                numCuenta = rs.getInt("NumeroCuenta");
            }
            numCuenta += 1;

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
            	
                Cuenta cue = new Cuenta();
                cue.setId(rs.getInt("Id"));
                cue.setIdCliente(rs.getInt("IdCliente"));
                cue.setTipoCuenta(rs.getInt("TipoCuenta"));
                cue.setFechaCreacion(rs.getString("FechaCreacion"));
                cue.setNumeroCuenta(rs.getInt("NumeroCuenta"));
                cue.setCbu(rs.getInt("CBU"));
                cue.setSaldo(rs.getFloat("Saldo"));
                cue.setActivo(rs.getBoolean("Activo"));
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
            	
                Cuenta cue = new Cuenta();
                cue.setId(rs.getInt("Id"));
                cue.setIdCliente(rs.getInt("IdCliente"));
                cue.setTipoCuenta(rs.getInt("TipoCuenta"));
                cue.setFechaCreacion(rs.getString("FechaCreacion"));
                cue.setNumeroCuenta(rs.getInt("NumeroCuenta"));
                cue.setCbu(rs.getInt("CBU"));
                cue.setSaldo(rs.getFloat("Saldo"));
                cue.setActivo(rs.getBoolean("Activo"));
                ListaCuenta.add(cue);
            }
        	
        }
        catch (SQLException e) {
            e.printStackTrace();
            
        }
        
        return ListaCuenta;
    }    
 
    public boolean EliminarCuenta(int id) {
        boolean exitoso = false;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        String query = EliminarCuenta;
        
        try {
            if (conexion == null || conexion.isClosed()) {
                throw new SQLException("La conexi�n est� cerrada.");
            }

            conexion.setAutoCommit(false);

            PreparedStatement st = conexion.prepareStatement(query);
            st.setInt(1, id);

            if (st.executeUpdate() > 0) {
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
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exitoso;
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
            statement.setBoolean(5, cuenta.isActivo());
            statement.setInt(6, cuenta.getId());  // Usamos el ID para identificar la cuenta a modificar

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
                throw new SQLException("La conexi�n est� cerrada.");
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
}
