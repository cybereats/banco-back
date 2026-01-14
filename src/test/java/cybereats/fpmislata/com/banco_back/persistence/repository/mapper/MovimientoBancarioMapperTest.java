package cybereats.fpmislata.com.banco_back.persistence.repository.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.MovimientoBancarioDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.model.OrigenMovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.model.TipoMovimientoBancario;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.MovimientoBancarioJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.TarjetaCreditoJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoBancarioMapperTest {

    private final MovimientoBancarioMapper movimientoBancarioMapper = MovimientoBancarioMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de MovimientoBancarioJpaEntity a MovimientoBancarioDto")
    void shouldMapToDto() {
        TarjetaCreditoJpaEntity tarjetaEntity = new TarjetaCreditoJpaEntity();
        tarjetaEntity.setId(1L);

        MovimientoBancarioJpaEntity entity = new MovimientoBancarioJpaEntity();
        entity.setId(1L);
        entity.setTipoMovimientoBancario(TipoMovimientoBancario.HABER);
        entity.setOrigenMovimientoBancario(OrigenMovimientoBancario.TRANSFERENCIA);
        entity.setFecha(LocalDateTime.now());
        entity.setImporte(new BigDecimal("100.00"));
        entity.setConcepto("Concepto");
        entity.setTarjetaCreditoOrigen(tarjetaEntity);

        MovimientoBancarioDto dto = movimientoBancarioMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getTipoMovimientoBancario(), dto.tipoMovimientoBancario());
        assertEquals(entity.getOrigenMovimientoBancario(), dto.origenMovimientoBancario());
        assertEquals(entity.getFecha(), dto.fecha());
        assertEquals(entity.getImporte(), dto.importe());
        assertEquals(entity.getConcepto(), dto.concepto());
        assertNotNull(dto.tarjetaCreditoOrigen());
        assertEquals(tarjetaEntity.getId(), dto.tarjetaCreditoOrigen().id());
    }

    @Test
    @DisplayName("Debería mapear de MovimientoBancarioDto a MovimientoBancarioJpaEntity")
    void shouldMapToEntity() {
        TarjetaCreditoDto tarjetaDto = new TarjetaCreditoDto(1L, "1234", LocalDate.of(2025, 12, 25), 123, "User");
        MovimientoBancarioDto dto = new MovimientoBancarioDto(1L, TipoMovimientoBancario.HABER,
                OrigenMovimientoBancario.TRANSFERENCIA, LocalDateTime.now(), new BigDecimal("100.00"), "Concepto",
                tarjetaDto);

        MovimientoBancarioJpaEntity entity = movimientoBancarioMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.tipoMovimientoBancario(), entity.getTipoMovimientoBancario());
        assertEquals(dto.origenMovimientoBancario(), entity.getOrigenMovimientoBancario());
        assertEquals(dto.fecha(), entity.getFecha());
        assertEquals(dto.importe(), entity.getImporte());
        assertEquals(dto.concepto(), entity.getConcepto());
        assertNotNull(entity.getTarjetaCreditoOrigen());
        assertEquals(tarjetaDto.id(), entity.getTarjetaCreditoOrigen().getId());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(movimientoBancarioMapper.toDto(null));
        assertNull(movimientoBancarioMapper.toEntity(null));
    }
}
