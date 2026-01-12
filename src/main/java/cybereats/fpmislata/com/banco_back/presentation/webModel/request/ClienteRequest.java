package cybereats.fpmislata.com.banco_back.presentation.webModel.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
                Long id,
                @NotNull String login,
                @NotNull String password,
                @NotNull String nombre,
                String apellido1,
                String apellido2,
                @NotNull String dni,
                String apiToken) {
}
