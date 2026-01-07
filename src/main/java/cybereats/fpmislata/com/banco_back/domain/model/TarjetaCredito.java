package cybereats.fpmislata.com.banco_back.domain.model;

import java.time.LocalDate;

public class TarjetaCredito {
    private String numeroTarjeta;
    private LocalDate fechaCaducidad;
    private String cvc;
    private String nombreCompleto;
    private CuentaBancaria cuentaBancaria;

    public TarjetaCredito() {
    }

    public TarjetaCredito(String numeroTarjeta, LocalDate fechaCaducidad, String cvc, String nombreCompleto,
            CuentaBancaria cuentaBancaria) {
        this.numeroTarjeta = numeroTarjeta;
        this.fechaCaducidad = fechaCaducidad;
        this.cvc = cvc;
        this.nombreCompleto = nombreCompleto;
        this.cuentaBancaria = cuentaBancaria;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }
}
