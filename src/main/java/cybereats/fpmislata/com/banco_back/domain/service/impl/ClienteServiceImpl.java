package cybereats.fpmislata.com.banco_back.domain.service.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.domain.repository.ClienteRepository;
import cybereats.fpmislata.com.banco_back.domain.service.ClienteService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDto create(ClienteDto clienteDto) {
        return clienteRepository.save(clienteDto);
    }

    @Override
    public ClienteDto update(ClienteDto clienteDto) {
        return clienteRepository.save(clienteDto);
    }

    @Override
    public ClienteDto findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Page<ClienteDto> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        clienteRepository.delete(id);
    }

    @Override
    public void validate(String login, String apiToken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }
}
