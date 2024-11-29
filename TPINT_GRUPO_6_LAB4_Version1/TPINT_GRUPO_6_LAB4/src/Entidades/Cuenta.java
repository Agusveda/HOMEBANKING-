package Entidades;

public class Cuenta {
	private int id;
	private int idTipoMovimiento;
	private int idCliente;
	private int tipoCuenta;
	private String fechaCreacion;
	private int numeroCuenta;
	private int cbu;
	private float saldo;
	private boolean activo;
	private Cliente cliente; 
	
	public Cuenta()
	{
		id=0;
		idTipoMovimiento=0;
		idCliente=0;
		tipoCuenta=1;
		fechaCreacion = "";
		numeroCuenta=0;
		cbu=0;
		saldo=0;
		activo=true;
		cliente = new Cliente();	
	}

	public Cuenta(int id, int idTipoMovimiento, int idCliente, int tipoCuenta, String fechaCreacion, int numeroCuenta,
			int cbu, float saldo, boolean activo) {
		super();
		this.id = id;
		this.idTipoMovimiento = idTipoMovimiento;
		this.idCliente = idCliente;
		this.tipoCuenta = tipoCuenta;
		this.fechaCreacion = fechaCreacion;
		this.numeroCuenta = numeroCuenta;
		this.cbu = cbu;
		this.saldo = saldo;
		this.activo = activo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdTipoMovimiento() {
		return idTipoMovimiento;
	}

	public void setIdTipoMovimiento(int idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(int tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public int getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public int getCbu() {
		return cbu;
	}

	public void setCbu(int cbu) {
		this.cbu = cbu;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Cliente getCliente() {
	    return cliente;
	}

	public void setCliente(Cliente cliente) {
	    this.cliente = cliente;
	}
	@Override
	public String toString() {
		return "Cuenta [id=" + id + ", idTipoMovimiento=" + idTipoMovimiento + ", idCliente=" + idCliente
				+ ", tipoCuenta=" + tipoCuenta + ", fechaCreacion=" + fechaCreacion + ", numeroCuenta=" + numeroCuenta
				+ ", cbu=" + cbu + ", saldo=" + saldo + ", activo=" + activo + "]";
	};
}
