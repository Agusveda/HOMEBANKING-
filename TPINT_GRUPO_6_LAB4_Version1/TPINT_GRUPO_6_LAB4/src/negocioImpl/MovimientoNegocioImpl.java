package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import Entidades.Cuenta;
import Entidades.Cuota;
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

	@Override
	public float ReporteMovimiento(int TipoMovimiento, String FechaInicio, String FechaFinal) {
		return MovimientoDao.ReporteMovimiento(TipoMovimiento, FechaInicio, FechaFinal);
	}

	@Override
	public float EgresoDeCliente(int DNICLIENTE) {
		return MovimientoDao.EgresoDeCliente(DNICLIENTE);
	}

	@Override
	public float IngresoDeCliente(int DNICLIENTE) {
		return MovimientoDao.IngresoDeCliente(DNICLIENTE);

	}

	@Override
	public boolean insertarAltaCuenta(Movimiento movi, int idCue) {
		return MovimientoDao.insertarAltaCuenta(movi, idCue);
	}

	@Override
	public boolean insertarPrestamo(Prestamo prestamo) {
		
		return MovimientoDao.insertarPrestamo(prestamo);
	}

	@Override
	public boolean CargarPrestamoEnCuenta(int idcuenta, float monto) {
		
		return CargarPrestamoEnCuenta(idcuenta, monto);
	}

	@Override
	public double obtenerTotalPrestamosConfirmados(int idCliente) {
		
		return obtenerTotalPrestamosConfirmados(idCliente);
	}

	@Override
	public List<Prestamo> obtenerPrestamosConfirmados(int idCliente) {

		return obtenerPrestamosConfirmados(idCliente);
	}

	@Override
	public List<Cuota> obtenerCuotas(int idCliente, int idPrestamo) {
	
		return obtenerCuotas(idCliente, idPrestamo);
	}

	@Override
	public boolean realizarPagoCuota(int cuotaId, int cuentaId, float monto) {
		
		return realizarPagoCuota(cuotaId, cuentaId,monto) ;
	}

	@Override
	public double obtenerSumaCuotasPendientes(int idCliente) {

		return obtenerSumaCuotasPendientes(idCliente);
	}
}