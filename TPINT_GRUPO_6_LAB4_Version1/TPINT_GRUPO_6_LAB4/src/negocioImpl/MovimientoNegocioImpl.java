package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import Entidades.Cuenta;
import Entidades.Cuota;
import Entidades.Movimiento;
import Entidades.Prestamo;
import dao.PrestamoDao;
import daoImp.MovimientoDaoImp;
import daoImp.PrestamoDaoImp;
import negocio.MovimientoNegocio;

public class MovimientoNegocioImpl implements MovimientoNegocio
{
	private MovimientoDaoImp movimientoDao = new MovimientoDaoImp();
	
	
	@Override
	public int ObtenerIdCuentaPorIdCliente(int IdCliente) {

		return movimientoDao.ObtenerIdCuentaPorIdCliente(IdCliente);
	}
	@Override
	public int ObtenerIdCuentaPorCBU(int CBU) {
		return movimientoDao.ObtenerIdCuentaPorCBU(CBU);
	}

	@Override
	public ArrayList<Movimiento> ListarMovimientosPorCuenta(int idCue) {
		return movimientoDao.ListarMovimientosPorCuenta(idCue);
	}
	@Override
	public float ObtenerSaldoPorIdCuenta(int idCue) {
		return movimientoDao.ObtenerSaldoPorIdCuenta(idCue);
	}
	@Override
	public boolean ExisteCBU(int Cbu) {
		return movimientoDao.ExisteCBU(Cbu);
	}
	@Override
	public float ReporteMovimiento(int TipoMovimiento, String FechaInicio, String FechaFinal) {
		return movimientoDao.ReporteMovimiento(TipoMovimiento,FechaInicio,FechaFinal);
	}
	@Override
	public ArrayList<Cuenta> TraeCuentasPorIdCliente(int idCliente) {
		return movimientoDao.TraeCuentasPorIdCliente(idCliente);
	}
	@Override
	public float EgresoDeCliente(int DNICLIENTE) {
		return movimientoDao.EgresoDeCliente(DNICLIENTE);
	}
	@Override
	public float IngresoDeCliente(int DNICLIENTE) {
		return movimientoDao.IngresoDeCliente(DNICLIENTE);
	}
	@Override
	public boolean insertar(Movimiento movi, int idCue) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean insertarAltaCuenta(Movimiento movi, int idCue) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean insertarMovimientosTransferencia(Movimiento movimiento, int idCuenta) {
		return movimientoDao.insertarMovimientosTransferencia(movimiento, idCuenta);
	}

}