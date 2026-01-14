package cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuenta_bancaria")
public class CuentaBancariaJpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal saldo;
    private String iban;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteJpaEntity cliente;

    @OneToMany(mappedBy = "cuentaBancaria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TarjetaCreditoJpaEntity> tarjetas = new ArrayList<>();

    @OneToMany(mappedBy = "cuentaBancaria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimientoBancarioJpaEntity> movimientos = new ArrayList<>();

    public CuentaBancariaJpaEntity() {
    }

    public CuentaBancariaJpaEntity(Long id, BigDecimal saldo, String iban, ClienteJpaEntity cliente) {
        this.id = id;
        this.saldo = saldo;
        this.iban = iban;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public ClienteJpaEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteJpaEntity cliente) {
        this.cliente = cliente;
    }

    public List<TarjetaCreditoJpaEntity> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<TarjetaCreditoJpaEntity> tarjetas) {
        this.tarjetas = tarjetas;
    }

    public List<MovimientoBancarioJpaEntity> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoBancarioJpaEntity> movimientos) {
        this.movimientos = movimientos;
    }
}
