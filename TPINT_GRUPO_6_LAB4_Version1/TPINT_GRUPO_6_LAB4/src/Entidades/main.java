package Entidades;

import negocioImpl.ClienteNegocioImpl;

public class main {

	public static void main(String[] args) {
		ClienteNegocioImpl clienteNegocio = new ClienteNegocioImpl();
        String email = "elpityloco@gmail.com"; // Cambia esto a un correo existente o no existente en tu base de datos
        /*
        if (clienteNegocio.existeEmail(email)) {
            System.out.println("El correo existe en la base de datos.");
        } else {
            System.out.println("El correo NO existe en la base de datos.");
        }
        */
       
        String nuevaContrasena = "NuevaPassword123";

        boolean resultado = clienteNegocio.actualizarContrasenaPorEmail(email, nuevaContrasena);

        if (resultado) {
            System.out.println("Contraseña actualizada correctamente.");
        } else {
            System.out.println("No se pudo actualizar la contraseña.");
        }
    }


}
