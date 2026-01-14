package cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl;

import cybereats.fpmislata.com.banco_back.persistence.TestConfig;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.ClienteDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = "spring.flyway.locations=classpath:db/migration/test")
class ClienteDaoJpaImplTest {

    @Autowired
    private ClienteDaoJpa clienteDaoJpa;

    private ClienteJpaEntity expectedCliente;

    @BeforeEach
    void setUp() {
        expectedCliente = new ClienteJpaEntity(1L, "usuario1",
                "$2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgOtT7P6D82DUcjh.98Q7Vdfp.8q", "Ismael", "Garcia", "Garcia",
                "12345678A", "token_demo_123");
    }

    @Nested
    @DisplayName("Tests para el método findAll")
    class FindAllTests {
        @Test
        @DisplayName("Debería devolver todos los clientes")
        void shouldReturnAllClientes() {
            List<ClienteJpaEntity> result = clienteDaoJpa.findAll();
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals(expectedCliente.getLogin(), result.get(0).getLogin());
        }
    }

    @Nested
    @DisplayName("Tests para el método findById")
    class FindByIdTests {
        @Test
        @DisplayName("Debería encontrar un cliente por ID")
        void shouldFindById() {
            ClienteJpaEntity result = clienteDaoJpa.findById(1L);
            assertNotNull(result);
            assertEquals("usuario1", result.getLogin());
        }

        @Test
        @DisplayName("Debería devolver null si el ID no existe")
        void shouldReturnNullWhenNotFound() {
            ClienteJpaEntity result = clienteDaoJpa.findById(999L);
            assertNull(result);
        }
    }

    @Nested
    @DisplayName("Tests para el método insert")
    class InsertTests {
        @Test
        @DisplayName("Debería insertar un nuevo cliente")
        void shouldInsertCliente() {
            ClienteJpaEntity newCliente = new ClienteJpaEntity(null, "newuser", "pass", "Name", "A1", "A2", "87654321B",
                    "tok");
            ClienteJpaEntity saved = clienteDaoJpa.insert(newCliente);
            assertNotNull(saved.getId());
            assertEquals("newuser", saved.getLogin());
        }
    }

    @Nested
    @DisplayName("Tests para el método update")
    class UpdateTests {
        @Test
        @DisplayName("Debería actualizar un cliente existente")
        void shouldUpdateCliente() {
            ClienteJpaEntity existing = clienteDaoJpa.findById(1L);
            existing.setNombre("Updated Name");
            ClienteJpaEntity updated = clienteDaoJpa.update(existing);
            assertEquals("Updated Name", updated.getNombre());
        }
    }

    @Nested
    @DisplayName("Tests para el método delete")
    class DeleteTests {
        @Test
        @DisplayName("Debería eliminar un cliente")
        void shouldDeleteCliente() {
            ClienteJpaEntity newCliente = new ClienteJpaEntity(null, "todelete", "pass", "Name", "A1", "A2",
                    "00000000X", "tok");
            clienteDaoJpa.insert(newCliente);
            Long id = newCliente.getId();

            clienteDaoJpa.delete(id);
            assertNull(clienteDaoJpa.findById(id));
        }
    }
}
