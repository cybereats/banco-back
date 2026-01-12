package cybereats.fpmislata.com.banco_back.domain.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;

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

    public ClienteDto toDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDto(
                cliente.getId(),
                cliente.getLogin(),
                cliente.getPassword(),
                cliente.getNombre(),
                cliente.getApellido1(),
                cliente.getApellido2(),
                cliente.getDni(),
                cliente.getApiToken());
    }

    public Cliente toModel(ClienteDto clienteDto) {
        if (clienteDto == null) {
            return null;
        }

        return new Cliente(
                clienteDto.id(),
                clienteDto.login(),
                clienteDto.password(),
                clienteDto.nombre(),
                clienteDto.apellido1(),
                clienteDto.apellido2(),
                clienteDto.dni(),
                clienteDto.apiToken());
    }
}
