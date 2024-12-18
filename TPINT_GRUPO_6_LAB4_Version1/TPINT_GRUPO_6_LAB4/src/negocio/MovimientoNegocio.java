package negocio;

import java.util.ArrayList;
import Entidades.Cuenta;
import Entidades.Movimiento;
public interface MovimientoNegocio 
{
	int ObtenerIdCuentaPorIdCliente(int IdCliente); 
	int ObtenerIdCuentaPorCBU(int CBU);
	boolean insertar(Movimiento movi, int idCue);
	boolean insertarAltaCuenta(Movimiento movi, int idCue);
	ArrayList<Movimiento> ListarMovimientosPorCuenta(int idCue);
	float ObtenerSaldoPorIdCuenta(int idCue); 
	boolean ExisteCBU(int Cbu);
	float ReporteMovimiento(int TipoMovimiento, String FechaInicio, String FechaFinal); 
	ArrayList<Cuenta> TraeCuentasPorIdCliente(int idCliente);
    float EgresoDeCliente(int DNICLIENTE);
    float IngresoDeCliente(int DNICLIENTE);
    public boolean insertarMovimientosTransferencia(Movimiento movimiento, int idCuenta);
}