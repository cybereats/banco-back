package cybereats.fpmislata.com.banco_back.presentation.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.ClienteRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.response.ClienteResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteMapperTest {

    private final ClienteMapper clienteMapper = ClienteMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de ClienteRequest a ClienteDto")
    void shouldMapToDto() {
        ClienteRequest request = new ClienteRequest(1L, "user", "pass", "Name", "A1", "A2", "12345678A", "token");

        ClienteDto dto = clienteMapper.toDto(request);

        assertNotNull(dto);
        assertEquals(request.id(), dto.id());
        assertEquals(request.login(), dto.login());
        assertEquals(request.password(), dto.password());
        assertEquals(request.nombre(), dto.nombre());
        assertEquals(request.apellido1(), dto.apellido1());
        assertEquals(request.apellido2(), dto.apellido2());
        assertEquals(request.dni(), dto.dni());
        assertEquals(request.apiToken(), dto.apiToken());
    }

    @Test
    @DisplayName("Debería mapear de ClienteDto a ClienteResponse")
    void shouldMapToResponse() {
        ClienteDto dto = new ClienteDto(1L, "user", "pass", "Name", "A1", "A2", "12345678A", "token");

        ClienteResponse response = clienteMapper.toResponse(dto);

        assertNotNull(response);
        assertEquals(dto.id(), response.id());
        assertEquals(dto.login(), response.login());
        assertEquals(dto.nombre(), response.nombre());
        assertEquals(dto.apellido1(), response.apellido1());
        assertEquals(dto.apellido2(), response.apellido2());
        assertEquals(dto.dni(), response.dni());
        assertEquals(dto.apiToken(), response.apiToken());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(clienteMapper.toDto(null));
        assertNull(clienteMapper.toResponse(null));
    }
}
