package daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Entidades.Cliente;
import Entidades.Localidad;
import Entidades.Nacionalidades;
import Entidades.Provincia;
import Entidades.Usuario;
import dao.ClienteDao;
import daoImp.Conexion;

public class ClienteDaoImp implements ClienteDao {
	
private static final String insertCliente = "INSERT INTO Cliente (DNI,CUIL,Nombre,Apellido,Sexo,Nacionalidad,FechaNacimiento,Direccion,Localidad,Provincia,CorreoElectronico,Telefono,Activo) VALUES(?, ?, ?, ?, ?,?,?,?,?,?,?,?,?)";
private static final String insertUsuario = "INSERT INTO usuario (NombreUsuario, Contraseña, idCliente, TipoUsario, Activo) VALUES (?, ?, ?,?,?)";
private static final String modificarCliente = "update cliente SET DNI= ?, CUIL= ?, Nombre=?, Apellido=?, Sexo=?, Nacionalidad=?, FechaNacimiento=?, Direccion=?, Localidad=?, Provincia= ?, CorreoElectronico=?, Telefono=? , Activo= 1 where id = ?;";
private static final String modificarUsuario = "update usuario SET NombreUsuario=?, Contraseña=? where idCliente =?";
private static final String ListarClienteUsuario = "SELECT c.*, u.NombreUsuario, u.Contraseña FROM cliente c JOIN usuario u on u.IdCliente = c.Id where c.Activo=1;";
private static final String ObtenerPorId = "select * from cliente where id=?";
private static final String ObtenerUsuarioPorId = "select NombreUsuario, Contraseña from usuario where idCliente=?;";
private static final String ExisteDNI = "SELECT COUNT(*) AS total FROM cliente WHERE dni = ?";
private static final String ExisteCUIL = "select COUNT(*) AS total from cliente where CUIL = ?;";
private static final String ExisteNombreUsuario = "select COUNT(*) AS total from usuario where NombreUsuario =?;";

@Override 
public boolean insertCliente(Cliente cli, Usuario usu) {
    System.out.println("Iniciando inserción de cliente...");

    PreparedStatement statementCliente = null;
    PreparedStatement statementUsuario = null;
    Connection conexion = Conexion.getConexion().getSQLConexion();
    if (conexion == null) {
        System.out.println("No se pudo obtener la conexión a la base de datos.");
        return false;
    }
    boolean isInsertExitoso = false;
    usu.setActivo(true);
    
    String nacionalidad = null;
    String provincia = null;
    String localidad = null;
    
    try {
    	
    	 String sqlNacionalidad = "SELECT Pais FROM Nacionalidades WHERE IdNacionalidad = ?";
         PreparedStatement statementNacionalidad = conexion.prepareStatement(sqlNacionalidad);
         statementNacionalidad.setString(1, cli.getNacionalidad());  
         ResultSet rsNacionalidad = statementNacionalidad.executeQuery();
         if (rsNacionalidad.next()) {
             nacionalidad = rsNacionalidad.getString("Pais");
         }

         String sqlProvincia = "SELECT Provincia FROM Provincias WHERE IDProvincia = ?";
         PreparedStatement statementProvincia = conexion.prepareStatement(sqlProvincia);
         statementProvincia.setString(1, cli.getProvincia());  
         ResultSet rsProvincia = statementProvincia.executeQuery();
         if (rsProvincia.next()) {
             provincia = rsProvincia.getString("Provincia");
         }

         String sqlLocalidad = "SELECT Localiadad FROM localidades WHERE IDLocalidad = ?";
         PreparedStatement statementLocalidad = conexion.prepareStatement(sqlLocalidad);
         statementLocalidad.setString(1, cli.getLocalidad());  
         ResultSet rsLocalidad = statementLocalidad.executeQuery();
         if (rsLocalidad.next()) {
             localidad = rsLocalidad.getString("Localiadad");
         }
         
        System.out.println("Preparando declaración de inserción para Cliente...");

        statementCliente = conexion.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);
        statementCliente.setInt(1, cli.getDni());
        statementCliente.setLong(2, cli.getCuil());
        statementCliente.setString(3, cli.getNombre());
        statementCliente.setString(4, cli.getApellido());
        statementCliente.setString(5, cli.getSexo());
        statementCliente.setString(6, nacionalidad);
        statementCliente.setString(7, cli.getFechaNacimiento());
        statementCliente.setString(8, cli.getDireccion());
        statementCliente.setString(9, localidad);
        statementCliente.setString(10,  provincia);
        statementCliente.setString(11, cli.getCorreoElectronico());
        statementCliente.setInt(12, cli.getTelefono());
        statementCliente.setInt(13,1);


        if (statementCliente.executeUpdate() > 0) {
            System.out.println("Inserción en Cliente exitosa.");

            ResultSet generatedKeys = statementCliente.getGeneratedKeys();
            if (generatedKeys.next()) {
                int clienteId = generatedKeys.getInt(1);
                System.out.println("ID generado para cliente: " + clienteId);


                
                statementUsuario = conexion.prepareStatement(insertUsuario);
                statementUsuario.setString(1, usu.getUsuario());
                statementUsuario.setString(2, usu.getContraseña());
                statementUsuario.setInt(3, clienteId);
                statementUsuario.setInt(4, usu.getTipoUsuario()); 
                statementUsuario.setBoolean(5, usu.getActivo()); 


                if (statementUsuario.executeUpdate() > 0) {
                    System.out.println("Inserción en Usuario exitosa.");

                    conexion.commit();
                    isInsertExitoso = true;
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

public boolean ModificarCliente (Cliente cli, Usuario usu)
{
	
	try {
		Class.forName("com.mysql.jdbc.Driver");

	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
    PreparedStatement ps = null;
    PreparedStatement statementUsuario = null;
    Connection cn = Conexion.getConexion().getSQLConexion();
    if (cn == null) {
        System.out.println("No se pudo obtener la conexión a la base de datos.");
        return false;
    }
    
    boolean isModiExitoso = false;
	try
	{
		String query = modificarCliente;
				
		ps = cn.prepareStatement(query);
		
        ps.setInt(1, cli.getDni());
        ps.setLong(2, cli.getCuil());
        ps.setString(3, cli.getNombre());
        ps.setString(4, cli.getApellido());
        ps.setString(5, cli.getSexo());
        ps.setString(6, cli.getNacionalidad());
        ps.setString(7, cli.getFechaNacimiento());
        ps.setString(8, cli.getDireccion());
        ps.setString(9, cli.getLocalidad());
        ps.setString(10, cli.getProvincia());
        ps.setString(11, cli.getCorreoElectronico());
        ps.setInt(12, cli.getTelefono());
        ps.setInt(13, cli.getId());
        
        if (ps.executeUpdate() > 0) 
        {
        	System.out.println("Modificacion de cliente exitosa.");
        	
            statementUsuario = cn.prepareStatement(modificarUsuario);
            statementUsuario.setString(1, usu.getUsuario());
            statementUsuario.setString(2, usu.getContraseña());
            statementUsuario.setInt(3, cli.getId());
        	
            if (statementUsuario.executeUpdate() > 0) 
            {
            System.out.println("Modificacion de Usuario exitosa.");

            cn.commit();
            isModiExitoso = true;
            }
        }
        
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return isModiExitoso;
}


public ArrayList<Cliente> ListarCliente() {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver cargado exitosamente.");
    } catch (ClassNotFoundException e) {
        System.out.println("Error al cargar el driver: " + e.getMessage());
        e.printStackTrace();
    }
    
    ArrayList<Cliente> ListaCliente = new ArrayList<Cliente>();
    String query = ListarClienteUsuario;
    Connection con = Conexion.getConexion().getSQLConexion();
    
    if (con == null) {
        System.out.println("No se pudo obtener la conexión a la base de datos.");
        return ListaCliente;
    } else {
        System.out.println("Conexión a la base de datos establecida.");
    }
    
    try (PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
        
        System.out.println("Consulta ejecutada: " + query);
        
        int count = 0; // Contador para el número de clientes encontrados
        while (rs.next()) {
            Cliente cli = new Cliente();
            cli.setId(rs.getInt("Id"));
            cli.setNombre(rs.getString("Nombre"));
            cli.setApellido(rs.getString("Apellido"));
            cli.setDni(rs.getInt("DNI"));
            cli.setCuil(rs.getLong("CUIL"));
            cli.setSexo(rs.getString("Sexo"));
            cli.setNacionalidad(rs.getString("Nacionalidad"));
            cli.setFechaNacimiento(rs.getString("FechaNacimiento"));
            cli.setDireccion(rs.getString("Direccion"));
            cli.setLocalidad(rs.getString("Localidad"));
            cli.setProvincia(rs.getString("Provincia"));
            cli.setCorreoElectronico(rs.getString("CorreoElectronico"));
            cli.setTelefono(Integer.parseInt(rs.getString("Telefono")));
            cli.setUsuario(rs.getString("NombreUsuario"));
            cli.setContrasenia(rs.getString("Contraseña"));
     
            ListaCliente.add(cli);
            count++;
        }
        
        System.out.println("Número de clientes activos encontrados: " + count);
        
    } catch (SQLException e) {
        System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        e.printStackTrace();
    }
    
    if (ListaCliente.isEmpty()) {
        System.out.println("No se encontraron clientes activos.");
    } else {
        System.out.println("Lista de clientes cargada exitosamente.");
    }
    
    return ListaCliente;
}

public Cliente ObtenerDatosXid(int id) {
    Cliente cli = new Cliente();
    
    PreparedStatement ps = null;
    PreparedStatement psUsu = null;
    Connection cn = Conexion.getConexion().getSQLConexion();
    if (cn == null) {
        System.out.println("No se pudo obtener la conexión a la base de datos.");
        return cli;
    }
    try {
        String query = ObtenerPorId;
        
        ps = cn.prepareStatement(query);
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            cli.setId(rs.getInt("Id"));
            cli.setNombre(rs.getString("Nombre"));
            cli.setApellido(rs.getString("Apellido"));
            cli.setDni(rs.getInt("DNI"));
            cli.setCuil(rs.getLong("CUIL"));
            cli.setSexo(rs.getString("Sexo"));
            cli.setNacionalidad(rs.getString("Nacionalidad"));
            cli.setFechaNacimiento(rs.getString("FechaNacimiento"));
            cli.setDireccion(rs.getString("Direccion"));
            cli.setLocalidad(rs.getString("Localidad"));
            cli.setProvincia(rs.getString("Provincia"));
            cli.setCorreoElectronico(rs.getString("CorreoElectronico"));
            cli.setTelefono(Integer.parseInt(rs.getString("Telefono")));
        }
        
        String query2 = ObtenerUsuarioPorId;
        psUsu = cn.prepareStatement(query2);
        psUsu.setInt(1, id);
        
        ResultSet rss = psUsu.executeQuery();
        while (rss.next())
        {
        	cli.setUsuario(rss.getString("NombreUsuario"));
        	cli.setContrasenia(rss.getString("Contraseña"));
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return cli;
}

public boolean eliminarCliente(int idCliente) {
    Connection conexion = null;
    PreparedStatement stmt = null;
    PreparedStatement stmtU = null;
    PreparedStatement stmtCuentas = null;
    boolean success = false;
    boolean success2 = false;
    boolean success3 = false;

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

      
        String query = "UPDATE cliente SET Activo = 0 WHERE id = ?";
        stmt = conexion.prepareStatement(query);
        stmt.setInt(1, idCliente);

        System.out.println("Ejecutando actualización para eliminar cliente con ID: " + idCliente);
        int rowsAffected = stmt.executeUpdate();

   
 
        if (rowsAffected > 0) {
            success = true;
            System.out.println("Cliente eliminado exitosamente. Filas afectadas: " + rowsAffected);
            conexion.commit(); 
        } else {
            System.out.println("No se encontró ningún cliente con ID: " + idCliente);
            conexion.rollback(); 
        }

        /// ELIMINAR USUARIO
        String query2 = "UPDATE usuario SET Activo = 0 WHERE idCliente = ?";
        stmtU = conexion.prepareStatement(query2);
        stmtU.setInt(1, idCliente);

        System.out.println("Ejecutando actualización para eliminar cliente con ID: " + idCliente);
        int rowsAffected2 = stmtU.executeUpdate();

   
 
        if (rowsAffected2 > 0) {
            success2 = true;
            System.out.println("Cliente eliminado exitosamente. Filas afectadas: " + rowsAffected2);
            conexion.commit(); 
        } else {
            System.out.println("No se encontró ningún cliente con ID: " + idCliente);
            conexion.rollback(); 
        }
        
        ///ELIMINAR CUENTA
        String query3 = "UPDATE cuenta SET Activo = 0 WHERE idCliente = ?";
        stmtCuentas = conexion.prepareStatement(query3);
        stmtCuentas.setInt(1, idCliente);

        System.out.println("Ejecutando actualización para eliminar cliente con ID: " + idCliente);
        int rowsAffected3 = stmtCuentas.executeUpdate();

   
 
        if (rowsAffected3 > 0) {
            success3 = true;
            System.out.println("Cliente eliminado exitosamente. Filas afectadas: " + rowsAffected3);
            conexion.commit(); 
        } else {
            System.out.println("No se encontró ningún cliente con ID: " + idCliente);
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


public Usuario verificarCredenciales(String username, String password) {
	Usuario usuario = null;
    String query = "SELECT * FROM usuario WHERE NombreUsuario = ? AND Contraseña = ? and Activo=1";

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = Conexion.getConexion().getSQLConexion(); // Mantener la conexión abierta
        stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        rs = stmt.executeQuery();

        if (rs.next()) {
        	try {
            usuario = new Usuario();
            usuario.setUsuario(rs.getString("NombreUsuario"));
            usuario.setContraseña(rs.getString("Contraseña"));
            usuario.setIdCliente(rs.getInt("IdCliente"));

                usuario.setTipoUsuario(rs.getInt("TipoUsario"));
            } catch (SQLException e) {
            	e.printStackTrace();
                // Manejo de error al obtener tipoUsuario
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Mostrar detalles del error
    } finally {
        // Cerrar solo el ResultSet y PreparedStatement, no la conexión
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return usuario;
}

@Override
public ArrayList<Cliente> filtrarClienteXsexo(String sexo) {
    ArrayList<Cliente> lista = new ArrayList<>();
    String query = "SELECT c.*, u.* FROM cliente c INNER JOIN usuario u ON u.IdCliente = c.Id WHERE c.Activo = 1 AND UPPER(c.Sexo) = UPPER(?)";
      
    Connection conexion = null;
    PreparedStatement statement = null;
    ResultSet rs = null;

    try {
        conexion = Conexion.getConexion().getSQLConexion(); 
        statement = conexion.prepareStatement(query);
        statement.setString(1, sexo);
        rs = statement.executeQuery();

        while (rs.next()) {
            Cliente cli = new Cliente();
            cli.setId(rs.getInt("Id"));
            cli.setDni(rs.getInt("DNI"));
            cli.setCuil(rs.getLong("CUIL"));
            cli.setNombre(rs.getString("Nombre"));
            cli.setApellido(rs.getString("Apellido"));
            cli.setSexo(rs.getString("Sexo"));
            cli.setNacionalidad(rs.getString("Nacionalidad"));
            cli.setFechaNacimiento(rs.getString("FechaNacimiento"));
            cli.setDireccion(rs.getString("Direccion"));
            cli.setLocalidad(rs.getString("Localidad"));
            cli.setProvincia(rs.getString("Provincia"));
            cli.setCorreoElectronico(rs.getString("CorreoElectronico"));
            cli.setTelefono(rs.getInt("Telefono"));
            cli.setActivo(rs.getBoolean("Activo"));
            cli.setUsuario(rs.getString("NombreUsuario"));
            cli.setContrasenia(rs.getString("Contraseña"));
            lista.add(cli);
        }

        System.out.println("Clientes encontrados para el género '" + sexo + "': " + lista.size());
    } catch (SQLException e) {
        System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        e.printStackTrace();
    } finally {
        // Cerrar solo el Statement y ResultSet, no la conexión
        try {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return lista;
}

@Override
public boolean ValidacionDni(int dni) {
	boolean exists = false;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        
        connection = Conexion.getConexion().getSQLConexion();
        if (connection == null) {
            throw new SQLException("Conexión a la base de datos es nula");
        }
        
        String query = "SELECT COUNT(*) FROM cliente WHERE DNI = ? and Activo = 1";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, dni);

        
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
public boolean ValidacionCuil(long cuil) {
	boolean exists = false;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        
        connection = Conexion.getConexion().getSQLConexion();
        if (connection == null) {
            throw new SQLException("Conexión a la base de datos es nula");
        }

        
        String query = "SELECT COUNT(*) FROM cliente WHERE CUIL = ? and Activo = 1";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, cuil);

        
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
public boolean ValidacionUsuario(String usu) {
	
	boolean exists = false;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        
        connection = Conexion.getConexion().getSQLConexion();
        if (connection == null) {
            throw new SQLException("Conexión a la base de datos es nula");
        }

        
        String query = "SELECT COUNT(*) FROM usuario WHERE NombreUsuario = ? and Activo = 1";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, usu);

        
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
public ArrayList<Provincia> listProvincias(int idNacionalidad) {
	ArrayList<Provincia> ListaProv = new ArrayList<Provincia>();
    String query = "SELECT IDProvincia, IdNacionalidad, Provincia FROM provincias WHERE IdNacionalidad = ?";
    Connection con = Conexion.getConexion().getSQLConexion();

    if (con == null) {
        System.out.println("No se pudo obtener la conexión a la base de datos.");
        return ListaProv;
    } else {
        System.out.println("Conexión a la base de datos establecida.");
    }

    try (PreparedStatement ps = con.prepareStatement(query)) {
        // Filtrar por nacionalidad
        ps.setInt(1, idNacionalidad);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Provincia pro = new Provincia();
                pro.setId(rs.getInt("IDProvincia"));
                pro.setIdNacionalidad(rs.getInt("IdNacionalidad"));
                pro.setProvincia(rs.getString("Provincia"));
                ListaProv.add(pro);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        e.printStackTrace();
    }

    return ListaProv;
	}

	
public ArrayList<Localidad> listLocalidades(int idProvincia) {
	ArrayList<Localidad> listaLoc = new ArrayList<Localidad>();
    String query = "SELECT IDLocalidad, IdProvincia, Localiadad FROM localidades WHERE IdProvincia = ?";
    Connection con = Conexion.getConexion().getSQLConexion();

    if (con == null) {
        System.out.println("No se pudo obtener la conexión a la base de datos.");
        return listaLoc;
    } else {
        System.out.println("Conexión a la base de datos establecida.");
    }

    try (PreparedStatement ps = con.prepareStatement(query)) {
        ps.setInt(1, idProvincia);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
            	Localidad localidad = new Localidad();
                localidad.setIdLocalidad(rs.getInt("idLocalidad"));
                localidad.setLocalidad(rs.getString("Localiadad"));
                localidad.setIdProvincia(rs.getInt("IDProvincia"));
                listaLoc.add(localidad);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        e.printStackTrace();
    }

    return listaLoc;
}

@Override
public boolean existeEmail(String Mail) {
	
	boolean exists = false;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        
        connection = Conexion.getConexion().getSQLConexion();
        if (connection == null) {
            throw new SQLException("Conexión a la base de datos es nula");
        }
        
        String query = "SELECT COUNT(*) FROM cliente WHERE CorreoElectronico = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, Mail);

        
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
public boolean actualizarContrasenaPorEmail(String email, String nuevaContrasena) {
	Connection cn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        cn = Conexion.getConexion().getSQLConexion();
        if (cn == null) {
            System.out.println("No se pudo obtener la conexión a la base de datos.");
            return false;
        }
        String query = "UPDATE usuario u "
                     + "INNER JOIN cliente c ON u.IdCliente = c.Id "
                     + "SET u.Contraseña = ? "
                     + "WHERE c.CorreoElectronico = ? and u.Activo = 1";
        
        ps = cn.prepareStatement(query);
        ps.setString(1, nuevaContrasena); // Nueva contraseña
        ps.setString(2, email);          // Correo electrónico

        int rowsAffected = ps.executeUpdate();
        
        if (rowsAffected > 0) {
            System.out.println("Actualización exitosa de la contraseña para el correo: " + email);
            cn.commit(); 
            return true;
        } else {
            System.out.println("No se encontró un cliente con el correo: " + email);
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        if (cn != null) {
            try {
                cn.rollback(); // Revertir la transacción en caso de error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        return false;
    } finally {
        try {
            if (ps != null) ps.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

@Override
public boolean ValidacionDniModificar(int dni, int id) 
{
	int cont=0;
	boolean exists = false;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        
        connection = Conexion.getConexion().getSQLConexion();
        if (connection == null) 
        {
            throw new SQLException("Conexión a la base de datos es nula");
        }
        
        String query = "SELECT COUNT(*) FROM cliente WHERE DNI = ? and Id != ? and Activo = 1";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, dni);
        preparedStatement.setInt(2, id);
     
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
	return exists;
}

@Override
public boolean ValidacionCuilModificar(long cuil, int id) {
	boolean exists = false;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        
        connection = Conexion.getConexion().getSQLConexion();
        if (connection == null) {
            throw new SQLException("Conexión a la base de datos es nula");
        }
       
        String query = "SELECT COUNT(*) FROM cliente WHERE CUIL = ? and Id != ? and Activo = 1";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, cuil);
        preparedStatement.setInt(2, id);
     
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return exists;
}

@Override
public boolean ValidacionUsuarioModificar(String usu, int id) {
	boolean exists = false;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    try {
        
        connection = Conexion.getConexion().getSQLConexion();
        if (connection == null) {
            throw new SQLException("Conexión a la base de datos es nula");
        }
        
        String query = "SELECT COUNT(*) FROM usuario WHERE NombreUsuario = ? and IdCliente != ? and Activo = 1";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, usu);
        preparedStatement.setInt(2, id);
      
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
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return exists;
}

@Override
public ArrayList<Nacionalidades> ListNacionalidades() {
	try {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver cargado exitosamente.");
    } catch (ClassNotFoundException e) {
        System.out.println("Error al cargar el driver: " + e.getMessage());
        e.printStackTrace();
    }
    
    ArrayList<Nacionalidades> ListaPais = new ArrayList<Nacionalidades>();
    String query = "SELECT IdNacionalidad, Pais FROM Nacionalidades";
    Connection con = Conexion.getConexion().getSQLConexion();
    
    if (con == null) {
        System.out.println("No se pudo obtener la conexión a la base de datos.");
        return ListaPais;
    } else {
        System.out.println("Conexión a la base de datos establecida.");
    }
    
    try (PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            Nacionalidades nac = new Nacionalidades();
            nac.setId(rs.getInt("IdNacionalidad"));
            nac.setNombre(rs.getString("Pais"));
     
            ListaPais.add(nac);           
        }        
        
    } catch (SQLException e) {
        System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        e.printStackTrace();
    }      
    return ListaPais;
	}
}


