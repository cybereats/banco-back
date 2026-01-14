package cybereats.fpmislata.com.banco_back.domain.service.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.repository.TarjetaCreditoRepository;
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
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarjetaCreditoServiceImplTest {

    @Mock
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    @InjectMocks
    private TarjetaCreditoServiceImpl tarjetaCreditoService;

    private TarjetaCreditoDto tarjetaCreditoDto;
    private CuentaBancariaDto cuentaBancariaDto;

    @BeforeEach
    void setUp() {
        ClienteDto clienteDto = new ClienteDto(1L, "jdoe", "pass", "John", "Doe", null, "12345678A", null);
        cuentaBancariaDto = new CuentaBancariaDto(1L, new BigDecimal("100"), "ES123", clienteDto, null, null);
        tarjetaCreditoDto = new TarjetaCreditoDto(1L, "1234567890123456", LocalDate.of(2026, 12, 1), 123, "John Doe");
    }

    @Nested
    @DisplayName("Tests para el método validate")
    class ValidateTests {
        @Test
        @DisplayName("Debería validar correctamente una tarjeta")
        void shouldValidateSuccessfully() {
            when(tarjetaCreditoRepository.findByNumeroTarjeta(tarjetaCreditoDto.numeroTarjeta()))
                    .thenReturn(tarjetaCreditoDto);

            assertDoesNotThrow(() -> tarjetaCreditoService.validate(tarjetaCreditoDto));
        }

        @Test
        @DisplayName("Debería lanzar error si la tarjeta no existe")
        void shouldThrowWhenNotFound() {
            when(tarjetaCreditoRepository.findByNumeroTarjeta(anyString())).thenReturn(null);

            assertThrows(BusinessException.class, () -> tarjetaCreditoService.validate(tarjetaCreditoDto));
        }

        @Test
        @DisplayName("Debería lanzar error si la fecha de caducidad no coincide")
        void shouldThrowWhenExpiryMismatch() {
            TarjetaCreditoDto storedCard = new TarjetaCreditoDto(1L, "1234567890123456", LocalDate.of(2025, 1, 1), 123,
                    "John Doe");
            when(tarjetaCreditoRepository.findByNumeroTarjeta(anyString())).thenReturn(storedCard);

            assertThrows(BusinessException.class, () -> tarjetaCreditoService.validate(tarjetaCreditoDto));
        }

        @Test
        @DisplayName("Debería lanzar error si el CVC no coincide")
        void shouldThrowWhenCvcMismatch() {
            TarjetaCreditoDto storedCard = new TarjetaCreditoDto(1L, "1234567890123456", LocalDate.of(2026, 12, 1), 999,
                    "John Doe");
            when(tarjetaCreditoRepository.findByNumeroTarjeta(anyString())).thenReturn(storedCard);

            assertThrows(BusinessException.class, () -> tarjetaCreditoService.validate(tarjetaCreditoDto));
        }
    }

    @Nested
    @DisplayName("Tests CRUD y búsquedas")
    class CrudAndSearchTests {
        @Test
        @DisplayName("Debería encontrar por cuenta bancaria")
        void shouldFindByCuenta() {
            when(tarjetaCreditoRepository.findByCuentaBancaria(cuentaBancariaDto))
                    .thenReturn(List.of(tarjetaCreditoDto));

            List<TarjetaCreditoDto> result = tarjetaCreditoService.findByCuentaBancaria(cuentaBancariaDto);

            assertEquals(1, result.size());
            verify(tarjetaCreditoRepository).findByCuentaBancaria(cuentaBancariaDto);
        }

        @Test
        @DisplayName("Debería crear una tarjeta")
        void shouldCreate() {
            when(tarjetaCreditoRepository.save(tarjetaCreditoDto)).thenReturn(tarjetaCreditoDto);

            TarjetaCreditoDto result = tarjetaCreditoService.create(tarjetaCreditoDto);

            assertNotNull(result);
            verify(tarjetaCreditoRepository).save(tarjetaCreditoDto);
        }
    }
}
