package negocio;

import java.util.ArrayList;

import Entidades.Cuenta;
import Entidades.Movimiento;
import Entidades.Prestamo;

public interface MovimientoNegocio 
{
	
	public int ObtenerIdCuentaPorCBU (int CBU);
	public boolean insertar(Movimiento movi, int idCue);
	public boolean insertarAltaCuenta(Movimiento movi, int idCue);
	public int ObtenerIdCuentaPorIdCliente(int IdCliente); 
	public ArrayList<Cuenta> TraeCuentasPorIdCliente(int idCliente);
	public float ObtenerSaldoPorIdCuenta(int idCue);
	public ArrayList<Movimiento> ListarMovimientosPorCuenta(int idCue);
	public boolean ExisteCBU(int Cbu);
	public ArrayList<Prestamo> ListPrestamosPedidos();
	public boolean actualizarConfirmacionPrestamo(int idPrestamo, int confirmacion);
	public ArrayList<Prestamo> ListPrestamosPedidosAutorizados();
	public ArrayList<Prestamo> filtrarClienteXImporte (String orden ); 
	public ArrayList<Prestamo> filtrarClienteXImporteConfirmado (String orden );
	
	///REPORTES
	public float ReporteMovimiento(int TipoMovimiento, String FechaInicio, String FechaFinal); 
	
    // REPORTE DE TRANSFERENCIAS
    public float EgresoDeCliente(int IdCliente);
    public float IngresoDeCliente(int IdCliente);
}