package Entidades;

import java.util.ArrayList;
import negocioImpl.MovimientoNegocioImpl;

public class main {

	public static void main(String[] args) {
		
        MovimientoNegocioImpl mov = new MovimientoNegocioImpl();
        int cont=0;
   
        try {
        	 ArrayList<Prestamo> prestamos = mov.ListPrestamosPedidosAutorizados();

            if (prestamos != null && !prestamos.isEmpty()) {
                System.out.println("Préstamos autorizados encontrados:");
                for (Prestamo prestamo : prestamos) {
                	cont++;
                    System.out.println(prestamo.toString()); // Asegúrate de tener un método toString en Prestamo
                    System.out.println("Cantidad de préstamos encontrados: " + prestamos.size());
                }
            } else {
                System.out.println("No se encontraron préstamos autorizados.");
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar el método: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
        
        
      
