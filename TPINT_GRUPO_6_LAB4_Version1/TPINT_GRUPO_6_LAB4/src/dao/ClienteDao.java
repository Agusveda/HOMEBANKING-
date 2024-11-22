package dao;

import java.util.ArrayList;

import Entidades.Cliente;
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
	public ArrayList<Nacionalidades> ListNacionaliadaes ();
	public ArrayList<Provincia> listProvincias ();
}
