package cybereats.fpmislata.com.banco_back.persistence.repository.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import cybereats.fpmislata.com.banco_back.domain.repository.AuthRepository;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.ClienteDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;
import java.util.Optional;

public class AuthRepositoryImpl implements AuthRepository {

    private final ClienteDaoJpa clienteDaoJpa;

    public AuthRepositoryImpl(ClienteDaoJpa clienteDaoJpa) {
        this.clienteDaoJpa = clienteDaoJpa;
    }

    @Override
    public Optional<Cliente> findByLogin(String login) {
        ClienteJpaEntity entity = clienteDaoJpa.findByLogin(login);
        if (entity == null) {
            return Optional.empty();
        }

        // Map Entity -> DTO (Persistence Mapper) -> Model (Domain Mapper)
        ClienteDto dto = cybereats.fpmislata.com.banco_back.persistence.repository.mapper.ClienteMapper.getInstance()
                .toDto(entity);
        Cliente model = cybereats.fpmislata.com.banco_back.domain.mapper.ClienteMapper.getInstance().toModel(dto);

        return Optional.of(model);
    }

    @Override
    public void save(Cliente cliente) {
        // Map Model -> DTO (Domain Mapper) -> Entity (Persistence Mapper)
        ClienteDto dto = cybereats.fpmislata.com.banco_back.domain.mapper.ClienteMapper.getInstance().toDto(cliente);
        ClienteJpaEntity entity = cybereats.fpmislata.com.banco_back.persistence.repository.mapper.ClienteMapper
                .getInstance().toEntity(dto);

        if (entity.getId() == null) {
            clienteDaoJpa.insert(entity);
        } else {
            clienteDaoJpa.update(entity);
        }
    }
}
