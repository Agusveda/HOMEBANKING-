package negocio;

import java.util.ArrayList;

import Entidades.Cuenta;

public interface CuentaNegocio {
	public boolean insertCuenta(Cuenta cuenta);
	public boolean EliminarCuenta(int id);
	public ArrayList<Cuenta> ListarCuenta(int DNI);
	public ArrayList<Cuenta> ListarCuenta();
	public int GenerarNumeroCuenta() ;
	public int GenerarCBU();
	public boolean modificarCuenta(Cuenta cuenta);
	public Cuenta obtenerCuentaPorId(int id);
}
