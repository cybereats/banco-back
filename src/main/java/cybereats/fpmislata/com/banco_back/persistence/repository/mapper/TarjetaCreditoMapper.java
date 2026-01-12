package cybereats.fpmislata.com.banco_back.persistence.repository.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.TarjetaCreditoJpaEntity;

public class TarjetaCreditoMapper {
    private static TarjetaCreditoMapper INSTANCE;

    private TarjetaCreditoMapper() {
    }

    public static TarjetaCreditoMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TarjetaCreditoMapper();
        }
        return INSTANCE;
    }

    public TarjetaCreditoDto toDto(TarjetaCreditoJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new TarjetaCreditoDto(
                entity.getId(),
                entity.getNumeroTarjeta(),
                entity.getFechaCaducidad(),
                entity.getCvc(),
                entity.getNombreCompleto());
    }

    public TarjetaCreditoJpaEntity toEntity(TarjetaCreditoDto dto) {
        if (dto == null) {
            return null;
        }

        TarjetaCreditoJpaEntity entity = new TarjetaCreditoJpaEntity();
        entity.setId(dto.id());
        entity.setNumeroTarjeta(dto.numeroTarjeta());
        entity.setFechaCaducidad(dto.fechaCaducidad());
        entity.setCvc(dto.cvc());
        entity.setNombreCompleto(dto.nombreCompleto());

        return entity;
    }
}
