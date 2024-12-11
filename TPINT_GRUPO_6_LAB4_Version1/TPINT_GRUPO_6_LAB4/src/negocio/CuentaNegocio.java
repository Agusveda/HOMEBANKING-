package negocio;

import java.util.ArrayList;

import Entidades.Cuenta;
import Entidades.Prestamo;

public interface CuentaNegocio {
	public boolean insertCuenta(Cuenta cuenta);
	public boolean EliminarCuenta(int id);
	public ArrayList<Cuenta> ListarCuenta(int DNI);
	public ArrayList<Cuenta> ListarCuenta();
	public int GenerarNumeroCuenta() ;
	public int GenerarCBU();
	public boolean modificarCuenta(Cuenta cuenta);
	public Cuenta obtenerCuentaPorId(int id);
	public ArrayList<Cuenta> filtrarCuentaXTipoCuenta (int tipoCuenta);
	public Cuenta obtenerCuentaPorIdCliente(int id);
	public float ReporteCuentas(); 
	public boolean ExisteId (int id);
	
}
