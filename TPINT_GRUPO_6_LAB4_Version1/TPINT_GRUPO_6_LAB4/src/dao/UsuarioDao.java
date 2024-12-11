package dao;

import Entidades.Cliente;

public interface UsuarioDao 
{
	public Cliente ObtenerDatosXidUsuario(int id);
	
	public boolean EsAdmin (int idCliente);
	
}
