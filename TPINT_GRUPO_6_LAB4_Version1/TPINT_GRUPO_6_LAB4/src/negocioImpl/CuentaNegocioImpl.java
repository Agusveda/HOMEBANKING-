package negocioImpl;

import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Cuenta;
import Entidades.Prestamo;
import Excepciones.ClienteExcedeCantCuentas;
import dao.CuentaDao;
import daoImp.CuentaDaoImpl;
import negocio.CuentaNegocio;

public class CuentaNegocioImpl implements CuentaNegocio {
	
	private CuentaDaoImpl cuentaDao = new CuentaDaoImpl();

	@Override
	public boolean insertCuenta(Cuenta cuenta) {
		return cuentaDao.insertCuenta(cuenta);
	}

	
	@Override
	public boolean EliminarCuenta(int id) {
		return cuentaDao.EliminarCuenta(id);
	}

	@Override
	public ArrayList<Cuenta> ListarCuenta(int DNI) {
		return (ArrayList<Cuenta>)cuentaDao.ListarCuenta(DNI);
	}

	@Override
	public ArrayList<Cuenta> ListarCuenta() {
		return (ArrayList<Cuenta>)cuentaDao.ListarCuenta();
	}

	@Override
	public int GenerarNumeroCuenta() {
		return cuentaDao.GenerarNumeroCuenta();
	}

	@Override
	public int GenerarCBU() {	
		return cuentaDao.GenerarCBU();
	}

	@Override
	public boolean modificarCuenta(Cuenta cuenta) {	
		return cuentaDao.modificarCuenta(cuenta);
	}

	@Override
	public Cuenta obtenerCuentaPorId(int id) {
		
		return cuentaDao.obtenerCuentaPorId(id);
	}
	

	@Override
	public ArrayList<Cuenta> filtrarCuentaXTipoCuenta(int tipoCuenta) {
		return cuentaDao.filtrarCuentaXTipoCuenta(tipoCuenta);

	}


	@Override
	public Cuenta obtenerCuentaPorIdCliente(int id) {
		
		return cuentaDao.obtenerCuentaPorIdCliente(id);
	}


	@Override
	public float ReporteCuentas() {
		return cuentaDao.ReporteCuentas();
	}


	@Override
	public boolean ExisteId(int id) 
	{
		return cuentaDao.ExisteId(id);
	}


	@Override
	public int ObtenerProximoIdCuenta() {
		return cuentaDao.ObtenerProximoIdCuenta();
	}


	


	@Override
	public int ClienteInactivo(int idCliente) {
		return cuentaDao.ClienteInactivo(idCliente);
	}


	@Override
	public int CuentasPorCliente(int idCliente) throws ClienteExcedeCantCuentas {
		return cuentaDao.CuentasPorCliente(idCliente);
	}


	

	
}
