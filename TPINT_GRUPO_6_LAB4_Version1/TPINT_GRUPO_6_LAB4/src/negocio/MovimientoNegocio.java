package negocio;

import Entidades.Movimiento;

public interface MovimientoNegocio 
{
	
	public int ObtenerIdCuentaPorCBU (int CBU);
	public boolean insertar(Movimiento movi, int idCue);
	public int ObtenerIdCuentaPorIdCliente(int IdCliente); 
}