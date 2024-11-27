package dao;

import Entidades.Movimiento;

public interface MovimientoDao 
{
	public int ObtenerIdCuentaPorCBU(int CBU);
	public boolean insertar(Movimiento movi, int idCue);
	public int ObtenerIdCuentaPorIdCliente(int IdCliente); 
}