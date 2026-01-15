package cybereats.fpmislata.com.banco_back.persistence.repository.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.domain.repository.ClienteRepository;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.ClienteDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.repository.mapper.ClienteMapper;

import java.util.List;

public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteDaoJpa clienteDaoJpa;

    public ClienteRepositoryImpl(ClienteDaoJpa clienteDaoJpa) {
        this.clienteDaoJpa = clienteDaoJpa;
    }

    @Override
    public ClienteDto save(ClienteDto clienteDto) {
        ClienteJpaEntity clienteJpaEntity = ClienteMapper.getInstance().toEntity(clienteDto);
        if (clienteJpaEntity.getId() == null) {
            return ClienteMapper.getInstance().toDto(clienteDaoJpa.insert(clienteJpaEntity));
        } else {
            return ClienteMapper.getInstance().toDto(clienteDaoJpa.update(clienteJpaEntity));
        }
    }

    @Override
    public ClienteDto findById(Long id) {
        return ClienteMapper.getInstance().toDto(clienteDaoJpa.findById(id));
    }

    @Override
    public Page<ClienteDto> findAll() {
        List<ClienteJpaEntity> list = clienteDaoJpa.findAll();
        List<ClienteDto> dtoList = list.stream()
                .map(ClienteMapper.getInstance()::toDto)
                .toList();
        return new Page<>(dtoList, 1, Math.max(1, dtoList.size()), dtoList.size());
    }

    @Override
    public ClienteDto findByLogin(String login) {
        return ClienteMapper.getInstance().toDto(clienteDaoJpa.findByLogin(login));
    }

    @Override
    public void delete(Long id) {
        clienteDaoJpa.delete(id);
    }
}
