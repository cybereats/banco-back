package cybereats.fpmislata.com.banco_back.domain.repository;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;

public interface ClienteRepository {
    ClienteDto save(ClienteDto clienteDto);

    ClienteDto findById(Long id);

    Page<ClienteDto> findAll();

    void delete(Long id);
}
