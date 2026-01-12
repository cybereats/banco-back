package cybereats.fpmislata.com.banco_back.presentation.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.ClienteRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.response.ClienteResponse;

public class ClienteMapper {

    private static ClienteMapper INSTANCE;

    private ClienteMapper() {
    }

    public static ClienteMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClienteMapper();
        }
        return INSTANCE;
    }

    public ClienteDto toDto(ClienteRequest request) {
        if (request == null) {
            return null;
        }

        return new ClienteDto(
                request.id(),
                request.login(),
                request.password(),
                request.nombre(),
                request.apellido1(),
                request.apellido2(),
                request.dni(),
                request.apiToken());
    }

    public ClienteResponse toResponse(ClienteDto dto) {
        if (dto == null) {
            return null;
        }

        return new ClienteResponse(
                dto.id(),
                dto.login(),
                dto.nombre(),
                dto.apellido1(),
                dto.apellido2(),
                dto.dni(),
                dto.apiToken());
    }
}
