package Entidades;

public class Provincia {
    private int Id;
    private int IdNacionalidad;
    private String Provincia;

  
    public Provincia(int id, String Provincia) {
        this.Id = id;
        this.Provincia = Provincia;
    }
    public Provincia() {}

  
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getProvincia() {
        return Provincia;
    }

    public void setProvincia(String Provincia) {
        this.Provincia = Provincia;
    }
	public int getIdNacionalidad() {
		return IdNacionalidad;
	}
	public void setIdNacionalidad(int idNacionalidad) {
		IdNacionalidad = idNacionalidad;
	}
	
	@Override
    public String toString() {
        return Provincia; 
    }
}

