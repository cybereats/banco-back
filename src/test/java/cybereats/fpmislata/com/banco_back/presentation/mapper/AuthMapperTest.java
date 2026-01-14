package cybereats.fpmislata.com.banco_back.presentation.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteProfileResponseDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthMapperTest {

    private final AuthMapper authMapper = AuthMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de Cliente a ClienteProfileResponseDto")
    void shouldMapToProfileResponse() {
        Cliente cliente = new Cliente(1L, "user", "pass", "Name", "A1", "A2", "12345678A", "token");

        ClienteProfileResponseDto response = authMapper.toProfileResponse(cliente);

        assertNotNull(response);
        assertEquals(cliente.getId(), response.id());
        assertEquals(cliente.getLogin(), response.login());
        assertEquals(cliente.getNombre(), response.nombre());
        assertEquals(cliente.getApellido1(), response.apellido1());
        assertEquals(cliente.getApellido2(), response.apellido2());
        assertEquals(cliente.getDni(), response.dni());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(authMapper.toProfileResponse(null));
    }
}
