package cybereats.fpmislata.com.banco_back.persistence.repository.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteMapperTest {

    private final ClienteMapper clienteMapper = ClienteMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de ClienteJpaEntity a ClienteDto")
    void shouldMapToDto() {
        ClienteJpaEntity entity = new ClienteJpaEntity(1L, "user1", "pass1", "Nombre", "Apell1", "Apell2", "12345678A",
                "token123");

        ClienteDto dto = clienteMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getLogin(), dto.login());
        assertEquals(entity.getPassword(), dto.password());
        assertEquals(entity.getNombre(), dto.nombre());
        assertEquals(entity.getApellido1(), dto.apellido1());
        assertEquals(entity.getApellido2(), dto.apellido2());
        assertEquals(entity.getDni(), dto.dni());
        assertEquals(entity.getApiToken(), dto.apiToken());
    }

    @Test
    @DisplayName("Debería mapear de ClienteDto a ClienteJpaEntity")
    void shouldMapToEntity() {
        ClienteDto dto = new ClienteDto(1L, "user1", "pass1", "Nombre", "Apell1", "Apell2", "12345678A", "token123");

        ClienteJpaEntity entity = clienteMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.login(), entity.getLogin());
        assertEquals(dto.password(), entity.getPassword());
        assertEquals(dto.nombre(), entity.getNombre());
        assertEquals(dto.apellido1(), entity.getApellido1());
        assertEquals(dto.apellido2(), entity.getApellido2());
        assertEquals(dto.dni(), entity.getDni());
        assertEquals(dto.apiToken(), entity.getApiToken());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(clienteMapper.toDto(null));
        assertNull(clienteMapper.toEntity(null));
    }
}
