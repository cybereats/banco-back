package cybereats.fpmislata.com.banco_back.persistence.dao.jpa;

import java.util.List;

import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;

public interface ClienteDaoJpa {

    ClienteJpaEntity insert(ClienteJpaEntity clienteJpaEntity);

    ClienteJpaEntity update(ClienteJpaEntity clienteJpaEntity);

    ClienteJpaEntity findById(Long id);

    List<ClienteJpaEntity> findAll();

    ClienteJpaEntity findByLogin(String login);

    void delete(Long id);
}
