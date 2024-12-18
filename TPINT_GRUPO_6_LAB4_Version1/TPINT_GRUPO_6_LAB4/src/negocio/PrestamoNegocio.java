package negocio;

import java.util.ArrayList;
import java.util.List;
import Entidades.Cuota;
import Entidades.Prestamo;

public interface PrestamoNegocio {
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
	boolean solicitarPrestamo(Prestamo prestamo);
	List<Cuota> generarCuotas(Prestamo prestamo, float montoConInteres);
	boolean confirmarPrestamoConMovimiento(int idPrestamo, int idCuenta);
	boolean realizarPagoCuotaConMovimiento(int cuotaId, int cuentaId, float monto);
	boolean realizarPagoCuota(int cuotaId, int cuentaId, float monto);  
}
