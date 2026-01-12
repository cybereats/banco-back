package cybereats.fpmislata.com.banco_back.persistence.repository.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
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

    public CuentaBancariaDto toDto(CuentaBancariaJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new CuentaBancariaDto(
                entity.getId(),
                entity.getSaldo(),
                entity.getIban(),
                ClienteMapper.getInstance().toDto(entity.getCliente()),
                entity.getTarjetas() != null
                        ? entity.getTarjetas().stream()
                                .map(TarjetaCreditoMapper.getInstance()::toDto)
                                .toList()
                        : null);
    }

    public CuentaBancariaJpaEntity toEntity(CuentaBancariaDto dto) {
        if (dto == null) {
            return null;
        }

        CuentaBancariaJpaEntity entity = new CuentaBancariaJpaEntity();
        entity.setId(dto.id());
        entity.setSaldo(dto.saldo());
        entity.setIban(dto.iban());
        entity.setCliente(ClienteMapper.getInstance().toEntity(dto.cliente()));
        if (dto.tarjetas() != null) {
            entity.setTarjetas(dto.tarjetas().stream()
                    .map(TarjetaCreditoMapper.getInstance()::toEntity)
                    .toList());
        }

        return entity;
    }
}
