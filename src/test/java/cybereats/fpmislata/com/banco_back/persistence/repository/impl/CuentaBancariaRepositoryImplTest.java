package cybereats.fpmislata.com.banco_back.persistence.repository.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.CuentaBancariaDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.MovimientoBancarioDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.MovimientoBancarioJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.TarjetaCreditoJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para CuentaBancariaRepositoryImpl")
class CuentaBancariaRepositoryImplTest {

    @Mock
    private CuentaBancariaDaoJpa cuentaBancariaDaoJpa;

    @Mock
    private MovimientoBancarioDaoJpa movimientoBancarioDaoJpa;

    @InjectMocks
    private CuentaBancariaRepositoryImpl cuentaBancariaRepository;

    private ClienteJpaEntity clienteJpaEntity;
    private ClienteDto clienteDto;
    private CuentaBancariaJpaEntity cuentaBancariaJpaEntity;
    private CuentaBancariaDto cuentaBancariaDto;

    @BeforeEach
    void setUp() {
        clienteJpaEntity = new ClienteJpaEntity(1L, "jdoe", "pass", "John", "Doe", null, "12345678A", "tok");
        clienteDto = new ClienteDto(1L, "jdoe", "pass", "John", "Doe", null, "12345678A", "tok");

        cuentaBancariaJpaEntity = new CuentaBancariaJpaEntity(1L, new BigDecimal("1000.00"), "ES1234567890123456789012",
                clienteJpaEntity);
        cuentaBancariaDto = new CuentaBancariaDto(1L, new BigDecimal("1000.00"), "ES1234567890123456789012", clienteDto,
                null, null);
    }

    @Nested
    @DisplayName("Tests para búsquedas")
    class SearchTests {
        @Test
        @DisplayName("Debería encontrar una cuenta por ID")
        void shouldFindById() {
            when(cuentaBancariaDaoJpa.findById(1L)).thenReturn(cuentaBancariaJpaEntity);

            CuentaBancariaDto result = cuentaBancariaRepository.findById(1L);

            assertNotNull(result);
            assertEquals(1L, result.id());
            verify(cuentaBancariaDaoJpa).findById(1L);
        }

        @Test
        @DisplayName("Debería encontrar cuentas por cliente")
        void shouldFindByClient() {
            when(cuentaBancariaDaoJpa.findByCliente(any(ClienteJpaEntity.class)))
                    .thenReturn(List.of(cuentaBancariaJpaEntity));

            Page<CuentaBancariaDto> result = cuentaBancariaRepository.findByClient(clienteDto);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(1, result.data().size()));
            verify(cuentaBancariaDaoJpa).findByCliente(any(ClienteJpaEntity.class));
        }

        @Test
        @DisplayName("Debería encontrar una cuenta por IBAN")
        void shouldFindByIban() {
            when(cuentaBancariaDaoJpa.findByIban("ES123")).thenReturn(cuentaBancariaJpaEntity);

            CuentaBancariaDto result = cuentaBancariaRepository.findByIban("ES123");

            assertNotNull(result);
            assertEquals("ES1234567890123456789012", result.iban());
        }
    }

    @Nested
    @DisplayName("Tests para el método save")
    class SaveTests {
        @Test
        @DisplayName("Debería insertar cuando el ID es nulo")
        void shouldInsertWhenIdIsNull() {
            CuentaBancariaDto dtoToSave = new CuentaBancariaDto(null, BigDecimal.ZERO, "ESNEW", clienteDto, null, null);
            CuentaBancariaJpaEntity savedEntity = new CuentaBancariaJpaEntity(10L, BigDecimal.ZERO, "ESNEW",
                    clienteJpaEntity);

            when(cuentaBancariaDaoJpa.insert(any(CuentaBancariaJpaEntity.class))).thenReturn(savedEntity);

            CuentaBancariaDto result = cuentaBancariaRepository.save(dtoToSave);

            assertNotNull(result);
            assertEquals(10L, result.id());
            verify(cuentaBancariaDaoJpa).insert(any(CuentaBancariaJpaEntity.class));
        }

        @Test
        @DisplayName("Debería actualizar cuando el ID no es nulo")
        void shouldUpdateWhenIdIsNotNull() {
            when(cuentaBancariaDaoJpa.update(any(CuentaBancariaJpaEntity.class))).thenReturn(cuentaBancariaJpaEntity);

            CuentaBancariaDto result = cuentaBancariaRepository.save(cuentaBancariaDto);

            assertNotNull(result);
            assertEquals(1L, result.id());
            verify(cuentaBancariaDaoJpa).update(any(CuentaBancariaJpaEntity.class));
        }
    }

    @Nested
    @DisplayName("Tests para ingresos y retiros")
    class TransactionRepositoryTests {
        @Test
        @DisplayName("Debería registrar un ingreso")
        void shouldIngresar() {
            when(cuentaBancariaDaoJpa.update(any(CuentaBancariaJpaEntity.class))).thenReturn(cuentaBancariaJpaEntity);
            when(movimientoBancarioDaoJpa.insert(any(MovimientoBancarioJpaEntity.class)))
                    .thenReturn(new MovimientoBancarioJpaEntity());

            CuentaBancariaDto result = cuentaBancariaRepository.ingresar(cuentaBancariaDto, new BigDecimal("100"),
                    "Ingreso Test");

            assertNotNull(result);
            verify(movimientoBancarioDaoJpa).insert(any(MovimientoBancarioJpaEntity.class));
        }

        @Test
        @DisplayName("Debería registrar un retiro")
        void shouldRetirar() {
            when(cuentaBancariaDaoJpa.update(any(CuentaBancariaJpaEntity.class))).thenReturn(cuentaBancariaJpaEntity);
            when(movimientoBancarioDaoJpa.insert(any(MovimientoBancarioJpaEntity.class)))
                    .thenReturn(new MovimientoBancarioJpaEntity());

            CuentaBancariaDto result = cuentaBancariaRepository.retirar(cuentaBancariaDto, new BigDecimal("50"),
                    "Retiro Test");

            assertNotNull(result);
            verify(movimientoBancarioDaoJpa).insert(any(MovimientoBancarioJpaEntity.class));
        }
    }
}
