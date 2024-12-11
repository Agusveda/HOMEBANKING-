package negocio;

import Entidades.Cliente;

public interface UsuarioNagocio {
	public Cliente ObtenerDatosXidUsuario(int id);
	public boolean EsAdmin (int idCliente);
}
