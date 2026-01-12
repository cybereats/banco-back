package cybereats.fpmislata.com.banco_back.domain.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.model.CuentaBancaria;
import java.util.stream.Collectors;

public class CuentaBancariaMapper {
    private static CuentaBancariaMapper INSTANCE;

    private CuentaBancariaMapper() {
    }

    public static CuentaBancariaMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CuentaBancariaMapper();
        }
        return INSTANCE;
    }

    public CuentaBancariaDto toDto(CuentaBancaria cuentaBancaria) {
        if (cuentaBancaria == null) {
            return null;
        }

        return new CuentaBancariaDto(
                cuentaBancaria.getId(),
                cuentaBancaria.getSaldo(),
                cuentaBancaria.getIban(),
                ClienteMapper.getInstance().toDto(cuentaBancaria.getCliente()),
                cuentaBancaria.getTarjetas() != null
                        ? cuentaBancaria.getTarjetas().stream()
                                .map(TarjetaCreditoMapper.getInstance()::toDto)
                                .toList()
                        : null);
    }

    public CuentaBancaria toModel(CuentaBancariaDto cuentaBancariaDto) {
        if (cuentaBancariaDto == null) {
            return null;
        }

        CuentaBancaria cuentaBancaria = new CuentaBancaria(
                cuentaBancariaDto.id(),
                cuentaBancariaDto.saldo(),
                cuentaBancariaDto.iban(),
                ClienteMapper.getInstance().toModel(cuentaBancariaDto.cliente()));

        if (cuentaBancariaDto.tarjetas() != null) {
            cuentaBancaria.setTarjetas(cuentaBancariaDto.tarjetas().stream()
                    .map(TarjetaCreditoMapper.getInstance()::toModel)
                    .toList());
        }

        return cuentaBancaria;
    }
}
