package cybereats.fpmislata.com.banco_back.presentation.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.MovimientoBancarioDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.MovimientoBancarioRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.response.MovimientoBancarioResponse;

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

    public MovimientoBancarioDto toDto(MovimientoBancarioRequest request) {
        if (request == null) {
            return null;
        }

        return new MovimientoBancarioDto(
                request.id(),
                request.tipoMovimientoBancario(),
                request.origenMovimientoBancario(),
                request.fecha(),
                request.importe(),
                request.concepto(),
                request.tarjetaCreditoId() != null ? mapTarjetaCredito(request.tarjetaCreditoId()) : null,
                request.cuentaBancariaId() != null ? mapCuentaBancaria(request.cuentaBancariaId()) : null);
    }

    public MovimientoBancarioResponse toResponse(MovimientoBancarioDto dto) {
        if (dto == null) {
            return null;
        }

        return new MovimientoBancarioResponse(
                dto.id(),
                dto.tipoMovimientoBancario(),
                dto.origenMovimientoBancario(),
                dto.fecha(),
                dto.importe(),
                dto.concepto(),
                TarjetaCreditoMapper.getInstance().toResponse(dto.tarjetaCreditoOrigen()),
                CuentaBancariaMapper.getInstance().toResponse(dto.cuentaBancaria()));
    }

    private TarjetaCreditoDto mapTarjetaCredito(Long id) {
        return new TarjetaCreditoDto(id, null, null, 0, null);
    }

    private CuentaBancariaDto mapCuentaBancaria(Long id) {
        return new CuentaBancariaDto(id, null, null, null, null);
    }
}
