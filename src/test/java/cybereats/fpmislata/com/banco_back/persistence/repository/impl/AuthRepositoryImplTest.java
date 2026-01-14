package cybereats.fpmislata.com.banco_back.persistence.repository.impl;

import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para AuthRepositoryImpl")
class AuthRepositoryImplTest {

    @Mock
    private ClienteDaoJpa clienteDaoJpa;

    @InjectMocks
    private AuthRepositoryImpl authRepository;

    private ClienteJpaEntity clienteJpaEntity;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        clienteJpaEntity = new ClienteJpaEntity(1L, "jdoe", "password", "John", "Doe", null, "12345678A", "token123");

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setLogin("jdoe");
        cliente.setPassword("password");
        cliente.setNombre("John");
        cliente.setApellido1("Doe");
        cliente.setDni("12345678A");
    }

    @Nested
    @DisplayName("Tests para el método findByLogin")
    class FindByLoginTests {
        @Test
        @DisplayName("Debería devolver un Optional con el Cliente cuando el login existe")
        void shouldReturnClienteWhenLoginExists() {
            when(clienteDaoJpa.findByLogin("jdoe")).thenReturn(clienteJpaEntity);

            Optional<Cliente> result = authRepository.findByLogin("jdoe");

            assertAll(
                    () -> assertTrue(result.isPresent()),
                    () -> assertEquals("jdoe", result.get().getLogin()),
                    () -> assertEquals(1L, result.get().getId()));
            verify(clienteDaoJpa).findByLogin("jdoe");
        }

        @Test
        @DisplayName("Debería devolver un Optional vacío cuando el login no existe")
        void shouldReturnEmptyWhenLoginDoesNotExist() {
            when(clienteDaoJpa.findByLogin("unknown")).thenReturn(null);

            Optional<Cliente> result = authRepository.findByLogin("unknown");

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("Tests para el método save")
    class SaveTests {
        @Test
        @DisplayName("Debería insertar el cliente cuando el ID es nulo")
        void shouldInsertWhenIdIsNull() {
            Cliente newCliente = new Cliente();
            newCliente.setLogin("newuser");
            newCliente.setPassword("pass");
            newCliente.setNombre("Name");
            newCliente.setDni("12345678A");

            authRepository.save(newCliente);

            verify(clienteDaoJpa, times(1)).insert(any(ClienteJpaEntity.class));
            verify(clienteDaoJpa, never()).update(any(ClienteJpaEntity.class));
        }

        @Test
        @DisplayName("Debería actualizar el cliente cuando el ID no es nulo")
        void shouldUpdateWhenIdIsNotNull() {
            authRepository.save(cliente);

            verify(clienteDaoJpa, times(1)).update(any(ClienteJpaEntity.class));
            verify(clienteDaoJpa, never()).insert(any(ClienteJpaEntity.class));
        }
    }
}
