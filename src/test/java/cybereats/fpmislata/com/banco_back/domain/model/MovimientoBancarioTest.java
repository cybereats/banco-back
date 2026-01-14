package cybereats.fpmislata.com.banco_back.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para MovimientoBancario")
class MovimientoBancarioTest {

    @Test
    @DisplayName("Debería crear un movimiento bancario con el constructor completo")
    void shouldCreateWithFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        TarjetaCredito tarjeta = new TarjetaCredito();
        MovimientoBancario movimiento = new MovimientoBancario(1L, TipoMovimientoBancario.DEBE,
                OrigenMovimientoBancario.TARJETA_BANCARIA, tarjeta, now, new BigDecimal("10.00"), "Cena");

        assertEquals(1L, movimiento.getId());
        assertEquals(TipoMovimientoBancario.DEBE, movimiento.getTipoMovimientoBancario());
        assertEquals(OrigenMovimientoBancario.TARJETA_BANCARIA, movimiento.getOrigenMovimientoBancario());
        assertEquals(tarjeta, movimiento.getTarjetaCreditoOrigen());
        assertEquals(now, movimiento.getFecha());
        assertEquals(new BigDecimal("10.00"), movimiento.getImporte());
        assertEquals("Cena", movimiento.getConcepto());
    }

    @Test
    @DisplayName("Debería funcionar con getters y setters")
    void shouldWorkWithGettersAndSetters() {
        MovimientoBancario movimiento = new MovimientoBancario();
        LocalDateTime now = LocalDateTime.now();
        TarjetaCredito tarjeta = new TarjetaCredito();

        movimiento.setId(1L);
        movimiento.setTipoMovimientoBancario(TipoMovimientoBancario.HABER);
        movimiento.setOrigenMovimientoBancario(OrigenMovimientoBancario.TRANSFERENCIA);
        movimiento.setTarjetaCreditoOrigen(tarjeta);
        movimiento.setFecha(now);
        movimiento.setImporte(new BigDecimal("20.00"));
        movimiento.setConcepto("Ingreso");

        assertEquals(1L, movimiento.getId());
        assertEquals(TipoMovimientoBancario.HABER, movimiento.getTipoMovimientoBancario());
        assertEquals(OrigenMovimientoBancario.TRANSFERENCIA, movimiento.getOrigenMovimientoBancario());
        assertEquals(tarjeta, movimiento.getTarjetaCreditoOrigen());
        assertEquals(now, movimiento.getFecha());
        assertEquals(new BigDecimal("20.00"), movimiento.getImporte());
        assertEquals("Ingreso", movimiento.getConcepto());
    }
}
