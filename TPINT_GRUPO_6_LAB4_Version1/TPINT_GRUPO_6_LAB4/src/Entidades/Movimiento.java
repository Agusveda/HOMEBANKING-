package Entidades;

public class Movimiento 
{
	private int id;
	private int TipoMovimiento;
	private String FechaMovimiento ;
	private float importe;
	private int idCuenta;
	private String detalle;
	
	/// NO ESTAN EN LA BASE DE DATOS 
	private boolean esPrestamo;  
    private int cantidadCuotas;   
    private float saldoPrestamo; 
	
	public Movimiento ()
	{
		id=0;
		TipoMovimiento = 0;
		FechaMovimiento = "";
		importe = 0;
		idCuenta  = 0;
		detalle  = "";
	}
	
	public Movimiento(int id, int tipoMovimiento, String fechaMovimiento, float importe, int idCuenta, String detalle) {
		super();
		this.id = id;
		TipoMovimiento = tipoMovimiento;
		FechaMovimiento = fechaMovimiento;
		this.importe = importe;
		this.idCuenta = idCuenta;
		this.detalle = detalle;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTipoMovimiento() {
		return TipoMovimiento;
	}
	public void setTipoMovimiento(int tipoMovimiento) {
		TipoMovimiento = tipoMovimiento;
	}
	public String getFechaMovimiento() {
		return FechaMovimiento;
	}
	public void setFechaMovimiento(String fechaMovimiento) {
		FechaMovimiento = fechaMovimiento;
	}
	public float getImporte() {
		return importe;
	}
	public void setImporte(float importe) {
		this.importe = importe;
	}
	public int getIdCuenta() {
		return idCuenta;
	}
	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	  public boolean isEsPrestamo() {
	        return esPrestamo;
	    }

	    public void setEsPrestamo(boolean esPrestamo) {
	        this.esPrestamo = esPrestamo;
	    }

	    public int getCantidadCuotas() {
	        return cantidadCuotas;
	    }

	    public void setCantidadCuotas(int cantidadCuotas) {
	        this.cantidadCuotas = cantidadCuotas;
	    }

	    public float getSaldoPrestamo() {
	        return saldoPrestamo;
	    }

	    public void setSaldoPrestamo(float saldoPrestamo) {
	        this.saldoPrestamo = saldoPrestamo;
	    }
}
