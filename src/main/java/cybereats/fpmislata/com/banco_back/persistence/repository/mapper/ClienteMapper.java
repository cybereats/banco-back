package cybereats.fpmislata.com.banco_back.persistence.repository.mapper;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;

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

    public ClienteDto toDto(ClienteJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ClienteDto(
                entity.getId(),
                entity.getLogin(),
                entity.getPassword(),
                entity.getNombre(),
                entity.getApellido1(),
                entity.getApellido2(),
                entity.getDni(),
                entity.getApiToken());
    }

    public ClienteJpaEntity toEntity(ClienteDto dto) {
        if (dto == null) {
            return null;
        }

        return new ClienteJpaEntity(
                dto.id(),
                dto.login(),
                dto.password(),
                dto.nombre(),
                dto.apellido1(),
                dto.apellido2(),
                dto.dni(),
                dto.apiToken());
    }
}
