package cybereats.fpmislata.com.banco_back.domain.model;

import cybereats.fpmislata.com.banco_back.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para CuentaBancaria")
class CuentaBancariaTest {

    @Nested
    @DisplayName("Tests de Constructor y Getters/Setters")
    class ConstructorAndGettersSetters {
        @Test
        @DisplayName("Debería crear una cuenta con el constructor completo")
        void shouldCreateWithFullConstructor() {
            Cliente cliente = new Cliente();
            CuentaBancaria cuenta = new CuentaBancaria(1L, new BigDecimal("100.00"), "ES1234567890", cliente);

            assertEquals(1L, cuenta.getId());
            assertEquals(new BigDecimal("100.00"), cuenta.getSaldo());
            assertEquals("ES1234567890", cuenta.getIban());
            assertEquals(cliente, cuenta.getCliente());
        }

        @Test
        @DisplayName("Debería funcionar con getters y setters")
        void shouldWorkWithGettersAndSetters() {
            CuentaBancaria cuenta = new CuentaBancaria();
            Cliente cliente = new Cliente();
            List<TarjetaCredito> tarjetas = new ArrayList<>();
            List<MovimientoBancario> movimientos = new ArrayList<>();

            cuenta.setId(2L);
            cuenta.setSaldo(new BigDecimal("50.00"));
            cuenta.setIban("ES0987654321");
            cuenta.setCliente(cliente);
            cuenta.setTarjetas(tarjetas);
            cuenta.setMovimientos(movimientos);

            assertEquals(2L, cuenta.getId());
            assertEquals(new BigDecimal("50.00"), cuenta.getSaldo());
            assertEquals("ES0987654321", cuenta.getIban());
            assertEquals(cliente, cuenta.getCliente());
            assertEquals(tarjetas, cuenta.getTarjetas());
            assertEquals(movimientos, cuenta.getMovimientos());
        }
    }

    @Nested
    @DisplayName("Tests de Lógica de Negocio")
    class BusinessLogic {
        @Test
        @DisplayName("Debería retirar importe correctamente")
        void shouldRetireCorrectly() {
            CuentaBancaria cuenta = new CuentaBancaria();
            cuenta.setSaldo(new BigDecimal("100.00"));

            cuenta.retirar(new BigDecimal("40.00"));

            assertEquals(new BigDecimal("60.00"), cuenta.getSaldo());
        }

        @Test
        @DisplayName("Debería lanzar error al retirar importe negativo o cero")
        void shouldThrowErrorWhenRetireAmountIsNegativeOrZero() {
            CuentaBancaria cuenta = new CuentaBancaria();
            cuenta.setSaldo(new BigDecimal("100.00"));

            assertThrows(IllegalArgumentException.class, () -> cuenta.retirar(new BigDecimal("-10.00")));
            assertThrows(IllegalArgumentException.class, () -> cuenta.retirar(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("Debería lanzar BusinessException si el saldo es insuficiente al retirar")
        void shouldThrowBusinessExceptionWhenInsufficientBalance() {
            CuentaBancaria cuenta = new CuentaBancaria();
            cuenta.setSaldo(new BigDecimal("100.00"));

            BusinessException exception = assertThrows(BusinessException.class,
                    () -> cuenta.retirar(new BigDecimal("150.00")));
            assertEquals("Saldo insuficiente", exception.getMessage());
        }

        @Test
        @DisplayName("Debería ingresar importe correctamente")
        void shouldDepositCorrectly() {
            CuentaBancaria cuenta = new CuentaBancaria();
            cuenta.setSaldo(new BigDecimal("100.00"));

            cuenta.ingresar(new BigDecimal("50.00"));

            assertEquals(new BigDecimal("150.00"), cuenta.getSaldo());
        }

        @Test
        @DisplayName("Debería lanzar error al ingresar importe negativo o cero")
        void shouldThrowErrorWhenDepositAmountIsNegativeOrZero() {
            CuentaBancaria cuenta = new CuentaBancaria();
            cuenta.setSaldo(new BigDecimal("100.00"));

            assertThrows(IllegalArgumentException.class, () -> cuenta.ingresar(new BigDecimal("-10.00")));
            assertThrows(IllegalArgumentException.class, () -> cuenta.ingresar(BigDecimal.ZERO));
        }

        @Test
        @DisplayName("Debería añadir un movimiento correctamente")
        void shouldAddMovimiento() {
            CuentaBancaria cuenta = new CuentaBancaria();
            MovimientoBancario movimiento = new MovimientoBancario();

            cuenta.addMovimiento(movimiento);

            assertTrue(cuenta.getMovimientos().contains(movimiento));
            assertEquals(1, cuenta.getMovimientos().size());
        }
    }
}
