package Entidades;

public class Usuario {
	private String usuario;
	private String contrase�a;
	private int tipoUsuario;
    private boolean Activo;

	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrase�a() {
		return contrase�a;
	}
	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}
	public int getTipoUsuario() {
	    return tipoUsuario;
	}

	public void setTipoUsuario(int tipoUsuario) {
	    this.tipoUsuario = tipoUsuario;
	}
	public boolean getActivo() {
		return Activo;
	}
	
	public void setActivo(boolean Activo) {
	    this.Activo = Activo;
	}
	
}
