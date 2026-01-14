package cybereats.fpmislata.com.banco_back.persistence.dao.jpa;

import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.TarjetaCreditoJpaEntity;
import java.util.List;

public interface TarjetaCreditoDaoJpa {

    List<TarjetaCreditoJpaEntity> findByCuentaBancaria(CuentaBancariaJpaEntity cuentaBancariaJpaEntity);

    TarjetaCreditoJpaEntity findByNumeroTarjeta(String numeroTarjeta);

    TarjetaCreditoJpaEntity insert(TarjetaCreditoJpaEntity tarjetaCreditoJpaEntity);

    TarjetaCreditoJpaEntity update(TarjetaCreditoJpaEntity tarjetaCreditoJpaEntity);

    TarjetaCreditoJpaEntity findById(Long id);

    List<TarjetaCreditoJpaEntity> findAll();

    void delete(Long id);
}
