package cybereats.fpmislata.com.banco_back.presentation.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteProfileResponseDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;

public class AuthMapper {
    private static AuthMapper INSTANCE;

    private AuthMapper() {
    }

    public static AuthMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthMapper();
        }
        return INSTANCE;
    }

    public ClienteProfileResponseDto toProfileResponse(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        return new ClienteProfileResponseDto(
                cliente.getId(),
                cliente.getLogin(),
                cliente.getNombre(),
                cliente.getApellido1(),
                cliente.getApellido2(),
                cliente.getDni());
    }
}
