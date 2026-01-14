package cybereats.fpmislata.com.banco_back.persistence.repository.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.MovimientoBancarioDto;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.MovimientoBancarioJpaEntity;

public class MovimientoBancarioMapper {
    private static MovimientoBancarioMapper INSTANCE;

    private MovimientoBancarioMapper() {
    }

    public static MovimientoBancarioMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovimientoBancarioMapper();
        }
        return INSTANCE;
    }

    public MovimientoBancarioDto toDto(MovimientoBancarioJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new MovimientoBancarioDto(
                entity.getId(),
                entity.getTipoMovimientoBancario(),
                entity.getOrigenMovimientoBancario(),
                entity.getFecha(),
                entity.getImporte(),
                entity.getConcepto(),
                TarjetaCreditoMapper.getInstance().toDto(entity.getTarjetaCreditoOrigen()));
    }

    public MovimientoBancarioJpaEntity toEntity(MovimientoBancarioDto dto) {
        if (dto == null) {
            return null;
        }

        MovimientoBancarioJpaEntity entity = new MovimientoBancarioJpaEntity();
        entity.setId(dto.id());
        entity.setTipoMovimientoBancario(dto.tipoMovimientoBancario());
        entity.setOrigenMovimientoBancario(dto.origenMovimientoBancario());
        entity.setFecha(dto.fecha());
        entity.setImporte(dto.importe());
        entity.setConcepto(dto.concepto());
        entity.setTarjetaCreditoOrigen(TarjetaCreditoMapper.getInstance().toEntity(dto.tarjetaCreditoOrigen()));

        return entity;
    }
}
