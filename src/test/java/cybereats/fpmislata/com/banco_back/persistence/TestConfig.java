package cybereats.fpmislata.com.banco_back.persistence;

import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.ClienteDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.CuentaBancariaDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.MovimientoBancarioDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.TarjetaCreditoDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl.ClienteDaoJpaImpl;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl.CuentaBancariaDaoJpaImpl;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl.MovimientoBancarioDaoJpaImpl;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl.TarjetaCreditoDaoJpaImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public ClienteDaoJpa clienteDaoJpa() {
        return new ClienteDaoJpaImpl();
    }

    @Bean
    public CuentaBancariaDaoJpa cuentaBancariaDaoJpa() {
        return new CuentaBancariaDaoJpaImpl();
    }

    @Bean
    public MovimientoBancarioDaoJpa movimientoBancarioDaoJpa() {
        return new MovimientoBancarioDaoJpaImpl();
    }

    @Bean
    public TarjetaCreditoDaoJpa tarjetaCreditoDaoJpa() {
        return new TarjetaCreditoDaoJpaImpl();
    }
}
