package cybereats.fpmislata.com.banco_back.domain.dto;

public record ClienteProfileResponseDto(
        Long id,
        String login,
        String nombre,
        String apellido1,
        String apellido2,
        String dni) {
}
