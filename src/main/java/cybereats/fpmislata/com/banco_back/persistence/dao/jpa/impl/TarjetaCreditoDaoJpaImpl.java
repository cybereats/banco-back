package cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl;

import cybereats.fpmislata.com.banco_back.exception.ResourceNotFoundException;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.TarjetaCreditoDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.TarjetaCreditoJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.stereotype.Component;

public class TarjetaCreditoDaoJpaImpl implements TarjetaCreditoDaoJpa {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TarjetaCreditoJpaEntity> findByCuentaBancaria(CuentaBancariaJpaEntity cuentaBancariaJpaEntity) {
        String jpql = "SELECT t FROM TarjetaCreditoJpaEntity t WHERE t.cuentaBancaria.id = :cuentaId";
        TypedQuery<TarjetaCreditoJpaEntity> query = entityManager.createQuery(jpql, TarjetaCreditoJpaEntity.class);
        query.setParameter("cuentaId", cuentaBancariaJpaEntity.getId());
        return query.getResultList();
    }

    @Override
    public TarjetaCreditoJpaEntity findByNumeroTarjeta(String numeroTarjeta) {
        String jpql = "SELECT t FROM TarjetaCreditoJpaEntity t WHERE t.numeroTarjeta = :numeroTarjeta";
        TypedQuery<TarjetaCreditoJpaEntity> query = entityManager.createQuery(jpql, TarjetaCreditoJpaEntity.class);
        query.setParameter("numeroTarjeta", numeroTarjeta);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public TarjetaCreditoJpaEntity insert(TarjetaCreditoJpaEntity tarjetaCreditoJpaEntity) {
        entityManager.persist(tarjetaCreditoJpaEntity);
        return tarjetaCreditoJpaEntity;
    }

    @Override
    public TarjetaCreditoJpaEntity update(TarjetaCreditoJpaEntity tarjetaCreditoJpaEntity) {
        TarjetaCreditoJpaEntity managed = entityManager.find(TarjetaCreditoJpaEntity.class,
                tarjetaCreditoJpaEntity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException(
                    "Tarjeta de credito with id " + tarjetaCreditoJpaEntity.getId() + " not found");
        }
        return entityManager.merge(tarjetaCreditoJpaEntity);
    }

    @Override
    public TarjetaCreditoJpaEntity findById(Long id) {
        return entityManager.find(TarjetaCreditoJpaEntity.class, id);
    }

    @Override
    public List<TarjetaCreditoJpaEntity> findAll() {
        return entityManager.createQuery("SELECT t FROM TarjetaCreditoJpaEntity t", TarjetaCreditoJpaEntity.class)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        TarjetaCreditoJpaEntity entity = findById(id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
