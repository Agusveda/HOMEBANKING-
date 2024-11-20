package negocio;

import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Usuario;

public interface ClienteNegocio {
	public boolean insertCliente (Cliente clien,Usuario usu);
	public boolean ModificarCliente (Cliente clie, Usuario usu);
	public boolean eliminarCliente(int idCliente);
	public ArrayList<Cliente> ListarCliente ();
	public Cliente ObtenerDatosXid(int id);
	public ArrayList<Cliente> filtrarClienteXsexo (String sexo);
	public boolean ValidacionDni (int dni);
	public boolean ValidacionCuil (int cuil);
	public Usuario verificarCredenciales(String username, String password);
}
