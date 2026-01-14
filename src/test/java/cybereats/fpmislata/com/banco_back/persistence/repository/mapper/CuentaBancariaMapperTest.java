package cybereats.fpmislata.com.banco_back.persistence.repository.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CuentaBancariaMapperTest {

    private final CuentaBancariaMapper cuentaBancariaMapper = CuentaBancariaMapper.getInstance();

    @Test
    @DisplayName("Debería mapear de CuentaBancariaJpaEntity a CuentaBancariaDto")
    void shouldMapToDto() {
        ClienteJpaEntity clienteEntity = new ClienteJpaEntity(1L, "user", "pass", "Name", "A1", "A2", "12345678A",
                "token");
        CuentaBancariaJpaEntity entity = new CuentaBancariaJpaEntity();
        entity.setId(1L);
        entity.setSaldo(new BigDecimal("1000.00"));
        entity.setIban("ES1234567890");
        entity.setCliente(clienteEntity);
        entity.setTarjetas(Collections.emptyList());
        entity.setMovimientos(Collections.emptyList());

        CuentaBancariaDto dto = cuentaBancariaMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getSaldo(), dto.saldo());
        assertEquals(entity.getIban(), dto.iban());
        assertNotNull(dto.cliente());
        assertEquals(clienteEntity.getId(), dto.cliente().id());
        assertNotNull(dto.tarjetas());
        assertNotNull(dto.movimientos());
    }

    @Test
    @DisplayName("Debería mapear de CuentaBancariaDto a CuentaBancariaJpaEntity")
    void shouldMapToEntity() {
        ClienteDto clienteDto = new ClienteDto(1L, "user", "pass", "Name", "A1", "A2", "12345678A", "token");
        CuentaBancariaDto dto = new CuentaBancariaDto(1L, new BigDecimal("1000.00"), "ES1234567890", clienteDto,
                Collections.emptyList(), Collections.emptyList());

        CuentaBancariaJpaEntity entity = cuentaBancariaMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.id(), entity.getId());
        assertEquals(dto.saldo(), entity.getSaldo());
        assertEquals(dto.iban(), entity.getIban());
        assertNotNull(entity.getCliente());
        assertEquals(clienteDto.id(), entity.getCliente().getId());
        assertNotNull(entity.getTarjetas());
        assertNotNull(entity.getMovimientos());
    }

    @Test
    @DisplayName("Debería devolver null si el objeto a mapear es null")
    void shouldReturnNullWhenSourceIsNull() {
        assertNull(cuentaBancariaMapper.toDto(null));
        assertNull(cuentaBancariaMapper.toEntity(null));
    }
}
