package Entidades;

import java.sql.Date;

public class Prestamo {
	private int Id;
	private int IdCliente;
	private float ImporteCliente;
	private Date fechaAlta;
	private int plazoPago;
	private float Impxmes;
	private int cantCuo;
	private boolean confimacion;
	
	public Prestamo () {}
	
	
	
	public Prestamo(int id, int idCliente, float importeCliente, Date fechaAlta, int plazoPago, float impxmes,int cantCuo, boolean confimacion) {
		Id = id;
		IdCliente = idCliente;
		ImporteCliente = importeCliente;
		this.fechaAlta = fechaAlta;
		this.plazoPago = plazoPago;
		Impxmes = impxmes;
		this.cantCuo = cantCuo;
		this.confimacion = confimacion;
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
	public Boolean getConfimarcion() {
		return confimacion;
	}
	public void setconfimacion(Boolean confimacion) {
		this.confimacion = confimacion;
	}
}
