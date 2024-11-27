package dao;

import Entidades.Movimiento;

public interface MovimientoDao 
{
	public int ObtenerIdCuentaPorCBU(int CBU);
	public boolean insertar(Movimiento movi);
}
