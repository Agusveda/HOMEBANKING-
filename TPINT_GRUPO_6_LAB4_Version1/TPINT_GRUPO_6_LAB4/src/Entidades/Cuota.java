package Entidades;

import java.sql.Date;

public class Cuota {
    private int Id;
    private int IdPrestamo;
    private int NumeroCuota;
    private double Monto; 
    private Date FechaPago;
    private boolean estaPagada; // Nuevo atributo

    public Cuota() {
        Id = 0;
        IdPrestamo = 0;
        NumeroCuota = 0;
        Monto = 0;
        FechaPago = null;
        estaPagada = false; // Por defecto, no está pagada
    }

    public Cuota(int id, int idPrestamo, int numeroCuota, float monto, Date fechaPago, boolean estaPagada) {
        Id = id;
        IdPrestamo = idPrestamo;
        NumeroCuota = numeroCuota;
        Monto = monto;
        FechaPago = fechaPago;
        this.estaPagada = estaPagada;
    }

    // Getters y Setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdPrestamo() {
        return IdPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        IdPrestamo = idPrestamo;
    }

    public int getNumeroCuota() {
        return NumeroCuota;
    }

    public void setNumeroCuota(int numeroCuota) {
        NumeroCuota = numeroCuota;
    }

    public double getMonto() {
        return Monto;
    }

    public void setMonto(double monto) {
        Monto = monto;
    }

    public Date getFechaPago() {
        return FechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        FechaPago = fechaPago;
    }

    public boolean isPagada() {
        return estaPagada;
    }

    public void setPagada(boolean estaPagada) {
        this.estaPagada = estaPagada;
    }

    @Override
    public String toString() {
        return "Cuota [Id=" + Id + ", IdPrestamo=" + IdPrestamo + ", NumeroCuota=" + NumeroCuota + ", Monto=" + Monto
                + ", FechaPago=" + FechaPago + ", estaPagada=" + estaPagada + "]";
    }
}
