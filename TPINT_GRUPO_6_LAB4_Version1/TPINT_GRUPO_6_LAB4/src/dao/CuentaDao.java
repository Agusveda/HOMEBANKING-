package dao;

import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Cuenta;
import Excepciones.ClienteExcedeCantCuentas;

public interface CuentaDao {
	public boolean insertCuenta(Cuenta cuenta);
	public boolean EliminarCuenta(int id);
	public ArrayList<Cuenta> ListarCuenta(int DNI);
	public ArrayList<Cuenta> ListarCuenta();
	public int GenerarNumeroCuenta() ;
	public int GenerarCBU();
	public boolean modificarCuenta(Cuenta cuenta);
	public Cuenta obtenerCuentaPorId(int id);
	public Cuenta obtenerCuentaPorIdCliente(int id);
	public ArrayList<Cuenta> filtrarCuentaXTipoCuenta (int tipoCuenta);
	public boolean ExisteId (int id);
	public int ObtenerProximoIdCuenta();
	public void verificarCuentasPorCliente(int idCliente) throws ClienteExcedeCantCuentas;
	public int ClienteInactivo(int idCliente);
	public float ReporteCuentas(); 
}

