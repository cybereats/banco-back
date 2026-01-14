package cybereats.fpmislata.com.banco_back.domain.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.model.TarjetaCredito;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class TarjetaCreditoMapperTest {

    private final TarjetaCreditoMapper tarjetaCreditoMapper = TarjetaCreditoMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de TarjetaCredito a TarjetaCreditoDto")
    void shouldMapToDto() {
        TarjetaCredito tarjeta = new TarjetaCredito(1L, "1234567890123456", LocalDate.of(2025, 12, 25), 123,
                "Test User");

        TarjetaCreditoDto dto = tarjetaCreditoMapper.toDto(tarjeta);

        assertNotNull(dto);
        assertEquals(tarjeta.getId(), dto.id());
        assertEquals(tarjeta.getNumeroTarjeta(), dto.numeroTarjeta());
        assertEquals(tarjeta.getFechaCaducidad(), dto.fechaCaducidad());
        assertEquals(tarjeta.getCvc(), dto.cvc());
        assertEquals(tarjeta.getNombreCompleto(), dto.nombreCompleto());
    }

    @Test
    @DisplayName("Debería mapear de TarjetaCreditoDto a TarjetaCredito")
    void shouldMapToModel() {
        TarjetaCreditoDto dto = new TarjetaCreditoDto(1L, "1234567890123456", LocalDate.of(2025, 12, 25), 123,
                "Test User");

        TarjetaCredito model = tarjetaCreditoMapper.toModel(dto);

        assertNotNull(model);
        assertEquals(dto.id(), model.getId());
        assertEquals(dto.numeroTarjeta(), model.getNumeroTarjeta());
        assertEquals(dto.fechaCaducidad(), model.getFechaCaducidad());
        assertEquals(dto.cvc(), model.getCvc());
        assertEquals(dto.nombreCompleto(), model.getNombreCompleto());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(tarjetaCreditoMapper.toDto(null));
        assertNull(tarjetaCreditoMapper.toModel(null));
    }
}
