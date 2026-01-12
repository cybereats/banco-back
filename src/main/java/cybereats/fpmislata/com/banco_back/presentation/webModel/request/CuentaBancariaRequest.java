package cybereats.fpmislata.com.banco_back.presentation.webModel.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record CuentaBancariaRequest(
        Long id,
        @NotNull BigDecimal saldo,
        @NotNull String iban,
        Long clienteId,
        List<TarjetaCreditoRequest> tarjetas) {
}
