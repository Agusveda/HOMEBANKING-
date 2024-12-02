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
                System.out.println("Pr�stamos autorizados encontrados:");
                for (Prestamo prestamo : prestamos) {
                	cont++;
                    System.out.println(prestamo.toString()); // Aseg�rate de tener un m�todo toString en Prestamo
                    System.out.println("Cantidad de pr�stamos encontrados: " + prestamos.size());
                }
            } else {
                System.out.println("No se encontraron pr�stamos autorizados.");
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar el m�todo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
        
        
      
