package cybereats.fpmislata.com.banco_back.persistence.repository.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.TarjetaCreditoJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class TarjetaCreditoMapperTest {

    private final TarjetaCreditoMapper tarjetaCreditoMapper = TarjetaCreditoMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de TarjetaCreditoJpaEntity a TarjetaCreditoDto")
    void shouldMapToDto() {
        TarjetaCreditoJpaEntity entity = new TarjetaCreditoJpaEntity();
        entity.setId(1L);
        entity.setNumeroTarjeta("1234567890123456");
        entity.setFechaCaducidad(LocalDate.of(2025, 12, 25));
        entity.setCvc(123);
        entity.setNombreCompleto("Test User");

        TarjetaCreditoDto dto = tarjetaCreditoMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getNumeroTarjeta(), dto.numeroTarjeta());
        assertEquals(entity.getFechaCaducidad(), dto.fechaCaducidad());
        assertEquals(entity.getCvc(), dto.cvc());
        assertEquals(entity.getNombreCompleto(), dto.nombreCompleto());
    }

    @Test
    @DisplayName("Debería mapear de TarjetaCreditoDto a TarjetaCreditoJpaEntity")
    void shouldMapToEntity() {
        TarjetaCreditoDto dto = new TarjetaCreditoDto(1L, "1234567890123456", LocalDate.of(2025, 12, 25), 123,
                "Test User");

        TarjetaCreditoJpaEntity entity = tarjetaCreditoMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.numeroTarjeta(), entity.getNumeroTarjeta());
        assertEquals(dto.fechaCaducidad(), entity.getFechaCaducidad());
        assertEquals(dto.cvc(), entity.getCvc());
        assertEquals(dto.nombreCompleto(), entity.getNombreCompleto());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(tarjetaCreditoMapper.toDto(null));
        assertNull(tarjetaCreditoMapper.toEntity(null));
    }
}
