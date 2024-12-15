package negocio;

import java.util.ArrayList;
import java.util.List;

import Entidades.Cuenta;
import Entidades.Cuota;
import Entidades.Prestamo;

public interface PrestamoNegocio {
    boolean insertarPrestamo(Prestamo prestamo);
    ArrayList<Prestamo> ListPrestamosPedidos();
    boolean actualizarConfirmacionPrestamo(int idPrestamo, int confirmacion);
    ArrayList<Prestamo> ListPrestamosPedidosAutorizados();
    boolean CargarPrestamoEnCuenta(int idcuenta, float monto);
    double obtenerTotalPrestamosConfirmados(int idCliente);
	List<Prestamo> obtenerPrestamosConfirmados(int idCliente);
	List<Cuota> obtenerCuotas(int idCliente, int idPrestamo);
	boolean realizarPagoCuota(int cuotaId, int cuentaId, float monto);
	double obtenerSumaCuotasPendientes(int idCliente);
	ArrayList<Prestamo> filtrarClienteXImporte (String orden); 
	ArrayList<Prestamo> filtrarClienteXImporteConfirmado (String orden);  
	ArrayList<Prestamo> obtenerPrestamosEnEspera(int idCliente);  

}
