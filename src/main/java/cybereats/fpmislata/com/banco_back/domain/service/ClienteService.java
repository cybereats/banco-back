package cybereats.fpmislata.com.banco_back.domain.service;

import java.util.List;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;

public interface ClienteService {

    ClienteDto create(ClienteDto clienteDto);

    ClienteDto update(ClienteDto clienteDto);

    ClienteDto findById(Long id);

    Page<ClienteDto> findAll();

    void delete(Long id);

    void validate(String login, String apiToken);
}
