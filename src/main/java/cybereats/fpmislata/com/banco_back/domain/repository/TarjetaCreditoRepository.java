package cybereats.fpmislata.com.banco_back.domain.repository;

import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import java.util.List;

public interface TarjetaCreditoRepository {

    List<TarjetaCreditoDto> findByCuentaBancaria(CuentaBancariaDto cuentaBancariaDto);

    TarjetaCreditoDto findByNumeroTarjeta(String numeroTarjeta);

    TarjetaCreditoDto save(TarjetaCreditoDto tarjetaCreditoDto);

    TarjetaCreditoDto findById(Long id);

    List<TarjetaCreditoDto> findAll();

    void delete(Long id);
}
