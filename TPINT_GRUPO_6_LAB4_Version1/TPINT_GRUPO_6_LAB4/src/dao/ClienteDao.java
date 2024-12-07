package dao;

import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Localidad;
import Entidades.Nacionalidades;
import Entidades.Provincia;
import Entidades.Usuario;

public interface ClienteDao  {
	public boolean insertCliente (Cliente clien,Usuario usu);
	public boolean ModificarCliente (Cliente clie, Usuario usu);
	public boolean eliminarCliente(int idCliente);
	public ArrayList<Cliente> ListarCliente ();
	public Cliente ObtenerDatosXid(int id);
	public ArrayList<Cliente> filtrarClienteXsexo (String sexo);
	public boolean ValidacionDni (int dni);
	public boolean ValidacionCuil (int cuil);
	public Usuario verificarCredenciales(String username, String password);
	public boolean ValidacionUsuario (String usu);
	public ArrayList<Nacionalidades> ListNacionalidades();
	public ArrayList<Provincia> listProvincias (int idNacionalidad);
	public ArrayList<Localidad> listLocalidades (int idLocalidad);
	public boolean existeEmail(String Mail);
	public boolean actualizarContrasenaPorEmail (String email, String nuevaContrasena);
	public boolean ValidacionDniModificar (int dni, int id);
	public boolean ValidacionCuilModificar (int cuil, int id);
	public boolean ValidacionUsuarioModificar (String usu, int id);
	
}
