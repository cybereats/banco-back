package cybereats.fpmislata.com.banco_back.domain.dto;

import java.time.LocalDateTime;

public record AuthResponseDto(
        String token,
        LocalDateTime expiration,
        Long id,
        String login,
        String nombre) {
}
