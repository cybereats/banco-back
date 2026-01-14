package cybereats.fpmislata.com.banco_back.presentation.webModel.request;

import java.math.BigDecimal;

public record Pago(
        BigDecimal importe,
        String concepto) {
}
