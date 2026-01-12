package cybereats.fpmislata.com.banco_back.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

public record CuentaBancariaDto(
        Long id,
        @NotNull BigDecimal saldo,
        @NotNull @Pattern(regexp = "^ES[0-9]{22}$", message = "Invalid IBAN format") String iban,
        ClienteDto cliente,
        List<TarjetaCreditoDto> tarjetas) {
}
