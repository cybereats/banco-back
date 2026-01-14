package cybereats.fpmislata.com.banco_back.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para TarjetaCredito")
class TarjetaCreditoTest {

    @Test
    @DisplayName("Debería crear una tarjeta con el constructor completo")
    void shouldCreateWithFullConstructor() {
        LocalDate expiry = LocalDate.now().plusYears(1);
        TarjetaCredito tarjeta = new TarjetaCredito(1L, "1234567890123456", expiry, 123, "Nombre Cliente");

        assertEquals(1L, tarjeta.getId());
        assertEquals("1234567890123456", tarjeta.getNumeroTarjeta());
        assertEquals(expiry, tarjeta.getFechaCaducidad());
        assertEquals(123, tarjeta.getCvc());
        assertEquals("Nombre Cliente", tarjeta.getNombreCompleto());
    }

    @Test
    @DisplayName("Debería funcionar con getters y setters")
    void shouldWorkWithGettersAndSetters() {
        TarjetaCredito tarjeta = new TarjetaCredito();
        LocalDate expiry = LocalDate.now().plusYears(1);

        tarjeta.setId(1L);
        tarjeta.setNumeroTarjeta("1234567890123456");
        tarjeta.setFechaCaducidad(expiry);
        tarjeta.setCvc(123);
        tarjeta.setNombreCompleto("Nombre Cliente");

        assertEquals(1L, tarjeta.getId());
        assertEquals("1234567890123456", tarjeta.getNumeroTarjeta());
        assertEquals(expiry, tarjeta.getFechaCaducidad());
        assertEquals(123, tarjeta.getCvc());
        assertEquals("Nombre Cliente", tarjeta.getNombreCompleto());
    }
}
