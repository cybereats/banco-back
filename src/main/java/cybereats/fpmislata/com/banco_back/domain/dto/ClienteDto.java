package cybereats.fpmislata.com.banco_back.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteDto(
        Long id,
        @NotNull String login,
        @NotNull String password,
        @NotNull String nombre,
        String apellido1,
        String apellido2,
        @NotNull @Pattern(regexp = "^[0-9]{8}[A-Z]$", message = "DNI must be valid") String dni,
        String apiToken) {
}
