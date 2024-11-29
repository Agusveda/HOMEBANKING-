package negocio;

import java.util.ArrayList;

import Entidades.Cuenta;
import Entidades.Movimiento;

public interface MovimientoNegocio 
{
	
	public int ObtenerIdCuentaPorCBU (int CBU);
	public boolean insertar(Movimiento movi, int idCue);
	public int ObtenerIdCuentaPorIdCliente(int IdCliente); 
	public ArrayList<Cuenta> TraeCuentasPorIdCliente(int idCliente);
	public float ObtenerSaldoPorIdCuenta(int idCue);
}