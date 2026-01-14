package cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl;

import cybereats.fpmislata.com.banco_back.exception.ResourceNotFoundException;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.MovimientoBancarioDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.MovimientoBancarioJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.stereotype.Component;

public class MovimientoBancarioDaoJpaImpl implements MovimientoBancarioDaoJpa {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MovimientoBancarioJpaEntity insert(MovimientoBancarioJpaEntity movimientoBancarioJpaEntity) {
        entityManager.persist(movimientoBancarioJpaEntity);
        return movimientoBancarioJpaEntity;
    }

    @Override
    public MovimientoBancarioJpaEntity update(MovimientoBancarioJpaEntity movimientoBancarioJpaEntity) {
        MovimientoBancarioJpaEntity managed = entityManager.find(MovimientoBancarioJpaEntity.class,
                movimientoBancarioJpaEntity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException(
                    "Movimiento bancario with id " + movimientoBancarioJpaEntity.getId() + " not found");
        }
        return entityManager.merge(movimientoBancarioJpaEntity);
    }

    @Override
    public MovimientoBancarioJpaEntity findById(Long id) {
        return entityManager.find(MovimientoBancarioJpaEntity.class, id);
    }

    @Override
    public List<MovimientoBancarioJpaEntity> findByCuentaBancaria(CuentaBancariaJpaEntity cuentaBancariaJpaEntity) {
        String jpql = "SELECT m FROM MovimientoBancarioJpaEntity m WHERE m.cuentaBancaria.id = :cuentaId";
        TypedQuery<MovimientoBancarioJpaEntity> query = entityManager.createQuery(jpql,
                MovimientoBancarioJpaEntity.class);
        query.setParameter("cuentaId", cuentaBancariaJpaEntity.getId());
        return query.getResultList();
    }

    @Override
    public void delete(Long id) {
        MovimientoBancarioJpaEntity entity = findById(id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
