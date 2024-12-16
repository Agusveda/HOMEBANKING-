package negocio;

import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Localidad;
import Entidades.Nacionalidades;
import Entidades.Provincia;
import Entidades.Usuario;

public interface ClienteNegocio {
	public boolean insertCliente (Cliente clien,Usuario usu);
	public boolean ModificarCliente (Cliente clie, Usuario usu);
	public boolean eliminarCliente(int idCliente);
	public ArrayList<Cliente> ListarCliente ();
	public Cliente ObtenerDatosXid(int id);
	public ArrayList<Cliente> filtrarClienteXsexo (String sexo);
	public boolean ValidacionDni (int dni);
	public boolean ValidacionCuil (long cuil);
	public Usuario verificarCredenciales(String username, String password);
	public boolean ValidacionUsuario (String usu);
	public ArrayList<Nacionalidades> ListNacionalidades();
	public ArrayList<Provincia> listProvincias (int idNacionalidad);
	public ArrayList<Localidad> listLocalidades (int idProvincias);
	public boolean existeEmail(String Mail);
	public boolean actualizarContrasenaPorEmail (String email, String nuevaContrasena);
	public boolean ValidacionDniModificar (int dni, int id);
	public boolean ValidacionCuilModificar (long cuil, int id);
	public boolean ValidacionUsuarioModificar (String usu, int id);

}
