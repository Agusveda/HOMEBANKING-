package Excepciones;

public class ClienteExcedeCantCuentas extends Exception {
	private static final long serialVersionUID = 1L;
	String mensajeDeError;
	
	
	public ClienteExcedeCantCuentas(String mensajeError) {
		super(mensajeError);
	}	
}