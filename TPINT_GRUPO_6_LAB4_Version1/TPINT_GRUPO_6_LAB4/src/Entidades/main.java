package Entidades;

import java.util.ArrayList;
import negocioImpl.MovimientoNegocioImpl;
import Entidades.Prestamo;

public class main {

	public static void main(String[] args) {
		
        MovimientoNegocioImpl mov = new MovimientoNegocioImpl();
        int cont=0;
        try {
            ArrayList<Prestamo> prestamosMayor = mov.filtrarClienteXImporte("Mayor");
            System.out.println("Préstamos ordenados de mayor a menor:");
            prestamosMayor.forEach(System.out::println);

            ArrayList<Prestamo> prestamosMenor = mov.filtrarClienteXImporte("Menor");
            System.out.println("Préstamos ordenados de menor a mayor:");
            prestamosMenor.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al filtrar los préstamos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
        
        
      
