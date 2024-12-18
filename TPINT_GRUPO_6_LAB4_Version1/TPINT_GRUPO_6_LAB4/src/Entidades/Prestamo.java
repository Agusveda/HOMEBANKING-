package Entidades;

import java.sql.Date;

public class Prestamo {
	private int Id;
	private int IdCliente;
	private int IdCuenta;
	private float ImporteCliente;
	private Date fechaAlta;
	private int plazoPago;
	private float Impxmes;
	private int cantCuo;
	private int confirmacion;
	
	public Prestamo () 
	{
		Id=0;
		IdCliente=0;
		ImporteCliente=0;
		plazoPago=0;
		Impxmes=0;
		cantCuo=0;	
	}
	
	public Prestamo(int id, int idCliente, float importeCliente, Date fechaAlta, int plazoPago, float impxmes,int cantCuo, int confirmacion) {
		Id = id;
		IdCliente = idCliente;
		ImporteCliente = importeCliente;
		this.fechaAlta = fechaAlta;
		this.plazoPago = plazoPago;
		Impxmes = impxmes;
		this.cantCuo = cantCuo;
		this.confirmacion = confirmacion;
	}


	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getIdCliente() {
		return IdCliente;
	}
	public void setIdCliente(int idCliente) {
		IdCliente = idCliente;
	}
	
	 public int getIdCuenta() {
	        return IdCuenta;
	}
	 
	public void setIdCuenta(int idCuenta) {
	        IdCuenta = idCuenta;
	}
	
	public float getImporteCliente() {
		return ImporteCliente;
	}
	public void setImporteCliente(float importeCliente) {
		ImporteCliente = importeCliente;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public int getPlazoPago() {
		return plazoPago;
	}
	public void setPlazoPago(int plazoPago) {
		this.plazoPago = plazoPago;
	}
	public float getImpxmes() {
		return Impxmes;
	}
	public void setImpxmes(float impxmes) {
		Impxmes = impxmes;
	}
	public int getCantCuo() {
		return cantCuo;
	}
	public void setCantCuo(int cantCuo) {
		this.cantCuo = cantCuo;
	}
	public int getConfimarcion() {
		return confirmacion;
	}

	public void setconfimacion(int confirmacion) {
		this.confirmacion = confirmacion;
	}

	@Override
	public String toString() {
		return "Prestamo [Id=" + Id + ", IdCliente=" + IdCliente + ", ImporteCliente=" + ImporteCliente + ", fechaAlta="
				+ fechaAlta + ", plazoPago=" + plazoPago + ", Impxmes=" + Impxmes + ", cantCuo=" + cantCuo
				+ ", confimacion=" + confirmacion + "]";
	}
}
