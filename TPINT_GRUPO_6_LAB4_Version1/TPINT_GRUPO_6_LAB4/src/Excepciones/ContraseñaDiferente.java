package Excepciones;

public class ContraseņaDiferente extends Exception{
	private static final long serialVersionUID = 1L;
	public ContraseņaDiferente() {	
	}

	@Override
	public String getMessage() {
		return "Las contraseņas no son iguales!";
	}
}

