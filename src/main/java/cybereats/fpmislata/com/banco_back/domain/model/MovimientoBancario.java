package cybereats.fpmislata.com.banco_back.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimientoBancario {
    private Long id;
    private TipoMovimientoBancario tipoMovimientoBancario;
    private OrigenMovimientoBancario origenMovimientoBancario;
    private TarjetaCredito tarjetaCreditoOrigen;
    private LocalDateTime fecha;
    private BigDecimal importe;
    private String concepto;

    public MovimientoBancario() {
    }

    public MovimientoBancario(Long id, TipoMovimientoBancario tipoMovimientoBancario,
            OrigenMovimientoBancario origenMovimientoBancario, TarjetaCredito tarjetaCreditoOrigen, LocalDateTime fecha,
            BigDecimal importe, String concepto) {
        this.id = id;
        this.tipoMovimientoBancario = tipoMovimientoBancario;
        this.origenMovimientoBancario = origenMovimientoBancario;
        this.tarjetaCreditoOrigen = tarjetaCreditoOrigen;
        this.fecha = fecha;
        this.importe = importe;
        this.concepto = concepto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMovimientoBancario getTipoMovimientoBancario() {
        return tipoMovimientoBancario;
    }

    public void setTipoMovimientoBancario(TipoMovimientoBancario tipoMovimientoBancario) {
        this.tipoMovimientoBancario = tipoMovimientoBancario;
    }

    public OrigenMovimientoBancario getOrigenMovimientoBancario() {
        return origenMovimientoBancario;
    }

    public void setOrigenMovimientoBancario(OrigenMovimientoBancario origenMovimientoBancario) {
        this.origenMovimientoBancario = origenMovimientoBancario;
    }

    public TarjetaCredito getTarjetaCreditoOrigen() {
        return tarjetaCreditoOrigen;
    }

    public void setTarjetaCreditoOrigen(TarjetaCredito tarjetaCreditoOrigen) {
        this.tarjetaCreditoOrigen = tarjetaCreditoOrigen;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}
