package cybereats.fpmislata.com.banco_back.domain.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import cybereats.fpmislata.com.banco_back.domain.model.CuentaBancaria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CuentaBancariaMapperTest {

    private final CuentaBancariaMapper cuentaBancariaMapper = CuentaBancariaMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de CuentaBancaria a CuentaBancariaDto")
    void shouldMapToDto() {
        Cliente cliente = new Cliente(1L, "user", "pass", "Name", "A1", "A2", "12345678A", "token");
        CuentaBancaria cuenta = new CuentaBancaria(1L, new BigDecimal("1000.00"), "ES1234567890", cliente);
        cuenta.setTarjetas(Collections.emptyList());
        cuenta.setMovimientos(Collections.emptyList());

        CuentaBancariaDto dto = cuentaBancariaMapper.toDto(cuenta);

        assertNotNull(dto);
        assertEquals(cuenta.getId(), dto.id());
        assertEquals(cuenta.getSaldo(), dto.saldo());
        assertEquals(cuenta.getIban(), dto.iban());
        assertNotNull(dto.cliente());
        assertEquals(cliente.getId(), dto.cliente().id());
        assertNotNull(dto.tarjetas());
        assertNotNull(dto.movimientos());
    }

    @Test
    @DisplayName("Debería mapear de CuentaBancariaDto a CuentaBancaria")
    void shouldMapToModel() {
        ClienteDto clienteDto = new ClienteDto(1L, "user", "pass", "Name", "A1", "A2", "12345678A", "token");
        CuentaBancariaDto dto = new CuentaBancariaDto(1L, new BigDecimal("1000.00"), "ES1234567890", clienteDto,
                Collections.emptyList(), Collections.emptyList());

        CuentaBancaria model = cuentaBancariaMapper.toModel(dto);

        assertNotNull(model);
        assertEquals(dto.id(), model.getId());
        assertEquals(dto.saldo(), model.getSaldo());
        assertEquals(dto.iban(), model.getIban());
        assertNotNull(model.getCliente());
        assertEquals(clienteDto.id(), model.getCliente().getId());
        assertNotNull(model.getTarjetas());
        assertNotNull(model.getMovimientos());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(cuentaBancariaMapper.toDto(null));
        assertNull(cuentaBancariaMapper.toModel(null));
    }
}
