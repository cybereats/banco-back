package cybereats.fpmislata.com.banco_back.domain.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.model.TarjetaCredito;

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

    public TarjetaCreditoDto toDto(TarjetaCredito tarjetaCredito) {
        if (tarjetaCredito == null) {
            return null;
        }

        return new TarjetaCreditoDto(
                tarjetaCredito.getId(),
                tarjetaCredito.getNumeroTarjeta(),
                tarjetaCredito.getFechaCaducidad(),
                tarjetaCredito.getCvc(),
                tarjetaCredito.getNombreCompleto());
    }

    public TarjetaCredito toModel(TarjetaCreditoDto tarjetaCreditoDto) {
        if (tarjetaCreditoDto == null) {
            return null;
        }

        return new TarjetaCredito(
                tarjetaCreditoDto.id(),
                tarjetaCreditoDto.numeroTarjeta(),
                tarjetaCreditoDto.fechaCaducidad(),
                tarjetaCreditoDto.cvc(),
                tarjetaCreditoDto.nombreCompleto());
    }
}
