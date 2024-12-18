package dao;

import java.util.ArrayList;
import java.util.List;

import Entidades.Cuenta;
import Entidades.Cuota;
import Entidades.Prestamo;

public interface PrestamoDao {
    ArrayList<Prestamo> ListPrestamosPedidos();
    boolean actualizarConfirmacionPrestamo(int idPrestamo, int confirmacion);
    ArrayList<Prestamo> ListPrestamosPedidosAutorizados();
    boolean CargarPrestamoEnCuenta(int idcuenta, float monto);
    double obtenerTotalPrestamosConfirmados(int idCliente);
	List<Prestamo> obtenerPrestamosConfirmados(int idCliente);
	List<Cuota> obtenerCuotas(int idCliente, int idPrestamo);
	double obtenerSumaCuotasPendientes(int idCliente);
	ArrayList<Prestamo> filtrarClienteXImporte (String orden); 
	ArrayList<Prestamo> filtrarClienteXImporteConfirmado (String orden);
	ArrayList<Prestamo> obtenerPrestamosEnEspera(int idCliente);  	
	boolean insertarPrestamo(Prestamo prestamo, List<Cuota> cuotas);
	boolean confirmacionPrestamo(int idPrestamo);
	boolean realizarPagoCuota(int cuotaId, int cuentaId, float monto);
	boolean actualizarEstadoCuota(int cuotaId, boolean estaPagada);
	boolean denegarPrestamo(int idPrestamo);
}
