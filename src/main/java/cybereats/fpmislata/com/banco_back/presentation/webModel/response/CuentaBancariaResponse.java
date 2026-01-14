package cybereats.fpmislata.com.banco_back.presentation.webModel.response;

import java.math.BigDecimal;
import java.util.List;

public record CuentaBancariaResponse(
                Long id,
                BigDecimal saldo,
                String iban,
                ClienteResponse cliente,
                List<TarjetaCreditoResponse> tarjetas,
                List<MovimientoBancarioResponse> movimientos) {
}
