package cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl;

import cybereats.fpmislata.com.banco_back.exception.ResourceNotFoundException;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.CuentaBancariaDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.TarjetaCreditoJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.stereotype.Component;

public class CuentaBancariaDaoJpaImpl implements CuentaBancariaDaoJpa {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CuentaBancariaJpaEntity insert(CuentaBancariaJpaEntity cuentaBancariaJpaEntity) {
        entityManager.persist(cuentaBancariaJpaEntity);
        return cuentaBancariaJpaEntity;
    }

    @Override
    public CuentaBancariaJpaEntity update(CuentaBancariaJpaEntity cuentaBancariaJpaEntity) {
        CuentaBancariaJpaEntity managed = entityManager.find(CuentaBancariaJpaEntity.class,
                cuentaBancariaJpaEntity.getId());
        if (managed == null) {
            throw new ResourceNotFoundException(
                    "Cuenta bancaria with id " + cuentaBancariaJpaEntity.getId() + " not found");
        }
        return entityManager.merge(cuentaBancariaJpaEntity);
    }

    @Override
    public CuentaBancariaJpaEntity findById(Long id) {
        return entityManager.find(CuentaBancariaJpaEntity.class, id);
    }

    @Override
    public CuentaBancariaJpaEntity findByIban(String iban) {
        String jpql = "SELECT c FROM CuentaBancariaJpaEntity c WHERE c.iban = :iban";
        TypedQuery<CuentaBancariaJpaEntity> query = entityManager.createQuery(jpql, CuentaBancariaJpaEntity.class);
        query.setParameter("iban", iban);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CuentaBancariaJpaEntity findByTarjetaCredito(TarjetaCreditoJpaEntity tarjetaCreditoJpaEntity) {
        String jpql = "SELECT c FROM CuentaBancariaJpaEntity c JOIN TarjetaCreditoJpaEntity t ON t.cuentaBancaria.id = c.id WHERE t.numeroTarjeta = :numeroTarjeta";
        TypedQuery<CuentaBancariaJpaEntity> query = entityManager.createQuery(jpql, CuentaBancariaJpaEntity.class);
        query.setParameter("numeroTarjeta", tarjetaCreditoJpaEntity.getNumeroTarjeta());

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CuentaBancariaJpaEntity> findByCliente(ClienteJpaEntity clienteJpaEntity) {
        String jpql = "SELECT c FROM CuentaBancariaJpaEntity c WHERE c.cliente.id = :clienteId";
        TypedQuery<CuentaBancariaJpaEntity> query = entityManager.createQuery(jpql, CuentaBancariaJpaEntity.class);
        query.setParameter("clienteId", clienteJpaEntity.getId());
        return query.getResultList();
    }

    @Override
    public void delete(Long id) {
        CuentaBancariaJpaEntity entity = findById(id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
