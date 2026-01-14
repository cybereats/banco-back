package cybereats.fpmislata.com.banco_back.domain.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.MovimientoBancarioDto;
import cybereats.fpmislata.com.banco_back.domain.model.MovimientoBancario;

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

    public MovimientoBancarioDto toDto(MovimientoBancario movimientoBancario) {
        if (movimientoBancario == null) {
            return null;
        }

        return new MovimientoBancarioDto(
                movimientoBancario.getId(),
                movimientoBancario.getTipoMovimientoBancario(),
                movimientoBancario.getOrigenMovimientoBancario(),
                movimientoBancario.getFecha(),
                movimientoBancario.getImporte(),
                movimientoBancario.getConcepto(),
                TarjetaCreditoMapper.getInstance().toDto(movimientoBancario.getTarjetaCreditoOrigen()));
    }

    public MovimientoBancario toModel(MovimientoBancarioDto movimientoBancarioDto) {
        if (movimientoBancarioDto == null) {
            return null;
        }

        return new MovimientoBancario(
                movimientoBancarioDto.id(),
                movimientoBancarioDto.tipoMovimientoBancario(),
                movimientoBancarioDto.origenMovimientoBancario(),
                TarjetaCreditoMapper.getInstance().toModel(movimientoBancarioDto.tarjetaCreditoOrigen()),
                movimientoBancarioDto.fecha(),
                movimientoBancarioDto.importe(),
                movimientoBancarioDto.concepto());
    }
}
