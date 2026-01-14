package cybereats.fpmislata.com.banco_back.presentation.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.CuentaBancariaRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.response.CuentaBancariaResponse;

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

    public CuentaBancariaDto toDto(CuentaBancariaRequest request) {
        if (request == null) {
            return null;
        }

        return new CuentaBancariaDto(
                request.id(),
                request.saldo(),
                request.iban(),
                request.clienteId() != null ? mapCliente(request.clienteId()) : null,
                request.tarjetas() != null
                        ? request.tarjetas().stream()
                                .map(TarjetaCreditoMapper.getInstance()::toDto)
                                .toList()
                        : null,
                null);
    }

    public CuentaBancariaResponse toResponse(CuentaBancariaDto dto) {
        if (dto == null) {
            return null;
        }

        return new CuentaBancariaResponse(
                dto.id(),
                dto.saldo(),
                dto.iban(),
                ClienteMapper.getInstance().toResponse(dto.cliente()),
                dto.tarjetas() != null
                        ? dto.tarjetas().stream()
                                .map(TarjetaCreditoMapper.getInstance()::toResponse)
                                .toList()
                        : null,
                dto.movimientos() != null
                        ? dto.movimientos().stream()
                                .map(MovimientoBancarioMapper.getInstance()::toResponse)
                                .toList()
                        : null);
    }

    private ClienteDto mapCliente(Long id) {
        return new ClienteDto(id, null, null, null, null, null, null, null);
    }
}
