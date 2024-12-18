package dao;

import java.util.ArrayList;
import java.util.List;

import Entidades.Cuenta;
import Entidades.Cuota;
import Entidades.Movimiento;
import Entidades.Prestamo;

public interface MovimientoDao {
	int ObtenerIdCuentaPorIdCliente(int IdCliente); 
	int ObtenerIdCuentaPorCBU(int CBU);
	ArrayList<Movimiento> ListarMovimientosPorCuenta(int idCue);
	float ObtenerSaldoPorIdCuenta(int idCue); 
	boolean ExisteCBU(int Cbu);
	float ReporteMovimiento(int TipoMovimiento, String FechaInicio, String FechaFinal); 
	ArrayList<Cuenta> TraeCuentasPorIdCliente(int idCliente);
    float EgresoDeCliente(int DNICLIENTE);
    float IngresoDeCliente(int DNICLIENTE);
    boolean insertarMovimientoAltaCuenta(Movimiento movimiento, int idCuenta);
    boolean insertarMovimientoAltaPrestamoConfirmado(Movimiento movimiento, int idCuenta);
    boolean insertarMovimientosTransferencia(Movimiento movimiento, int idCuenta);
	boolean insertarMovimientoPagoCuota(Movimiento movimiento, int idCuenta);  
}