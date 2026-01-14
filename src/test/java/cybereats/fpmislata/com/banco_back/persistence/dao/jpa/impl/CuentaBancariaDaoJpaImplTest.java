package cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl;

import cybereats.fpmislata.com.banco_back.persistence.TestConfig;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.CuentaBancariaDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = "spring.flyway.locations=classpath:db/migration/test")
class CuentaBancariaDaoJpaImplTest {

    @Autowired
    private CuentaBancariaDaoJpa cuentaBancariaDaoJpa;

    @Nested
    @DisplayName("Tests para el método findByIban")
    class FindByIbanTests {
        @Test
        @DisplayName("Debería encontrar una cuenta por IBAN")
        void shouldFindByIban() {
            String iban = "ES1234567890123456789012";
            CuentaBancariaJpaEntity result = cuentaBancariaDaoJpa.findByIban(iban);
            assertNotNull(result);
            assertEquals(1L, result.getId());
        }
    }

    @Nested
    @DisplayName("Tests para el método findByCliente")
    class FindByClienteTests {
        @Test
        @DisplayName("Debería encontrar cuentas por cliente")
        void shouldFindByCliente() {
            ClienteJpaEntity cliente = new ClienteJpaEntity();
            cliente.setId(1L);
            List<CuentaBancariaJpaEntity> result = cuentaBancariaDaoJpa.findByCliente(cliente);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
        }
    }

    @Nested
    @DisplayName("Tests CRUD básicos")
    class CRUDTests {
        @Test
        @DisplayName("Debería insertar una nueva cuenta")
        void shouldInsertCuenta() {
            ClienteJpaEntity cliente = new ClienteJpaEntity();
            cliente.setId(1L);
            CuentaBancariaJpaEntity newCuenta = new CuentaBancariaJpaEntity(null, new BigDecimal("500.00"), "ES999",
                    cliente);
            CuentaBancariaJpaEntity saved = cuentaBancariaDaoJpa.insert(newCuenta);
            assertNotNull(saved.getId());
            assertEquals("ES999", saved.getIban());
        }

        @Test
        @DisplayName("Debería encontrar por ID")
        void shouldFindById() {
            CuentaBancariaJpaEntity result = cuentaBancariaDaoJpa.findById(1L);
            assertNotNull(result);
            assertEquals(1L, result.getId());
        }
    }
}
