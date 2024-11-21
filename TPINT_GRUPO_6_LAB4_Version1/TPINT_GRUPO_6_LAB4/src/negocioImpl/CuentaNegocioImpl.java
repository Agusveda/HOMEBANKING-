package negocioImpl;

import java.util.ArrayList;

import Entidades.Cuenta;
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
	
}
