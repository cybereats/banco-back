package cybereats.fpmislata.com.banco_back.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "El nombre de usuario es obligatorio") String login,

        @NotBlank(message = "La contraseña es obligatoria") String password) {
}
