package cybereats.fpmislata.com.banco_back.presentation.webModel.response;

import cybereats.fpmislata.com.banco_back.domain.model.OrigenMovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.model.TipoMovimientoBancario;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoBancarioResponse(
        Long id,
        TipoMovimientoBancario tipoMovimientoBancario,
        OrigenMovimientoBancario origenMovimientoBancario,
        LocalDateTime fecha,
        BigDecimal importe,
        String concepto,
        TarjetaCreditoResponse tarjetaCreditoOrigen,
        CuentaBancariaResponse cuentaBancaria) {
}
