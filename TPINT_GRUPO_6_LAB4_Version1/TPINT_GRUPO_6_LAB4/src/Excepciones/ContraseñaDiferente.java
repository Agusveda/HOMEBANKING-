package Excepciones;

public class ContraseņaDiferente extends Exception{
	public ContraseņaDiferente() {
		
	}

	@Override
	public String getMessage() {
		
		return "Las contraseņas no son iguales!";
	}
}
