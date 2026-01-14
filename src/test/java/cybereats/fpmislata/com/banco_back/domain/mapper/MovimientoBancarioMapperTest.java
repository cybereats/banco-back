package cybereats.fpmislata.com.banco_back.domain.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.MovimientoBancarioDto;
import cybereats.fpmislata.com.banco_back.domain.model.MovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.model.OrigenMovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.model.TipoMovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.model.TarjetaCredito;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoBancarioMapperTest {

    private final MovimientoBancarioMapper movimientoBancarioMapper = MovimientoBancarioMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de MovimientoBancario a MovimientoBancarioDto")
    void shouldMapToDto() {
        TarjetaCredito tarjeta = new TarjetaCredito(1L, "1234", LocalDate.of(2025, 12, 25), 123, "User");
        LocalDateTime fecha = LocalDateTime.now();
        MovimientoBancario movimiento = new MovimientoBancario(1L, TipoMovimientoBancario.HABER,
                OrigenMovimientoBancario.TRANSFERENCIA, tarjeta, fecha, new BigDecimal("100.00"), "Concepto");

        MovimientoBancarioDto dto = movimientoBancarioMapper.toDto(movimiento);

        assertNotNull(dto);
        assertEquals(movimiento.getId(), dto.id());
        assertEquals(movimiento.getTipoMovimientoBancario(), dto.tipoMovimientoBancario());
        assertEquals(movimiento.getOrigenMovimientoBancario(), dto.origenMovimientoBancario());
        assertEquals(movimiento.getFecha(), dto.fecha());
        assertEquals(movimiento.getImporte(), dto.importe());
        assertEquals(movimiento.getConcepto(), dto.concepto());
        assertNotNull(dto.tarjetaCreditoOrigen());
        assertEquals(tarjeta.getId(), dto.tarjetaCreditoOrigen().id());
    }

    @Test
    @DisplayName("Debería mapear de MovimientoBancarioDto a MovimientoBancario")
    void shouldMapToModel() {
        TarjetaCredito tarjeta = new TarjetaCredito(1L, "1234", LocalDate.of(2025, 12, 25), 123, "User");
        LocalDateTime fecha = LocalDateTime.now();
        MovimientoBancario movimiento = new MovimientoBancario(1L, TipoMovimientoBancario.HABER,
                OrigenMovimientoBancario.TRANSFERENCIA, tarjeta, fecha, new BigDecimal("100.00"), "Concepto");
        MovimientoBancarioDto dto = MovimientoBancarioMapper.getInstance().toDto(movimiento);

        MovimientoBancario model = movimientoBancarioMapper.toModel(dto);

        assertNotNull(model);
        assertEquals(dto.id(), model.getId());
        assertEquals(dto.tipoMovimientoBancario(), model.getTipoMovimientoBancario());
        assertEquals(dto.origenMovimientoBancario(), model.getOrigenMovimientoBancario());
        assertEquals(dto.fecha(), model.getFecha());
        assertEquals(dto.importe(), model.getImporte());
        assertEquals(dto.concepto(), model.getConcepto());
        assertNotNull(model.getTarjetaCreditoOrigen());
        assertEquals(tarjeta.getId(), model.getTarjetaCreditoOrigen().getId());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(movimientoBancarioMapper.toDto(null));
        assertNull(movimientoBancarioMapper.toModel(null));
    }
}
