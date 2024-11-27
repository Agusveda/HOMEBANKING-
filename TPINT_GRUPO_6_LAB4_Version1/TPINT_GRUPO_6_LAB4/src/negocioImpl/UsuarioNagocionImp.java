package negocioImpl;

import Entidades.Cliente;
import dao.UsuarioDao;
import daoImp.UsuarioDaoImp;
import negocio.UsuarioNagocio;

public class UsuarioNagocionImp implements UsuarioNagocio {

	private UsuarioDao UsuarioDao = new UsuarioDaoImp();
	@Override
	public Cliente ObtenerDatosXidUsuario(int id) {
		return UsuarioDao.ObtenerDatosXidUsuario(id);
	}

}
