package Excepciones;

public class  DniRepetido extends Exception{

	private static final long serialVersionUID = 1L;
	
	String MensajeError;
	
	public DniRepetido(String mensajeError) {
		super();
		MensajeError = mensajeError;
	}

	public String getMensajeError() {
		return MensajeError;
	}

	public void setMensajeError() {
		MensajeError = "El DNI ya figura en la base de datos";
	}
	
	@Override
	public String toString() {
		return "UsuarioRepetido: " + getMessage();
	}
}
