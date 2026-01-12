package cybereats.fpmislata.com.banco_back.domain.dto;

import cybereats.fpmislata.com.banco_back.domain.model.OrigenMovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.model.TipoMovimientoBancario;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoBancarioDto(
        Long id,
        @NotNull TipoMovimientoBancario tipoMovimientoBancario,
        @NotNull OrigenMovimientoBancario origenMovimientoBancario,
        @NotNull LocalDateTime fecha,
        @NotNull BigDecimal importe,
        String concepto,
        TarjetaCreditoDto tarjetaCreditoOrigen,
        CuentaBancariaDto cuentaBancaria) {
}
