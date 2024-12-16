package negocioImpl;

import java.util.ArrayList;

import Entidades.Cliente;
import Entidades.Cuenta;
import Entidades.Movimiento;
import Entidades.Prestamo;
import Excepciones.ClienteExcedeCantCuentas;
import dao.CuentaDao;
import dao.MovimientoDao;
import daoImp.CuentaDaoImpl;
import daoImp.MovimientoDaoImp;
import negocio.CuentaNegocio;

public class CuentaNegocioImpl implements CuentaNegocio {
	private CuentaDaoImpl cuentaDao = new CuentaDaoImpl();
	private MovimientoDaoImp movimientoDao = new MovimientoDaoImp();
	
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
	public void verificarCuentasPorCliente(int idCliente) throws ClienteExcedeCantCuentas 
	{
		cuentaDao.verificarCuentasPorCliente(idCliente);
		
	}

	@Override
	public boolean insertarCuentaConMovimiento(Cuenta cuenta, Movimiento movimiento) {
	    boolean exito = false;

	    try {
	        // 1. Insertar la cuenta y actualizar su ID
	        boolean cuentaInsertada = cuentaDao.insertCuenta(cuenta);

	        if (cuentaInsertada) {
	            System.out.println("ID de la cuenta después de la inserción: " + cuenta.getId());

	            // 2. Verificar si el ID es válido
	            if (cuenta.getId() > 0) {
	                // 3. Crear el movimiento para registrar el alta de la cuenta
	                movimiento.setDetalle("Alta de Cuenta");
	                movimiento.setTipoMovimiento(1); // Tipo 1: Alta de cuenta	         
	                // 4. Registrar el movimiento asociado a la cuenta
	                boolean movimientoInsertado = movimientoDao.insertarMovimientoAltaCuenta(movimiento, cuenta.getId());

	                if (movimientoInsertado) {
	                    System.out.println("La cuenta y el movimiento se procesaron exitosamente.");
	                    exito = true;
	                } else {
	                    System.out.println("Error al registrar el movimiento asociado.");
	                }
	            } else {
	                System.out.println("El ID de la cuenta no es válido después de la inserción.");
	            }
	        } else {
	            System.out.println("Error al insertar la cuenta.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return exito;
	}	
}
