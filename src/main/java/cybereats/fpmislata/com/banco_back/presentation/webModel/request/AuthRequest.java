package cybereats.fpmislata.com.banco_back.presentation.webModel.request;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio") String login,

        @NotBlank(message = "La contraseña es obligatoria") String password) {
}
