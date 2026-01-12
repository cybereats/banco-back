package cybereats.fpmislata.com.banco_back.presentation.webModel.response;

public record ClienteResponse(
        Long id,
        String login,
        String nombre,
        String apellido1,
        String apellido2,
        String dni,
        String apiToken) {
}
