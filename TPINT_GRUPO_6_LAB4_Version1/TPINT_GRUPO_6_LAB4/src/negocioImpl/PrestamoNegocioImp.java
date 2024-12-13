package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import Entidades.Cuota;
import Entidades.Prestamo;
import daoImp.PrestamoDaoImp;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImp implements PrestamoNegocio {
	private PrestamoDaoImp prestamoDao = new PrestamoDaoImp();

	@Override
	public boolean insertarPrestamo(Prestamo prestamo) {
		return prestamoDao.insertarPrestamo(prestamo);
	}

	@Override
	public ArrayList<Prestamo> ListPrestamosPedidos() {
		return prestamoDao.ListPrestamosPedidos();
	}

	@Override
	public boolean actualizarConfirmacionPrestamo(int idPrestamo, int confirmacion) {
		return prestamoDao.actualizarConfirmacionPrestamo(idPrestamo,confirmacion);
	}

	@Override
	public ArrayList<Prestamo> ListPrestamosPedidosAutorizados() {
		
		return prestamoDao.ListPrestamosPedidosAutorizados();
	}
	@Override
	public boolean CargarPrestamoEnCuenta(int idcuenta, float monto) {
		return prestamoDao.CargarPrestamoEnCuenta(idcuenta,monto);
	}

	@Override
	public double obtenerTotalPrestamosConfirmados(int idCliente) {
		return prestamoDao.obtenerTotalPrestamosConfirmados(idCliente);
	}

	@Override
	public List<Prestamo> obtenerPrestamosConfirmados(int idCliente) {
		return prestamoDao.obtenerPrestamosConfirmados(idCliente);
	}

	@Override
	public List<Cuota> obtenerCuotas(int idCliente, int idPrestamo) {
		return prestamoDao.obtenerCuotas(idCliente,idPrestamo);
	}

	@Override
	public boolean realizarPagoCuota(int cuotaId, int cuentaId, float monto) {
		return prestamoDao.realizarPagoCuota(cuotaId,cuentaId,monto);
	}

	@Override
	public double obtenerSumaCuotasPendientes(int idCliente) {
		return prestamoDao.obtenerSumaCuotasPendientes(idCliente);
	}

	@Override
	public ArrayList<Prestamo> filtrarClienteXImporte(String orden) {
		return prestamoDao.filtrarClienteXImporte(orden);
	}

	@Override
	public ArrayList<Prestamo> filtrarClienteXImporteConfirmado(String orden) {
		return prestamoDao.filtrarClienteXImporteConfirmado(orden);
	}
	

}
