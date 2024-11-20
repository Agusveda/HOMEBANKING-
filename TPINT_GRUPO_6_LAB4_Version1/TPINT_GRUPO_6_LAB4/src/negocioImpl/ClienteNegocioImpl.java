package negocioImpl;

import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Usuario;
import dao.ClienteDao;
import daoImp.ClienteDaoImp;
import negocio.ClienteNegocio;

public class ClienteNegocioImpl implements ClienteNegocio {

	private ClienteDao clienteDao = new ClienteDaoImp();

	@Override
	public boolean insertCliente(Cliente clien, Usuario usu) {
		return clienteDao.insertCliente(clien, usu);
	}

	@Override
	public boolean ModificarCliente(Cliente clie, Usuario usu) {
		return clienteDao.ModificarCliente(clie, usu) ;
	}

	@Override
	public boolean eliminarCliente(int idCliente) {
		return clienteDao.eliminarCliente(idCliente) ;
	}

	@Override
	public ArrayList<Cliente> ListarCliente() {
		return clienteDao.ListarCliente();
	}

	@Override
	public Cliente ObtenerDatosXid(int id) {
		return clienteDao.ObtenerDatosXid(id);
	}

	@Override
	public ArrayList<Cliente> filtrarClienteXsexo(String sexo) {
		return clienteDao.filtrarClienteXsexo(sexo);
	}

	@Override
	public boolean ValidacionDni(int dni) {
		return clienteDao.ValidacionDni(dni);
	}

	@Override
	public boolean ValidacionCuil(int cuil) {
		return clienteDao.ValidacionCuil(cuil);
	}

	public Usuario verificarCredenciales(String username, String password) {
		return clienteDao.verificarCredenciales(username, password) ;
	}

}
