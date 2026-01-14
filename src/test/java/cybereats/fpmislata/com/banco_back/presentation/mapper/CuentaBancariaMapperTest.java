package cybereats.fpmislata.com.banco_back.presentation.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.CuentaBancariaRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.response.CuentaBancariaResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CuentaBancariaMapperTest {

    private final CuentaBancariaMapper cuentaBancariaMapper = CuentaBancariaMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de CuentaBancariaRequest a CuentaBancariaDto")
    void shouldMapToDto() {
        CuentaBancariaRequest request = new CuentaBancariaRequest(1L, new BigDecimal("1000.00"), "ES1234", 1L,
                Collections.emptyList());

        CuentaBancariaDto dto = cuentaBancariaMapper.toDto(request);

        assertNotNull(dto);
        assertEquals(request.id(), dto.id());
        assertEquals(request.saldo(), dto.saldo());
        assertEquals(request.iban(), dto.iban());
        assertNotNull(dto.cliente());
        assertEquals(request.clienteId(), dto.cliente().id());
        assertNotNull(dto.tarjetas());
    }

    @Test
    @DisplayName("Debería mapear de CuentaBancariaDto a CuentaBancariaResponse")
    void shouldMapToResponse() {
        ClienteDto clienteDto = new ClienteDto(1L, "user", "pass", "Name", "A1", "A2", "12345678A", "token");
        CuentaBancariaDto dto = new CuentaBancariaDto(1L, new BigDecimal("1000.00"), "ES1234", clienteDto,
                Collections.emptyList(), Collections.emptyList());

        CuentaBancariaResponse response = cuentaBancariaMapper.toResponse(dto);

        assertNotNull(response);
        assertEquals(dto.id(), response.id());
        assertEquals(dto.saldo(), response.saldo());
        assertEquals(dto.iban(), response.iban());
        assertNotNull(response.cliente());
        assertEquals(clienteDto.id(), response.cliente().id());
        assertNotNull(response.tarjetas());
        assertNotNull(response.movimientos());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(cuentaBancariaMapper.toDto(null));
        assertNull(cuentaBancariaMapper.toResponse(null));
    }
}
