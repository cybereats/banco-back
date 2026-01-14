package cybereats.fpmislata.com.banco_back.domain.service.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.domain.repository.CuentaBancariaRepository;
import cybereats.fpmislata.com.banco_back.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaBancariaServiceImplTest {

    @Mock
    private CuentaBancariaRepository cuentaBancariaRepository;

    @InjectMocks
    private CuentaBancariaServiceImpl cuentaBancariaService;

    private CuentaBancariaDto cuentaBancariaDto;
    private ClienteDto clienteDto;

    @BeforeEach
    void setUp() {
        clienteDto = new ClienteDto(1L, "jdoe", "password", "John", "Doe", "Smith", "12345678A", "token123");
        // Correct constructor order: id, saldo, iban, cliente, tarjetas, movimientos
        cuentaBancariaDto = new CuentaBancariaDto(1L, new BigDecimal("1000.00"), "ES1234567890123456789012", clienteDto,
                null, null);
    }

    @Nested
    @DisplayName("Tests para operaciones CRUD básicas")
    class CRUDTests {
        @Test
        @DisplayName("Debería crear una cuenta bancaria")
        void shouldCreateCuenta() {
            when(cuentaBancariaRepository.save(cuentaBancariaDto)).thenReturn(cuentaBancariaDto);
            CuentaBancariaDto result = cuentaBancariaService.create(cuentaBancariaDto);
            assertNotNull(result);
            verify(cuentaBancariaRepository).save(cuentaBancariaDto);
        }

        @Test
        @DisplayName("Debería encontrar una cuenta por ID")
        void shouldFindById() {
            when(cuentaBancariaRepository.findById(1L)).thenReturn(cuentaBancariaDto);
            CuentaBancariaDto result = cuentaBancariaService.findById(1L);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(1L, result.id()));
            verify(cuentaBancariaRepository).findById(1L);
        }
    }

    @Nested
    @DisplayName("Tests para métodos de búsqueda específicos")
    class SearchTests {
        @Test
        @DisplayName("Debería encontrar cuentas por cliente")
        void shouldFindByClient() {
            Page<CuentaBancariaDto> page = new Page<>(List.of(cuentaBancariaDto), 1, 1, 1L);
            when(cuentaBancariaRepository.findByClient(clienteDto)).thenReturn(page);
            Page<CuentaBancariaDto> result = cuentaBancariaService.findByClient(clienteDto);
            assertNotNull(result);
            verify(cuentaBancariaRepository).findByClient(clienteDto);
        }

        @Test
        @DisplayName("Debería encontrar una cuenta por IBAN")
        void shouldFindByIban() {
            when(cuentaBancariaRepository.findByIban("ES123")).thenReturn(cuentaBancariaDto);
            CuentaBancariaDto result = cuentaBancariaService.findByIban("ES123");
            assertNotNull(result);
            verify(cuentaBancariaRepository).findByIban("ES123");
        }
    }

    @Nested
    @DisplayName("Tests para operaciones de ingreso y retiro")
    class TransactionTests {
        @Test
        @DisplayName("Debería ingresar dinero correctamente")
        void shouldIngresarDinero() {
            BigDecimal importe = new BigDecimal("100.00");
            String concepto = "Ingreso test";

            when(cuentaBancariaRepository.ingresar(any(CuentaBancariaDto.class), eq(importe), eq(concepto)))
                    .thenReturn(cuentaBancariaDto);

            CuentaBancariaDto result = cuentaBancariaService.ingresar(cuentaBancariaDto, importe, concepto);

            assertNotNull(result);
            verify(cuentaBancariaRepository).ingresar(any(CuentaBancariaDto.class), eq(importe), eq(concepto));
        }

        @Test
        @DisplayName("Debería retirar dinero correctamente")
        void shouldRetirarDinero() {
            BigDecimal importe = new BigDecimal("50.00");
            String concepto = "Retiro test";

            when(cuentaBancariaRepository.retirar(any(CuentaBancariaDto.class), eq(importe), eq(concepto)))
                    .thenReturn(cuentaBancariaDto);

            CuentaBancariaDto result = cuentaBancariaService.retirar(cuentaBancariaDto, importe, concepto);

            assertNotNull(result);
            verify(cuentaBancariaRepository).retirar(any(CuentaBancariaDto.class), eq(importe), eq(concepto));
        }

        @Test
        @DisplayName("Debería lanzar BusinessException si el concepto es corto")
        void shouldThrowExceptionWhenConceptoTooShort() {
            BigDecimal importe = new BigDecimal("50.00");
            String concepto = "ab";

            assertThrows(BusinessException.class,
                    () -> cuentaBancariaService.ingresar(cuentaBancariaDto, importe, concepto));
        }
    }
}
