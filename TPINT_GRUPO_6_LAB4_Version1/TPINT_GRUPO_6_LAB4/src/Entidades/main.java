package Entidades;

import java.util.ArrayList;
import negocioImpl.MovimientoNegocioImpl;
import Entidades.Prestamo;

public class main {

	public static void main(String[] args) {
		
		MovimientoNegocioImpl mov = new MovimientoNegocioImpl();
	    int cont = 0;

	    try {
	        System.out.println("Intentando filtrar pr�stamos de mayor a menor...");
	        ArrayList<Prestamo> prestamosMayor = mov.filtrarClienteXImporteConfirmado("Mayor");
	        System.out.println("Pr�stamos ordenados de mayor a menor:");
	        prestamosMayor.forEach(System.out::println);
	        cont++;

	        System.out.println("Intentando filtrar pr�stamos de menor a mayor...");
	        ArrayList<Prestamo> prestamosMenor = mov.filtrarClienteXImporteConfirmado("Menor");
	        System.out.println("Pr�stamos ordenados de menor a mayor:");
	        prestamosMenor.forEach(System.out::println);
	        cont++;
	    } catch (Exception e) {
	        System.err.println("Ocurri� un error al filtrar los pr�stamos: " + e.getMessage());
	        e.printStackTrace();
	    }

	    System.out.println("Total de consultas ejecutadas: " + cont);
    }
}
     
        
      
