package cybereats.fpmislata.com.banco_back.persistence.repository.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.TarjetaCreditoDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
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
@DisplayName("Tests para TarjetaCreditoRepositoryImpl")
class TarjetaCreditoRepositoryImplTest {

    @Mock
    private TarjetaCreditoDaoJpa tarjetaCreditoDaoJpa;

    @InjectMocks
    private TarjetaCreditoRepositoryImpl tarjetaCreditoRepository;

    private TarjetaCreditoJpaEntity tarjetaJpaEntity;
    private TarjetaCreditoDto tarjetaDto;
    private CuentaBancariaDto cuentaDto;
    private CuentaBancariaJpaEntity cuentaJpaEntity;

    @BeforeEach
    void setUp() {
        ClienteJpaEntity clienteJpa = new ClienteJpaEntity(1L, "jdoe", "pass", "John", "Doe", null, "12345678A", "tok");
        cuentaJpaEntity = new CuentaBancariaJpaEntity(1L, new BigDecimal("100"), "ES123", clienteJpa);
        tarjetaJpaEntity = new TarjetaCreditoJpaEntity(1L, "1234567890123456", LocalDate.of(2026, 12, 1), 123,
                "John Doe", cuentaJpaEntity);

        ClienteDto clienteDto = new ClienteDto(1L, "jdoe", "pass", "John", "Doe", null, "12345678A", "tok");
        cuentaDto = new CuentaBancariaDto(1L, new BigDecimal("100"), "ES123", clienteDto, null, null);
        tarjetaDto = new TarjetaCreditoDto(1L, "1234567890123456", LocalDate.of(2026, 12, 1), 123, "John Doe");
    }

    @Nested
    @DisplayName("Tests para búsquedas de tarjetas")
    class SearchTests {
        @Test
        @DisplayName("Debería encontrar por cuenta bancaria")
        void shouldFindByCuenta() {
            when(tarjetaCreditoDaoJpa.findByCuentaBancaria(any(CuentaBancariaJpaEntity.class)))
                    .thenReturn(List.of(tarjetaJpaEntity));

            List<TarjetaCreditoDto> result = tarjetaCreditoRepository.findByCuentaBancaria(cuentaDto);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(1, result.size()),
                    () -> assertEquals("1234567890123456", result.get(0).numeroTarjeta()));
            verify(tarjetaCreditoDaoJpa).findByCuentaBancaria(any(CuentaBancariaJpaEntity.class));
        }

        @Test
        @DisplayName("Debería encontrar por número de tarjeta")
        void shouldFindByNumero() {
            when(tarjetaCreditoDaoJpa.findByNumeroTarjeta("1234567890123456")).thenReturn(tarjetaJpaEntity);

            TarjetaCreditoDto result = tarjetaCreditoRepository.findByNumeroTarjeta("1234567890123456");

            assertNotNull(result);
            assertEquals(1L, result.id());
        }

        @Test
        @DisplayName("Debería devolver todas las tarjetas")
        void shouldFindAll() {
            when(tarjetaCreditoDaoJpa.findAll()).thenReturn(List.of(tarjetaJpaEntity));

            List<TarjetaCreditoDto> result = tarjetaCreditoRepository.findAll();

            assertEquals(1, result.size());
        }
    }

    @Nested
    @DisplayName("Tests para el método save")
    class SaveTests {
        @Test
        @DisplayName("Debería insertar cuando el ID es nulo")
        void shouldInsertWhenIdIsNull() {
            TarjetaCreditoDto dtoToSave = new TarjetaCreditoDto(null, "1111222233334444", LocalDate.of(2027, 1, 1), 999,
                    "New Card");
            TarjetaCreditoJpaEntity savedEntity = new TarjetaCreditoJpaEntity(5L, "1111222233334444",
                    LocalDate.of(2027, 1, 1), 999, "New Card", cuentaJpaEntity);

            when(tarjetaCreditoDaoJpa.insert(any(TarjetaCreditoJpaEntity.class))).thenReturn(savedEntity);

            TarjetaCreditoDto result = tarjetaCreditoRepository.save(dtoToSave);

            assertNotNull(result);
            assertEquals(5L, result.id());
            verify(tarjetaCreditoDaoJpa).insert(any(TarjetaCreditoJpaEntity.class));
        }

        @Test
        @DisplayName("Debería actualizar cuando el ID no es nulo")
        void shouldUpdateWhenIdIsNotNull() {
            when(tarjetaCreditoDaoJpa.update(any(TarjetaCreditoJpaEntity.class))).thenReturn(tarjetaJpaEntity);

            TarjetaCreditoDto result = tarjetaCreditoRepository.save(tarjetaDto);

            assertNotNull(result);
            assertEquals(1L, result.id());
            verify(tarjetaCreditoDaoJpa).update(any(TarjetaCreditoJpaEntity.class));
        }
    }

    @Nested
    @DisplayName("Tests para el método delete")
    class DeleteTests {
        @Test
        @DisplayName("Debería llamar al DAO para borrar por ID")
        void shouldDelete() {
            doNothing().when(tarjetaCreditoDaoJpa).delete(1L);

            tarjetaCreditoRepository.delete(1L);

            verify(tarjetaCreditoDaoJpa).delete(1L);
        }
    }
}
