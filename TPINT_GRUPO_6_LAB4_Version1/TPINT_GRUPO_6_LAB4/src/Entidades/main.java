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
            System.out.println("Pr�stamos ordenados de mayor a menor:");
            prestamosMayor.forEach(System.out::println);

            ArrayList<Prestamo> prestamosMenor = mov.filtrarClienteXImporte("Menor");
            System.out.println("Pr�stamos ordenados de menor a mayor:");
            prestamosMenor.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Ocurri� un error al filtrar los pr�stamos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
        
        
      
