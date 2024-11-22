package daoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Usuario;
import dao.ClienteDao;
import daoImp.Conexion;


public class ClienteDaoImp implements ClienteDao {
	
private static final String insertCliente = "INSERT INTO Cliente (DNI,CUIL,Nombre,Apellido,Sexo,Nacionalidad,FechaNacimiento,Direccion,Localidad,Provincia,CorreoElectronico,Telefono,Activo) VALUES(?, ?, ?, ?, ?,?,?,?,?,?,?,?,?)";
private static final String insertUsuario = "INSERT INTO usuario (NombreUsuario, Contraseña, idCliente, TipoUsario, Activo) VALUES (?, ?, ?,?,?)";
private static final String modificarCliente = "update cliente SET DNI= ?, CUIL= ?, Nombre=?, Apellido=?, Sexo=?, Nacionalidad=?, FechaNacimiento=?, Direccion=?, Localidad=?, Provincia= ?, CorreoElectronico=?, Telefono=? , Activo= 1 where id = ?;";
private static final String modificarUsuario = "update usuario SET NombreUsuario=?, Contraseña=? where idCliente =?";
private static final String ListarClienteUsuario = "SELECT cliente.Id, DNI, CUIL, Nombre, Apellido, Sexo, Nacionalidad, FechaNacimiento, Direccion, Localidad, Provincia, CorreoElectronico, Telefono, NombreUsuario AS NombreUsuario, Contraseña AS Contraseña FROM cliente JOIN usuario ON cliente.id = usuario.idcliente;";
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
    
    try {
        // Inserción en la tabla Cliente con generación de ID
        System.out.println("Preparando declaración de inserción para Cliente...");

        statementCliente = conexion.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);
        statementCliente.setInt(1, cli.getDni());
        statementCliente.setInt(2, cli.getCuil());
        statementCliente.setString(3, cli.getNombre());
        statementCliente.setString(4, cli.getApellido());
        statementCliente.setString(5, cli.getSexo());
        statementCliente.setString(6, cli.getNacionalidad());
        statementCliente.setString(7, cli.getFechaNacimiento());
        statementCliente.setString(8, cli.getDireccion());
        statementCliente.setString(9, cli.getLocalidad());
        statementCliente.setString(10, cli.getProvincia());
        statementCliente.setString(11, cli.getCorreoElectronico());
        statementCliente.setInt(12, cli.getTelefono());
        statementCliente.setInt(13,1);


        if (statementCliente.executeUpdate() > 0) {
            System.out.println("Inserción en Cliente exitosa.");

            // Obtener el ID generado para Cliente
            ResultSet generatedKeys = statementCliente.getGeneratedKeys();
            if (generatedKeys.next()) {
                int clienteId = generatedKeys.getInt(1);
                System.out.println("ID generado para cliente: " + clienteId);


                // Inserción en la tabla Usuario 
                
                statementUsuario = conexion.prepareStatement(insertUsuario);
                statementUsuario.setString(1, usu.getUsuario());
                statementUsuario.setString(2, usu.getContraseña());
                statementUsuario.setInt(3, clienteId);
                statementUsuario.setInt(4, usu.getTipoUsuario()); 
                statementUsuario.setBoolean(5, usu.getActivo()); 


                // Ejecutar la inserción de Usuario
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
        ps.setInt(2, cli.getCuil());
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
    String query = "SELECT * FROM cliente WHERE Activo = 1";
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
            cli.setCuil(rs.getInt("CUIL"));
            cli.setSexo(rs.getString("Sexo"));
            cli.setNacionalidad(rs.getString("Nacionalidad"));
            cli.setFechaNacimiento(rs.getString("FechaNacimiento"));
            cli.setDireccion(rs.getString("Direccion"));
            cli.setLocalidad(rs.getString("Localidad"));
            cli.setProvincia(rs.getString("Provincia"));
            cli.setCorreoElectronico(rs.getString("CorreoElectronico"));
            cli.setTelefono(Integer.parseInt(rs.getString("Telefono")));
     
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
            cli.setCuil(rs.getInt("CUIL"));
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
    String query = "SELECT * FROM usuario WHERE NombreUsuario = ? AND Contraseña = ?";

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
            usuario = new Usuario();
            usuario.setUsuario(rs.getString("NombreUsuario"));
            usuario.setContraseña(rs.getString("Contraseña"));

            try {
                usuario.setTipoUsuario(rs.getInt("TipoUsario"));
            } catch (SQLException e) {
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

    // No cerramos la conexión aquí, para que pueda ser reutilizada si es necesario
    return usuario;
}

@Override
public ArrayList<Cliente> filtrarClienteXsexo(String sexo) {
    ArrayList<Cliente> lista = new ArrayList<>();
    String query = "SELECT * FROM cliente WHERE Activo = 1 AND UPPER(Sexo) = UPPER(?)";
    
   
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
            cli.setCuil(rs.getInt("CUIL"));
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

    // No se cierra la conexión aquí
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

        
        String query = "SELECT COUNT(*) FROM cliente WHERE DNI = ?";
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
public boolean ValidacionCuil(int cuil) {
	boolean exists = false;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        
        connection = Conexion.getConexion().getSQLConexion();
        if (connection == null) {
            throw new SQLException("Conexión a la base de datos es nula");
        }

        
        String query = "SELECT COUNT(*) FROM cliente WHERE CUIL = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, cuil);

        
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

        
        String query = "SELECT COUNT(*) FROM usuario WHERE NombreUsuario = ?";
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
}


