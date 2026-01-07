package cybereats.fpmislata.com.banco_back.domain.model;

import java.math.BigDecimal;

public class CuentaBancaria {
    private BigDecimal saldo;
    private String iban;
    private Cliente cliente;

    public CuentaBancaria() {
    }

    public CuentaBancaria(BigDecimal saldo, String iban, Cliente cliente) {
        this.saldo = saldo;
        this.iban = iban;
        this.cliente = cliente;
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
}
