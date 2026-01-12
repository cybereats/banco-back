package cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity;

import cybereats.fpmislata.com.banco_back.domain.model.OrigenMovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.model.TipoMovimientoBancario;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento_bancario")
public class MovimientoBancarioJpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoMovimientoBancario tipoMovimientoBancario;

    @Enumerated(EnumType.STRING)
    private OrigenMovimientoBancario origenMovimientoBancario;

    private LocalDateTime fecha;
    private BigDecimal importe;
    private String concepto;

    @ManyToOne
    @JoinColumn(name = "tarjeta_credito_id")
    private TarjetaCreditoJpaEntity tarjetaCreditoOrigen;

    @ManyToOne
    @JoinColumn(name = "cuenta_bancaria_id")
    private CuentaBancariaJpaEntity cuentaBancaria;

    public MovimientoBancarioJpaEntity() {
    }

    public MovimientoBancarioJpaEntity(Long id, TipoMovimientoBancario tipoMovimientoBancario,
            OrigenMovimientoBancario origenMovimientoBancario, LocalDateTime fecha, BigDecimal importe, String concepto,
            TarjetaCreditoJpaEntity tarjetaCreditoOrigen, CuentaBancariaJpaEntity cuentaBancaria) {
        this.id = id;
        this.tipoMovimientoBancario = tipoMovimientoBancario;
        this.origenMovimientoBancario = origenMovimientoBancario;
        this.fecha = fecha;
        this.importe = importe;
        this.concepto = concepto;
        this.tarjetaCreditoOrigen = tarjetaCreditoOrigen;
        this.cuentaBancaria = cuentaBancaria;
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

    public TarjetaCreditoJpaEntity getTarjetaCreditoOrigen() {
        return tarjetaCreditoOrigen;
    }

    public void setTarjetaCreditoOrigen(TarjetaCreditoJpaEntity tarjetaCreditoOrigen) {
        this.tarjetaCreditoOrigen = tarjetaCreditoOrigen;
    }

    public CuentaBancariaJpaEntity getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(CuentaBancariaJpaEntity cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }
}
