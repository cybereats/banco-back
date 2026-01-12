package cybereats.fpmislata.com.banco_back.domain.model;

import java.math.BigDecimal;
import java.util.List;

public class CuentaBancaria {
    private Long id;
    private BigDecimal saldo;
    private String iban;
    private Cliente cliente;
    private List<TarjetaCredito> tarjetas;

    public CuentaBancaria() {
    }

    public CuentaBancaria(Long id, BigDecimal saldo, String iban, Cliente cliente) {
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<TarjetaCredito> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<TarjetaCredito> tarjetas) {
        this.tarjetas = tarjetas;
    }
}
