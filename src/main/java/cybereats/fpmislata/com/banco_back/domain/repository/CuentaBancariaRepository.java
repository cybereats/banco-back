package cybereats.fpmislata.com.banco_back.domain.repository;

import java.math.BigDecimal;
import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;

public interface CuentaBancariaRepository {

    CuentaBancariaDto save(CuentaBancariaDto cuentaBancariaDto);

    CuentaBancariaDto findById(Long id);

    CuentaBancariaDto findByIban(String iban);

    Page<CuentaBancariaDto> findByClient(ClienteDto clienteDto);

    CuentaBancariaDto findByTarjetaCredito(TarjetaCreditoDto tarjetaCreditoDto);

    CuentaBancariaDto ingresar(CuentaBancariaDto cuentaBancariaDto, BigDecimal importe, String concepto);

    CuentaBancariaDto retirar(CuentaBancariaDto cuentaBancariaDto, BigDecimal importe, String concepto);

    void delete(Long id);
}
