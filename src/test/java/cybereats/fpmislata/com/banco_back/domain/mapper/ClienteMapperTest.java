package cybereats.fpmislata.com.banco_back.domain.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteMapperTest {

    private final ClienteMapper clienteMapper = ClienteMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de Cliente a ClienteDto")
    void shouldMapToDto() {
        Cliente cliente = new Cliente(1L, "user1", "pass1", "Nombre", "Apell1", "Apell2", "12345678A", "token123");

        ClienteDto dto = clienteMapper.toDto(cliente);

        assertNotNull(dto);
        assertEquals(cliente.getId(), dto.id());
        assertEquals(cliente.getLogin(), dto.login());
        assertEquals(cliente.getPassword(), dto.password());
        assertEquals(cliente.getNombre(), dto.nombre());
        assertEquals(cliente.getApellido1(), dto.apellido1());
        assertEquals(cliente.getApellido2(), dto.apellido2());
        assertEquals(cliente.getDni(), dto.dni());
        assertEquals(cliente.getApiToken(), dto.apiToken());
    }

    @Test
    @DisplayName("Debería mapear de ClienteDto a Cliente")
    void shouldMapToModel() {
        ClienteDto dto = new ClienteDto(1L, "user1", "pass1", "Nombre", "Apell1", "Apell2", "12345678A", "token123");

        Cliente model = clienteMapper.toModel(dto);

        assertNotNull(model);
        assertEquals(dto.id(), model.getId());
        assertEquals(dto.login(), model.getLogin());
        assertEquals(dto.password(), model.getPassword());
        assertEquals(dto.nombre(), model.getNombre());
        assertEquals(dto.apellido1(), model.getApellido1());
        assertEquals(dto.apellido2(), model.getApellido2());
        assertEquals(dto.dni(), model.getDni());
        assertEquals(dto.apiToken(), model.getApiToken());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(clienteMapper.toDto(null));
        assertNull(clienteMapper.toModel(null));
    }
}
