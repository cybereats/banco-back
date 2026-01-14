package cybereats.fpmislata.com.banco_back.persistence.repository.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.ClienteDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para ClienteRepositoryImpl")
class ClienteRepositoryImplTest {

    @Mock
    private ClienteDaoJpa clienteDaoJpa;

    @InjectMocks
    private ClienteRepositoryImpl clienteRepository;

    private ClienteJpaEntity clienteJpaEntity;
    private ClienteDto clienteDto;

    @BeforeEach
    void setUp() {
        clienteJpaEntity = new ClienteJpaEntity(1L, "jdoe", "password", "John", "Doe", "Smith", "12345678A",
                "token123");
        clienteDto = new ClienteDto(1L, "jdoe", "password", "John", "Doe", "Smith", "12345678A", "token123");
    }

    @Nested
    @DisplayName("Tests para el método findAll")
    class FindAllTests {
        @Test
        @DisplayName("Debería devolver una página de todos los clientes")
        void shouldReturnPageOfClientes() {
            when(clienteDaoJpa.findAll()).thenReturn(List.of(clienteJpaEntity));

            Page<ClienteDto> result = clienteRepository.findAll();

            assertAll("Verificación de la página devuelta",
                    () -> assertNotNull(result),
                    () -> assertEquals(1, result.data().size()),
                    () -> assertEquals(1, result.totalElements()),
                    () -> assertEquals(clienteDto.login(), result.data().get(0).login()));
            verify(clienteDaoJpa).findAll();
        }
    }

    @Nested
    @DisplayName("Tests para el método findById")
    class FindByIdTests {
        @Test
        @DisplayName("Debería devolver el cliente cuando el ID existe")
        void shouldReturnClienteWhenIdExists() {
            when(clienteDaoJpa.findById(1L)).thenReturn(clienteJpaEntity);

            ClienteDto result = clienteRepository.findById(1L);

            assertAll("Verificación del cliente devuelto",
                    () -> assertNotNull(result),
                    () -> assertEquals(1L, result.id()),
                    () -> assertEquals("jdoe", result.login()));
            verify(clienteDaoJpa).findById(1L);
        }

        @Test
        @DisplayName("Debería devolver null cuando el ID no existe")
        void shouldReturnNullWhenIdDoesNotExist() {
            when(clienteDaoJpa.findById(999L)).thenReturn(null);

            ClienteDto result = clienteRepository.findById(999L);

            assertNull(result);
        }
    }

    @Nested
    @DisplayName("Tests para el método save")
    class SaveTests {
        @Test
        @DisplayName("Debería insertar el cliente cuando el ID es nulo")
        void shouldInsertClienteWhenIdIsNull() {
            ClienteDto clienteDtoToSave = new ClienteDto(null, "newuser", "pass", "Name", "A1", "A2", "12345678A",
                    null);
            ClienteJpaEntity savedEntity = new ClienteJpaEntity(10L, "newuser", "pass", "Name", "A1", "A2", "12345678A",
                    null);

            when(clienteDaoJpa.insert(any(ClienteJpaEntity.class))).thenReturn(savedEntity);

            ClienteDto result = clienteRepository.save(clienteDtoToSave);

            assertAll("Verificación de la inserción",
                    () -> assertNotNull(result),
                    () -> assertEquals(10L, result.id()),
                    () -> verify(clienteDaoJpa, times(1)).insert(any(ClienteJpaEntity.class)),
                    () -> verify(clienteDaoJpa, never()).update(any(ClienteJpaEntity.class)));
        }

        @Test
        @DisplayName("Debería actualizar el cliente cuando el ID no es nulo")
        void shouldUpdateClienteWhenIdIsNotNull() {
            when(clienteDaoJpa.update(any(ClienteJpaEntity.class))).thenReturn(clienteJpaEntity);

            ClienteDto result = clienteRepository.save(clienteDto);

            assertAll("Verificación de la actualización",
                    () -> assertNotNull(result),
                    () -> assertEquals(1L, result.id()),
                    () -> verify(clienteDaoJpa, times(1)).update(any(ClienteJpaEntity.class)),
                    () -> verify(clienteDaoJpa, never()).insert(any(ClienteJpaEntity.class)));
        }
    }

    @Nested
    @DisplayName("Tests para el método delete")
    class DeleteTests {
        @Test
        @DisplayName("Debería llamar al DAO para borrar por ID")
        void shouldDeleteCliente() {
            doNothing().when(clienteDaoJpa).delete(1L);

            clienteRepository.delete(1L);

            verify(clienteDaoJpa, times(1)).delete(1L);
        }
    }
}
