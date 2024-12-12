package dao;

import java.util.ArrayList;
import java.util.List;

import Entidades.Cuenta;
import Entidades.Cuota;
import Entidades.Movimiento;
import Entidades.Prestamo;


public interface MovimientoDao 
{
	public int ObtenerIdCuentaPorCBU(int CBU);
	public boolean insertar(Movimiento movi, int idCue);
	public boolean insertarAltaCuenta(Movimiento movi, int idCue);
	public int ObtenerIdCuentaPorIdCliente(int IdCliente); 
	public ArrayList<Cuenta> TraeCuentasPorIdCliente(int idCliente);
	public float ObtenerSaldoPorIdCuenta(int idCue); 
	public ArrayList<Movimiento> ListarMovimientosPorCuenta(int idCue);
	public boolean ExisteCBU(int Cbu);
	
	///PRESTAMOS
    public boolean insertarPrestamo(Prestamo prestamo);
    public ArrayList<Prestamo> ListPrestamosPedidos();
    public boolean actualizarConfirmacionPrestamo(int idPrestamo, int confirmacion);
    public ArrayList<Prestamo> ListPrestamosPedidosAutorizados();
    public ArrayList<Prestamo> filtrarClienteXImporte (String orden); 
    public ArrayList<Prestamo> filtrarClienteXImporteConfirmado (String orden);     
    public boolean CargarPrestamoEnCuenta(int idcuenta, float monto);
    public double obtenerTotalPrestamosConfirmados(int idCliente);
    
	List<Prestamo> obtenerPrestamosConfirmados(int idCliente);
	public List<Cuota> obtenerCuotas(int idCliente, int idPrestamo);
	public boolean realizarPagoCuota(int cuotaId, int cuentaId, float monto);
	public double obtenerSumaCuotasPendientes(int idCliente);

	
	
    ///REPORTE DE MOVIMIENTO
    public float ReporteMovimiento(int TipoMovimiento, String FechaInicio, String FechaFinal); 
    
    // REPORTE DE TRANSFERENCIAS
    public float EgresoDeCliente(int DNICLIENTE);
    public float IngresoDeCliente(int DNICLIENTE);
    

}