package cybereats.fpmislata.com.banco_back.presentation.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.TarjetaCreditoRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.response.TarjetaCreditoResponse;

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

    public TarjetaCreditoDto toDto(TarjetaCreditoRequest request) {
        if (request == null) {
            return null;
        }

        return new TarjetaCreditoDto(
                request.id(),
                request.numeroTarjeta(),
                request.fechaCaducidad(),
                request.cvc(),
                request.nombreCompleto());
    }

    public TarjetaCreditoResponse toResponse(TarjetaCreditoDto dto) {
        if (dto == null) {
            return null;
        }

        return new TarjetaCreditoResponse(
                dto.id(),
                dto.numeroTarjeta(),
                dto.fechaCaducidad(),
                dto.cvc(),
                dto.nombreCompleto());
    }
}
