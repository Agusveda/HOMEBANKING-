package negocioImpl;

import java.util.ArrayList;

import Entidades.Cuenta;
import Entidades.Movimiento;
import Entidades.Prestamo;
import daoImp.MovimientoDaoImp;
import negocio.MovimientoNegocio;

public class MovimientoNegocioImpl implements MovimientoNegocio
{
	
	private MovimientoDaoImp MovimientoDao = new MovimientoDaoImp();

	@Override
	public boolean insertar(Movimiento movi, int idCue) 
	{
		return MovimientoDao.insertar(movi, idCue);
	}

	@Override
	public int ObtenerIdCuentaPorCBU(int CBU) {
		
		return MovimientoDao.ObtenerIdCuentaPorCBU(CBU);
		
	}

	@Override
	public int ObtenerIdCuentaPorIdCliente(int IdCliente) {
		return MovimientoDao.ObtenerIdCuentaPorIdCliente(IdCliente);
	}

	@Override
	public ArrayList<Cuenta> TraeCuentasPorIdCliente(int idCliente) {
		
		
		return MovimientoDao.TraeCuentasPorIdCliente(idCliente);
	}

	@Override
	public float ObtenerSaldoPorIdCuenta(int idCue) {
		return MovimientoDao.ObtenerSaldoPorIdCuenta(idCue);
	}

	@Override
	public ArrayList<Movimiento> ListarMovimientosPorCuenta(int idCue) {
		return MovimientoDao.ListarMovimientosPorCuenta(idCue);
	}

	@Override
	public boolean ExisteCBU(int Cbu) {
		return MovimientoDao.ExisteCBU(Cbu);
	}

	@Override
	public ArrayList<Prestamo> ListPrestamosPedidos() {
		return MovimientoDao.ListPrestamosPedidos();
	}

	@Override
	public boolean actualizarConfirmacionPrestamo(int idPrestamo, int confirmacion) {
		return MovimientoDao.actualizarConfirmacionPrestamo(idPrestamo, confirmacion);
	}

	@Override
	public ArrayList<Prestamo> ListPrestamosPedidosAutorizados() {
		return MovimientoDao.ListPrestamosPedidosAutorizados();
	}

	@Override
	public ArrayList<Prestamo> filtrarClienteXImporte(String orden) {
		return MovimientoDao.filtrarClienteXImporte(orden);
	}

	@Override
	public ArrayList<Prestamo> filtrarClienteXImporteConfirmado(String orden) {
		return MovimientoDao.filtrarClienteXImporteConfirmado(orden);
	}



}