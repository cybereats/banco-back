package cybereats.fpmislata.com.banco_back.domain.model;

import cybereats.fpmislata.com.banco_back.exception.BusinessException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancaria {
    private Long id;
    private BigDecimal saldo;
    private String iban;
    private Cliente cliente;
    private List<TarjetaCredito> tarjetas = new ArrayList<>();
    private List<MovimientoBancario> movimientos = new ArrayList<>();

    public CuentaBancaria() {
    }

    public CuentaBancaria(Long id, BigDecimal saldo, String iban, Cliente cliente) {
        this.id = id;
        this.saldo = saldo;
        this.iban = iban;
        this.cliente = cliente;
    }

    public void addMovimiento(MovimientoBancario movimiento) {
        if (this.movimientos == null) {
            this.movimientos = new ArrayList<>();
        }
        this.movimientos.add(movimiento);
    }

    public void retirar(BigDecimal importe) {
        if (importe.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El importe a retirar debe ser mayor que cero");
        }
        if (this.saldo.compareTo(importe) < 0) {
            throw new BusinessException("Saldo insuficiente");
        }
        this.saldo = this.saldo.subtract(importe);
    }

    public void ingresar(BigDecimal importe) {
        if (importe.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El importe a ingresar debe ser mayor que cero");
        }
        this.saldo = this.saldo.add(importe);
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

    public List<MovimientoBancario> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoBancario> movimientos) {
        this.movimientos = movimientos;
    }
}
