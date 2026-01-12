package cybereats.fpmislata.com.banco_back.presentation.webModel.response;

import java.time.LocalDate;

public record TarjetaCreditoResponse(
                Long id,
                String numeroTarjeta,
                LocalDate fechaCaducidad,
                int cvc,
                String nombreCompleto) {
}
