package cybereats.fpmislata.com.banco_back.presentation.webModel.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TarjetaCreditoRequest(
                Long id,
                @NotNull String numeroTarjeta,
                @NotNull LocalDate fechaCaducidad,
                @NotNull int cvc,
                @NotNull String nombreCompleto) {
}
