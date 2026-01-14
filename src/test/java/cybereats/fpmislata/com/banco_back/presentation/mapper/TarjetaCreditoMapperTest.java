package cybereats.fpmislata.com.banco_back.presentation.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.TarjetaCreditoRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.response.TarjetaCreditoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class TarjetaCreditoMapperTest {

    private final TarjetaCreditoMapper tarjetaCreditoMapper = TarjetaCreditoMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de TarjetaCreditoRequest a TarjetaCreditoDto")
    void shouldMapToDto() {
        TarjetaCreditoRequest request = new TarjetaCreditoRequest(1L, "1234567890123456", LocalDate.of(2025, 12, 25),
                123, "Test User");

        TarjetaCreditoDto dto = tarjetaCreditoMapper.toDto(request);

        assertNotNull(dto);
        assertEquals(request.id(), dto.id());
        assertEquals(request.numeroTarjeta(), dto.numeroTarjeta());
        assertEquals(request.fechaCaducidad(), dto.fechaCaducidad());
        assertEquals(request.cvc(), dto.cvc());
        assertEquals(request.nombreCompleto(), dto.nombreCompleto());
    }

    @Test
    @DisplayName("Debería mapear de TarjetaCreditoDto a TarjetaCreditoResponse")
    void shouldMapToResponse() {
        TarjetaCreditoDto dto = new TarjetaCreditoDto(1L, "1234567890123456", LocalDate.of(2025, 12, 25), 123,
                "Test User");

        TarjetaCreditoResponse response = tarjetaCreditoMapper.toResponse(dto);

        assertNotNull(response);
        assertEquals(dto.id(), response.id());
        assertEquals(dto.numeroTarjeta(), response.numeroTarjeta());
        assertEquals(dto.fechaCaducidad(), response.fechaCaducidad());
        assertEquals(dto.cvc(), response.cvc());
        assertEquals(dto.nombreCompleto(), response.nombreCompleto());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(tarjetaCreditoMapper.toDto(null));
        assertNull(tarjetaCreditoMapper.toResponse(null));
    }
}
