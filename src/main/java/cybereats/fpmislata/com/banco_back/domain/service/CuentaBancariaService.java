package cybereats.fpmislata.com.banco_back.domain.service;

import java.math.BigDecimal;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;

public interface CuentaBancariaService {

    CuentaBancariaDto create(CuentaBancariaDto cuentaBancariaDto);

    CuentaBancariaDto update(CuentaBancariaDto cuentaBancariaDto);

    CuentaBancariaDto ingresar(CuentaBancariaDto cuentaBancariaDto, BigDecimal importe, String concepto);

    CuentaBancariaDto retirar(CuentaBancariaDto cuentaBancariaDto, BigDecimal importe, String concepto);

    CuentaBancariaDto findById(Long id);

    Page<CuentaBancariaDto> findByClient(ClienteDto clienteDto);

    CuentaBancariaDto findByIban(String iban);

    CuentaBancariaDto findByTarjetaCredito(TarjetaCreditoDto tarjetaCreditoDto);

    void delete(Long id);
}
