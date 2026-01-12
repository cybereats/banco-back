package cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tarjeta_credito")
public class TarjetaCreditoJpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroTarjeta;
    private LocalDate fechaCaducidad;
    private int cvc;
    private String nombreCompleto;

    @ManyToOne
    @JoinColumn(name = "cuenta_bancaria_id")
    private CuentaBancariaJpaEntity cuentaBancaria;

    public TarjetaCreditoJpaEntity() {
    }

    public TarjetaCreditoJpaEntity(Long id, String numeroTarjeta, LocalDate fechaCaducidad, int cvc,
            String nombreCompleto, CuentaBancariaJpaEntity cuentaBancaria) {
        this.id = id;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaCaducidad = fechaCaducidad;
        this.cvc = cvc;
        this.nombreCompleto = nombreCompleto;
        this.cuentaBancaria = cuentaBancaria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public CuentaBancariaJpaEntity getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(CuentaBancariaJpaEntity cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }
}
