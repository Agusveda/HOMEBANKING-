package negocioImpl;

import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Localidad;
import Entidades.Nacionalidades;
import Entidades.Provincia;
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
	public boolean ValidacionCuil(long cuil) {
		return clienteDao.ValidacionCuil(cuil);
	}

	public Usuario verificarCredenciales(String username, String password) {
		return clienteDao.verificarCredenciales(username, password) ;
	}

	@Override
	public boolean ValidacionUsuario(String usu) {
		
		return clienteDao.ValidacionUsuario(usu);
	}

	@Override
	public ArrayList<Nacionalidades> ListNacionalidades() {
		return clienteDao.ListNacionalidades();
	}

	@Override
	public ArrayList<Provincia> listProvincias(int idNacionalidad) {
		return clienteDao.listProvincias(idNacionalidad);
	}
	
	@Override
	public ArrayList<Localidad> listLocalidades(int idProvincias) {
		return clienteDao.listLocalidades(idProvincias);
	}

	@Override
	public boolean existeEmail(String Mail) {
		
		return clienteDao.existeEmail(Mail);
	}

	@Override
	public boolean actualizarContrasenaPorEmail(String email, String nuevaContrasena) {
		
		return clienteDao.actualizarContrasenaPorEmail(email, nuevaContrasena);
	}
	
	@Override
	public boolean ValidacionDniModificar(int dni, int id) {
		return clienteDao.ValidacionDniModificar(dni, id);
	}

	@Override
	public boolean ValidacionCuilModificar(long cuil, int id) {
		
		return clienteDao.ValidacionCuilModificar(cuil, id);
	}

	@Override
	public boolean ValidacionUsuarioModificar(String usu, int id) {
		return clienteDao.ValidacionUsuarioModificar(usu, id);
	}



}
