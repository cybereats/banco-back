package cybereats.fpmislata.com.banco_back.persistence.dao.jpa;

import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.TarjetaCreditoJpaEntity;

import java.util.List;

import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.ClienteJpaEntity;

public interface CuentaBancariaDaoJpa {

    CuentaBancariaJpaEntity insert(CuentaBancariaJpaEntity cuentaBancariaJpaEntity);

    CuentaBancariaJpaEntity update(CuentaBancariaJpaEntity cuentaBancariaJpaEntity);

    CuentaBancariaJpaEntity findById(Long id);

    CuentaBancariaJpaEntity findByIban(String iban);

    CuentaBancariaJpaEntity findByTarjetaCredito(TarjetaCreditoJpaEntity tarjetaCreditoJpaEntity);

    List<CuentaBancariaJpaEntity> findByCliente(ClienteJpaEntity clienteJpaEntity);

    void delete(Long id);
}
