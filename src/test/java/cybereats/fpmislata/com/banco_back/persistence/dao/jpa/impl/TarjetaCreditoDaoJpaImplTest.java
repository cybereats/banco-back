package cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl;

import cybereats.fpmislata.com.banco_back.persistence.TestConfig;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.TarjetaCreditoDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.TarjetaCreditoJpaEntity;
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
class TarjetaCreditoDaoJpaImplTest {

    @Autowired
    private TarjetaCreditoDaoJpa tarjetaCreditoDaoJpa;

    @Nested
    @DisplayName("Tests para el método findByNumeroTarjeta")
    class FindByNumeroTests {
        @Test
        @DisplayName("Debería encontrar una tarjeta por su número")
        void shouldFindByNumero() {
            String numero = "1234567890123456";
            TarjetaCreditoJpaEntity result = tarjetaCreditoDaoJpa.findByNumeroTarjeta(numero);
            assertNotNull(result);
            assertEquals(1L, result.getId());
            assertEquals(123, result.getCvc());
        }
    }

    @Nested
    @DisplayName("Tests para el método findByCuentaBancaria")
    class FindByCuentaTests {
        @Test
        @DisplayName("Debería encontrar tarjetas asociadas a una cuenta")
        void shouldFindByCuenta() {
            CuentaBancariaJpaEntity cuenta = new CuentaBancariaJpaEntity();
            cuenta.setId(1L);
            List<TarjetaCreditoJpaEntity> result = tarjetaCreditoDaoJpa.findByCuentaBancaria(cuenta);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
        }
    }

    @Nested
    @DisplayName("Tests CRUD básicos")
    class CRUDTests {
        @Test
        @DisplayName("Debería devolver todas las tarjetas")
        void shouldFindAll() {
            List<TarjetaCreditoJpaEntity> result = tarjetaCreditoDaoJpa.findAll();
            assertFalse(result.isEmpty());
            assertTrue(result.size() >= 1);
        }

        @Test
        @DisplayName("Debería encontrar por ID")
        void shouldFindById() {
            TarjetaCreditoJpaEntity result = tarjetaCreditoDaoJpa.findById(1L);
            assertNotNull(result);
            assertEquals("1234567890123456", result.getNumeroTarjeta());
        }
    }
}
