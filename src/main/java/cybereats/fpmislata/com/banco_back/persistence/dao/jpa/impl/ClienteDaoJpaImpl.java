package cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl;

import cybereats.fpmislata.com.banco_back.exception.ResourceNotFoundException;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.ClienteDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Component;

public class ClienteDaoJpaImpl implements ClienteDaoJpa {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ClienteJpaEntity insert(ClienteJpaEntity clienteJpaEntity) {
        entityManager.persist(clienteJpaEntity);
        return clienteJpaEntity;
    }

    @Override
    public ClienteJpaEntity update(ClienteJpaEntity clienteJpaEntity) {
        ClienteJpaEntity managed = entityManager.find(ClienteJpaEntity.class, clienteJpaEntity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException("Cliente with id " + clienteJpaEntity.getId() + " not found");
        }
        return entityManager.merge(clienteJpaEntity);
    }

    @Override
    public ClienteJpaEntity findById(Long id) {
        return entityManager.find(ClienteJpaEntity.class, id);
    }

    @Override
    public List<ClienteJpaEntity> findAll() {
        return entityManager.createQuery("SELECT c FROM ClienteJpaEntity c", ClienteJpaEntity.class).getResultList();
    }

    @Override
    public ClienteJpaEntity findByLogin(String login) {
        try {
            String jpql = "SELECT c FROM ClienteJpaEntity c WHERE c.login = :login";
            return entityManager.createQuery(jpql, ClienteJpaEntity.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        ClienteJpaEntity clienteJpaEntity = findById(id);
        if (clienteJpaEntity != null) {
            entityManager.remove(clienteJpaEntity);
        }
    }
}
