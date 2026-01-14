package cybereats.fpmislata.com.banco_back.presentation.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.MovimientoBancarioDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.model.OrigenMovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.model.TipoMovimientoBancario;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.MovimientoBancarioRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.response.MovimientoBancarioResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoBancarioMapperTest {

    private final MovimientoBancarioMapper movimientoBancarioMapper = MovimientoBancarioMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de MovimientoBancarioRequest a MovimientoBancarioDto")
    void shouldMapToDto() {
        MovimientoBancarioRequest request = new MovimientoBancarioRequest(1L, TipoMovimientoBancario.HABER,
                OrigenMovimientoBancario.TRANSFERENCIA, LocalDateTime.now(), new BigDecimal("100.00"), "Concepto", 1L,
                1L);

        MovimientoBancarioDto dto = movimientoBancarioMapper.toDto(request);

        assertNotNull(dto);
        assertEquals(request.id(), dto.id());
        assertEquals(request.tipoMovimientoBancario(), dto.tipoMovimientoBancario());
        assertEquals(request.origenMovimientoBancario(), dto.origenMovimientoBancario());
        assertEquals(request.fecha(), dto.fecha());
        assertEquals(request.importe(), dto.importe());
        assertEquals(request.concepto(), dto.concepto());
        assertNotNull(dto.tarjetaCreditoOrigen());
        assertEquals(request.tarjetaCreditoId(), dto.tarjetaCreditoOrigen().id());
    }

    @Test
    @DisplayName("Debería mapear de MovimientoBancarioDto a MovimientoBancarioResponse")
    void shouldMapToResponse() {
        TarjetaCreditoDto tarjetaDto = new TarjetaCreditoDto(1L, "1234567890123456", LocalDate.now().plusYears(1), 123,
                "User");
        MovimientoBancarioDto dto = new MovimientoBancarioDto(1L, TipoMovimientoBancario.HABER,
                OrigenMovimientoBancario.TRANSFERENCIA, LocalDateTime.now(), new BigDecimal("100.00"), "Concepto",
                tarjetaDto);

        MovimientoBancarioResponse response = movimientoBancarioMapper.toResponse(dto);

        assertNotNull(response);
        assertEquals(dto.id(), response.id());
        assertEquals(dto.tipoMovimientoBancario(), response.tipoMovimientoBancario());
        assertEquals(dto.origenMovimientoBancario(), response.origenMovimientoBancario());
        assertEquals(dto.fecha(), response.fecha());
        assertEquals(dto.importe(), response.importe());
        assertEquals(dto.concepto(), response.concepto());
        assertNotNull(response.tarjetaCreditoOrigen());
        assertEquals(tarjetaDto.id(), response.tarjetaCreditoOrigen().id());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(movimientoBancarioMapper.toDto(null));
        assertNull(movimientoBancarioMapper.toResponse(null));
    }
}
