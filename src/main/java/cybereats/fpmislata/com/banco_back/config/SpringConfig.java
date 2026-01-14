package cybereats.fpmislata.com.banco_back.config;

import cybereats.fpmislata.com.banco_back.domain.repository.AuthRepository;
import cybereats.fpmislata.com.banco_back.domain.repository.ClienteRepository;
import cybereats.fpmislata.com.banco_back.domain.repository.CuentaBancariaRepository;
import cybereats.fpmislata.com.banco_back.domain.repository.TarjetaCreditoRepository;
import cybereats.fpmislata.com.banco_back.domain.service.AuthService;
import cybereats.fpmislata.com.banco_back.domain.service.ClienteService;
import cybereats.fpmislata.com.banco_back.domain.service.CuentaBancariaService;
import cybereats.fpmislata.com.banco_back.domain.service.PagoTarjeta;
import cybereats.fpmislata.com.banco_back.domain.service.TarjetaCreditoService;
import cybereats.fpmislata.com.banco_back.domain.service.impl.AuthServiceImpl;
import cybereats.fpmislata.com.banco_back.domain.service.impl.ClienteServiceImpl;
import cybereats.fpmislata.com.banco_back.domain.service.impl.CuentaBancariaServiceImpl;
import cybereats.fpmislata.com.banco_back.domain.service.impl.PagoTarjetaImpl;
import cybereats.fpmislata.com.banco_back.domain.service.impl.TarjetaCreditoServiceImpl;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.ClienteDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.CuentaBancariaDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.MovimientoBancarioDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.TarjetaCreditoDaoJpa;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl.ClienteDaoJpaImpl;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl.CuentaBancariaDaoJpaImpl;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl.MovimientoBancarioDaoJpaImpl;
import cybereats.fpmislata.com.banco_back.persistence.dao.jpa.impl.TarjetaCreditoDaoJpaImpl;
import cybereats.fpmislata.com.banco_back.persistence.repository.impl.AuthRepositoryImpl;
import cybereats.fpmislata.com.banco_back.persistence.repository.impl.ClienteRepositoryImpl;
import cybereats.fpmislata.com.banco_back.persistence.repository.impl.CuentaBancariaRepositoryImpl;
import cybereats.fpmislata.com.banco_back.persistence.repository.impl.TarjetaCreditoRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // DAOs
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

    // Repositories
    @Bean
    public AuthRepository authRepository(ClienteDaoJpa clienteDaoJpa) {
        return new AuthRepositoryImpl(clienteDaoJpa);
    }

    @Bean
    public ClienteRepository clienteRepository(ClienteDaoJpa clienteDaoJpa) {
        return new ClienteRepositoryImpl(clienteDaoJpa);
    }

    @Bean
    public CuentaBancariaRepository cuentaBancariaRepository(CuentaBancariaDaoJpa cuentaBancariaDaoJpa,
            MovimientoBancarioDaoJpa movimientoBancarioDaoJpa) {
        return new CuentaBancariaRepositoryImpl(cuentaBancariaDaoJpa, movimientoBancarioDaoJpa);
    }

    @Bean
    public TarjetaCreditoRepository tarjetaCreditoRepository(TarjetaCreditoDaoJpa tarjetaCreditoDaoJpa) {
        return new TarjetaCreditoRepositoryImpl(tarjetaCreditoDaoJpa);
    }

    // Services
    @Bean
    public AuthService authService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        return new AuthServiceImpl(authRepository, passwordEncoder);
    }

    @Bean
    public ClienteService clienteService(ClienteRepository clienteRepository) {
        return new ClienteServiceImpl(clienteRepository);
    }

    @Bean
    public CuentaBancariaService cuentaBancariaService(CuentaBancariaRepository cuentaBancariaRepository) {
        return new CuentaBancariaServiceImpl(cuentaBancariaRepository);
    }

    @Bean
    public TarjetaCreditoService tarjetaCreditoService(TarjetaCreditoRepository tarjetaCreditoRepository) {
        return new TarjetaCreditoServiceImpl(tarjetaCreditoRepository);
    }

    @Bean
    public PagoTarjeta pagoTarjeta(ClienteService clienteService, CuentaBancariaService cuentaBancariaService,
            TarjetaCreditoService tarjetaCreditoService) {
        return new PagoTarjetaImpl(clienteService, cuentaBancariaService, tarjetaCreditoService);
    }
}
