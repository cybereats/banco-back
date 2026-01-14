package cybereats.fpmislata.com.banco_back.persistence.dao.jpa;

import java.util.List;

import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.CuentaBancariaJpaEntity;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.entity.MovimientoBancarioJpaEntity;

public interface MovimientoBancarioDaoJpa {

    MovimientoBancarioJpaEntity insert(MovimientoBancarioJpaEntity movimientoBancarioJpaEntity);

    MovimientoBancarioJpaEntity update(MovimientoBancarioJpaEntity movimientoBancarioJpaEntity);

    MovimientoBancarioJpaEntity findById(Long id);

    List<MovimientoBancarioJpaEntity> findByCuentaBancaria(CuentaBancariaJpaEntity cuentaBancariaJpaEntity);

    void delete(Long id);
}
