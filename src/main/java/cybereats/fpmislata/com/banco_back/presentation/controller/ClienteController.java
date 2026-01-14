package cybereats.fpmislata.com.banco_back.presentation.controller;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.domain.service.ClienteService;
import cybereats.fpmislata.com.banco_back.domain.validation.DtoValidator;
import cybereats.fpmislata.com.banco_back.presentation.mapper.ClienteMapper;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.ClienteRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.response.ClienteResponse;
import cybereats.fpmislata.com.banco_back.security.AuthRequired;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AuthRequired
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> createCliente(@RequestBody ClienteRequest clienteRequest) {
        DtoValidator.validate(clienteRequest);
        ClienteDto clienteDto = ClienteMapper.getInstance().toDto(clienteRequest);
        ClienteDto createdClienteDto = clienteService.create(clienteDto);
        return new ResponseEntity<>(ClienteMapper.getInstance().toResponse(createdClienteDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Long id) {
        ClienteDto clienteDto = clienteService.findById(id);
        return new ResponseEntity<>(ClienteMapper.getInstance().toResponse(clienteDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponse>> getAllClientes(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        // Validation of pagination params could be here if needed
        Page<ClienteDto> clienteDtoPage = clienteService.findAll(); // Service currently returns all
        List<ClienteResponse> content = clienteDtoPage.data().stream()
                .map(ClienteMapper.getInstance()::toResponse)
                .toList();
        return new ResponseEntity<>(new Page<>(content, clienteDtoPage.pageNumber(), clienteDtoPage.pageSize(),
                clienteDtoPage.totalElements()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> updateCliente(@PathVariable Long id,
            @RequestBody ClienteRequest clienteRequest) {
        if (!id.equals(clienteRequest.id())) {
            throw new IllegalArgumentException("El id del cliente no coincide con el id proporcionado");
        }
        DtoValidator.validate(clienteRequest);
        ClienteDto clienteDto = ClienteMapper.getInstance().toDto(clienteRequest);
        ClienteDto updatedClienteDto = clienteService.update(clienteDto);
        return new ResponseEntity<>(ClienteMapper.getInstance().toResponse(updatedClienteDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
