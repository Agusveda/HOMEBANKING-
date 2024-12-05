package Entidades;

public class Localidad {
    private int idLocalidad;
    private String localidad;
    private int idProvincia;

    
    public Localidad(int idLocalidad, String localidad, int idProvincia) {
        this.idLocalidad = idLocalidad;
        this.localidad = localidad;
        this.idProvincia = idProvincia;
    }

   
    public Localidad() 
    {
    	idLocalidad=0;
    	localidad= "";
    	idProvincia=0;
    }


    public int getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(int idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }
}