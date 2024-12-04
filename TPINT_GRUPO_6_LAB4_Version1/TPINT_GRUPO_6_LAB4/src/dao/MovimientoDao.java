package dao;

import java.util.ArrayList;

import Entidades.Cuenta;
import Entidades.Movimiento;
import Entidades.Prestamo;

public interface MovimientoDao 
{
	public int ObtenerIdCuentaPorCBU(int CBU);
	public boolean insertar(Movimiento movi, int idCue);
	public int ObtenerIdCuentaPorIdCliente(int IdCliente); 
	public ArrayList<Cuenta> TraeCuentasPorIdCliente(int idCliente);
	public float ObtenerSaldoPorIdCuenta(int idCue); 
	public ArrayList<Movimiento> ListarMovimientosPorCuenta(int idCue);
	public boolean ExisteCBU(int Cbu);
    public boolean insertarPrestamo(Prestamo prestamo);
    public ArrayList<Prestamo> ListPrestamosPedidos();
    public boolean actualizarConfirmacionPrestamo(int idPrestamo, int confirmacion);
    public ArrayList<Prestamo> ListPrestamosPedidosAutorizados();
    public ArrayList<Prestamo> filtrarClienteXImporte (String orden ); 
}