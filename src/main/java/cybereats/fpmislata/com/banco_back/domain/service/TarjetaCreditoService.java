package cybereats.fpmislata.com.banco_back.domain.service;

import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import java.util.List;

public interface TarjetaCreditoService {

    List<TarjetaCreditoDto> findByCuentaBancaria(CuentaBancariaDto cuentaBancariaDto);

    void validate(TarjetaCreditoDto tarjetaCreditoDto);

    TarjetaCreditoDto create(TarjetaCreditoDto tarjetaCreditoDto);

    TarjetaCreditoDto update(TarjetaCreditoDto tarjetaCreditoDto);

    TarjetaCreditoDto findById(Long id);

    List<TarjetaCreditoDto> findAll();

    void delete(Long id);
}
