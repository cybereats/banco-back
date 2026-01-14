package cybereats.fpmislata.com.banco_back.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank(message = "El nombre de usuario es obligatorio") @Size(min = 4, max = 20, message = "El nombre de usuario debe tener entre 4 y 20 caracteres") String login,

        @NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password,

        @NotBlank(message = "El nombre es obligatorio") String nombre,

        @NotBlank(message = "El primer apellido es obligatorio") String apellido1,

        String apellido2,

        @NotBlank(message = "El DNI es obligatorio") String dni) {
}
