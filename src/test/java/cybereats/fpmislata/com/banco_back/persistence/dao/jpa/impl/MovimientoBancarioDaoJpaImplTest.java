package cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl;

import cybereats.fpmislata.com.banco_back.domain.model.TipoMovimientoBancario;
import cybereats.fpmislata.com.banco_back.domain.model.OrigenMovimientoBancario;
import cybereats.fpmislata.com.banco_back.persistence.TestConfig;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.MovimientoBancarioDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.MovimientoBancarioJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = "spring.flyway.locations=classpath:db/migration/test")
class MovimientoBancarioDaoJpaImplTest {

    @Autowired
    private MovimientoBancarioDaoJpa movimientoBancarioDaoJpa;

    @Nested
    @DisplayName("Tests para el método insert")
    class InsertTests {
        @Test
        @DisplayName("Debería insertar un nuevo movimiento")
        void shouldInsertMovimiento() {
            CuentaBancariaJpaEntity cuenta = new CuentaBancariaJpaEntity();
            cuenta.setId(1L);

            MovimientoBancarioJpaEntity movimiento = new MovimientoBancarioJpaEntity();
            movimiento.setTipoMovimientoBancario(TipoMovimientoBancario.HABER);
            movimiento.setOrigenMovimientoBancario(OrigenMovimientoBancario.TRANSFERENCIA);
            movimiento.setFecha(LocalDateTime.now());
            movimiento.setImporte(new BigDecimal("100.00"));
            movimiento.setConcepto("Test Insert");
            movimiento.setCuentaBancaria(cuenta);

            MovimientoBancarioJpaEntity saved = movimientoBancarioDaoJpa.insert(movimiento);

            assertNotNull(saved.getId());
            assertEquals("Test Insert", saved.getConcepto());
        }
    }

    @Nested
    @DisplayName("Tests para el método findByCuentaBancaria")
    class FindByCuentaTests {
        @Test
        @DisplayName("Debería encontrar movimientos por cuenta")
        void shouldFindByCuenta() {
            CuentaBancariaJpaEntity cuenta = new CuentaBancariaJpaEntity();
            cuenta.setId(1L);
            List<MovimientoBancarioJpaEntity> result = movimientoBancarioDaoJpa.findByCuentaBancaria(cuenta);
            assertFalse(result.isEmpty());
            assertTrue(result.size() >= 3); // Sembrado tiene 3
        }
    }

    @Nested
    @DisplayName("Tests CRUD básicos")
    class CRUDTests {
        @Test
        @DisplayName("Debería encontrar por ID")
        void shouldFindById() {
            MovimientoBancarioJpaEntity result = movimientoBancarioDaoJpa.findById(1L);
            assertNotNull(result);
            assertEquals(1L, result.getId());
            assertEquals("Nomina Enero", result.getConcepto());
        }
    }
}
