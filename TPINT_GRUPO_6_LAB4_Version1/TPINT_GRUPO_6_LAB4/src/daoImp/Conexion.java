package daoImp;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Conexion {
    private static Conexion instancia;
    private Connection connection;

    private Conexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Modificar la URL de conexión para incluir la zona horaria
            String url = "jdbc:mysql://localhost:3306/tpint_grupo_6_lab4?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires&allowPublicKeyRetrieval=true";
            this.connection = DriverManager.getConnection(url, "root", "root");
            this.connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Conexion getConexion() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        
        try {
            if (instancia.connection == null || instancia.connection.isClosed()) {
                instancia = new Conexion(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return instancia;
    }

    public Connection getSQLConexion() {
        return this.connection;
    }

    public void cerrarConexion() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        instancia = null;
    }
}
