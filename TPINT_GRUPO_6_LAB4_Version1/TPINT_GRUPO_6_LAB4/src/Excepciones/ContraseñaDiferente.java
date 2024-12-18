package Excepciones;

public class ContraseñaDiferente extends Exception{
	private static final long serialVersionUID = 1L;
	public ContraseñaDiferente() {	
	}

	@Override
	public String getMessage() {
		return "Las contraseñas no son iguales!";
	}
}

