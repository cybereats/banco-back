package cybereats.fpmislata.com.banco_back.domain.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record TarjetaCreditoDto(
                Long id,
                @NotNull @Pattern(regexp = "^[0-9]{16}$", message = "Invalid card number") String numeroTarjeta,
                @NotNull @Future LocalDate fechaCaducidad,
                @Min(1) @Max(9999) int cvc,
                @NotNull String nombreCompleto) {
}
