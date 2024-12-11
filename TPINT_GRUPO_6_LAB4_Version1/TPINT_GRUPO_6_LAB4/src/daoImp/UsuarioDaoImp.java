package daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidades.Cliente;
import dao.UsuarioDao;

public class UsuarioDaoImp implements UsuarioDao {

	@Override
	public Cliente ObtenerDatosXidUsuario(int id) {
		Cliente cli = new Cliente();
	    PreparedStatement ps = null;
	    Connection cn = Conexion.getConexion().getSQLConexion();
	    
	    if (cn == null) {
	        System.out.println("No se pudo obtener la conexión a la base de datos.");
	        return cli;
	    }

	    try {
	        // Consulta combinada con JOIN para obtener datos del cliente y usuario
	        String query = "SELECT c.*, u.NombreUsuario, u.Contraseña " +
	                       "FROM cliente c " +
	                       "INNER JOIN usuario u ON c.Id = u.IdCliente " +
	                       "WHERE u.Id = ?";

	        ps = cn.prepareStatement(query);
	        ps.setInt(1, id);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            // Datos del cliente
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
	            cli.setTelefono(rs.getInt("Telefono"));

	            // Datos del usuario
	            cli.setUsuario(rs.getString("NombreUsuario"));
	            cli.setContrasenia(rs.getString("Contraseña"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Asegúrate de cerrar el PreparedStatement si fue abierto
	        try {
	            if (ps != null) ps.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return cli;
	}

	@Override
	public boolean EsAdmin(int idCliente) 
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

	        
	        String query = "SELECT TipoUsario FROM usuario WHERE IdCliente = ? and Activo = 1";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, idCliente);

	        
	        resultSet = preparedStatement.executeQuery();

	        
	        if (resultSet.next()) 
	        {
	        	if (resultSet.getInt("TipoUsario") == 1)
	        	{
	        		exists = resultSet.getInt(1) > 0;	        		
	        	}
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
}


