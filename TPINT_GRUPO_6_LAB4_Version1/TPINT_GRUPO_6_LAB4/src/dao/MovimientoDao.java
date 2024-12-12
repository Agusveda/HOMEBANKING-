package dao;

import java.util.ArrayList;
import java.util.List;

import Entidades.Cuenta;
import Entidades.Cuota;
import Entidades.Movimiento;
import Entidades.Prestamo;


public interface MovimientoDao 
{
	int ObtenerIdCuentaPorCBU(int CBU);
	boolean insertar(Movimiento movi, int idCue);
	boolean insertarAltaCuenta(Movimiento movi, int idCue);
	int ObtenerIdCuentaPorIdCliente(int IdCliente); 
	ArrayList<Cuenta> TraeCuentasPorIdCliente(int idCliente);
	float ObtenerSaldoPorIdCuenta(int idCue); 
	ArrayList<Movimiento> ListarMovimientosPorCuenta(int idCue);
	boolean ExisteCBU(int Cbu);
    boolean insertarPrestamo(Prestamo prestamo);
    ArrayList<Prestamo> ListPrestamosPedidos();
    boolean actualizarConfirmacionPrestamo(int idPrestamo, int confirmacion);
    ArrayList<Prestamo> ListPrestamosPedidosAutorizados();
    ArrayList<Prestamo> filtrarClienteXImporte (String orden); 
    ArrayList<Prestamo> filtrarClienteXImporteConfirmado (String orden);     
    boolean CargarPrestamoEnCuenta(int idcuenta, float monto);
    double obtenerTotalPrestamosConfirmados(int idCliente);
	List<Prestamo> obtenerPrestamosConfirmados(int idCliente);
	List<Cuota> obtenerCuotas(int idCliente, int idPrestamo);
	boolean realizarPagoCuota(int cuotaId, int cuentaId, float monto);
	double obtenerSumaCuotasPendientes(int idCliente);
    float ReporteMovimiento(int TipoMovimiento, String FechaInicio, String FechaFinal); 
    float EgresoDeCliente(int DNICLIENTE);
    float IngresoDeCliente(int DNICLIENTE);
}