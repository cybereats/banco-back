package cybereats.fpmislata.com.banco_back.domain.repository;

import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import java.util.Optional;

public interface AuthRepository {
    Optional<Cliente> findByLogin(String login);

    void save(Cliente cliente);
}
