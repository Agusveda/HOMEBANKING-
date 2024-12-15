package negocioImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entidades.Cuota;
import Entidades.Prestamo;
import daoImp.PrestamoDaoImp;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImp implements PrestamoNegocio {
	private PrestamoDaoImp prestamoDao = new PrestamoDaoImp();
	

	//nuevo
	@Override
	public boolean solicitarPrestamo(Prestamo prestamo) {
	    boolean isInsertExitoso = false;

	    float tasaInteres = calcularTasaInteres(prestamo.getCantCuo());
	    if (tasaInteres == -1) {
	        System.out.println("Número de cuotas no válido.");
	        return false;
	    }

	    float montoConInteres = prestamo.getImporteCliente() * (1 + tasaInteres);

	    // Generamos las cuotas para el préstamo
	    List<Cuota> cuotas = generarCuotas(prestamo, montoConInteres);

	    // Realizamos la inserción del préstamo y las cuotas en el DAO
	    try {
	        isInsertExitoso = prestamoDao.insertarPrestamo(prestamo, cuotas);
	        if (isInsertExitoso) {
	            System.out.println("El préstamo y sus cuotas se han insertado correctamente.");
	        }
	    } catch (Exception e) {
	        System.out.println("Error al insertar el préstamo y las cuotas.");
	        e.printStackTrace();
	    }

	    return isInsertExitoso;
	}

	public List<Cuota> generarCuotas(Prestamo prestamo, float montoConInteres) {
	    List<Cuota> cuotas = new ArrayList<>();
	    
	    // Calculamos el monto de cada cuota
	    float montoCuota = montoConInteres / prestamo.getCantCuo();

	    // Generamos las cuotas
	    for (int i = 1; i <= prestamo.getCantCuo(); i++) {
	        Cuota cuota = new Cuota();
	        cuota.setNumeroCuota(i);
	        cuota.setMonto(montoCuota);
	        cuota.setFechaPago(LocalDate.now().plusMonths(i)); // Genera la fecha de pago para cada cuota
	        cuotas.add(cuota);
	    }
	    
	    return cuotas;
	}


	private float calcularTasaInteres(int cantidadCuotas) {
	    // Calcula la tasa de interés según la cantidad de cuotas
	    switch (cantidadCuotas) {
	        case 1: return 0.0f;   // Sin interés
	        case 3: return 0.05f;  // 5% interés
	        case 6: return 0.10f;  // 10% interés
	        case 9: return 0.15f;  // 15% interés
	        case 12: return 0.20f; // 20% interés
	        default:
	            System.out.println("Cantidad de cuotas no válida");
	            return -1; // Indicamos que la cantidad de cuotas no es válida
	    }
	}

	//fin nuevo
/*
	@Override
	public boolean insertarPrestamo(Prestamo prestamo) {
		return prestamoDao.insertarPrestamo(prestamo);
	}
*/
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
	
	public ArrayList<Prestamo> obtenerPrestamosEnEspera(int idCliente) {
		return prestamoDao.obtenerPrestamosEnEspera(idCliente);
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
