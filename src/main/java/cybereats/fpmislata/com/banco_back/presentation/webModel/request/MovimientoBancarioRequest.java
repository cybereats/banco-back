package cybereats.fpmislata.com.banco_back.presentation.webModel.request;

import cybereats.fpmislata.com.banco_back.domain.model.OrigenMovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.model.TipoMovimientoBancario;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoBancarioRequest(
        Long id,
        @NotNull TipoMovimientoBancario tipoMovimientoBancario,
        @NotNull OrigenMovimientoBancario origenMovimientoBancario,
        @NotNull LocalDateTime fecha,
        @NotNull BigDecimal importe,
        String concepto,
        Long tarjetaCreditoId,
        Long cuentaBancariaId) {
}
