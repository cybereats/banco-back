package cybereats.fpmislata.com.banco_back.presentation.controller;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.domain.service.CuentaBancariaService;
import cybereats.fpmislata.com.banco_back.domain.validation.DtoValidator;
import cybereats.fpmislata.com.banco_back.presentation.mapper.CuentaBancariaMapper;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.CuentaBancariaRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.response.CuentaBancariaResponse;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.Pago;
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
import org.springframework.web.bind.annotation.RestController;

@AuthRequired
@RestController
@RequestMapping("/api/cuentas")
public class CuentaBancariaController {

    private final CuentaBancariaService cuentaBancariaService;

    public CuentaBancariaController(CuentaBancariaService cuentaBancariaService) {
        this.cuentaBancariaService = cuentaBancariaService;
    }

    @PostMapping
    public ResponseEntity<CuentaBancariaResponse> createCuenta(
            @RequestBody CuentaBancariaRequest cuentaBancariaRequest) {
        DtoValidator.validate(cuentaBancariaRequest);
        CuentaBancariaDto cuentaBancariaDto = CuentaBancariaMapper.getInstance().toDto(cuentaBancariaRequest);
        CuentaBancariaDto createdCuenta = cuentaBancariaService.create(cuentaBancariaDto);
        return new ResponseEntity<>(CuentaBancariaMapper.getInstance().toResponse(createdCuenta), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaBancariaResponse> getCuentaById(@PathVariable Long id) {
        CuentaBancariaDto cuentaBancariaDto = cuentaBancariaService.findById(id);
        return new ResponseEntity<>(CuentaBancariaMapper.getInstance().toResponse(cuentaBancariaDto), HttpStatus.OK);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Page<CuentaBancariaResponse>> getCuentasByCliente(@PathVariable Long clienteId) {
        // Creating a dummy request or dto just to hold the ID, or Service should find
        // by ID.
        // Service finds by ClienteDto.
        // We can reconstruct a partial ClienteDto with just the ID.
        ClienteDto clientDto = new ClienteDto(clienteId, "", "", "", null, null, "00000000X", null);
        Page<CuentaBancariaDto> cuentaDtoPage = cuentaBancariaService.findByClient(clientDto);

        List<CuentaBancariaResponse> content = cuentaDtoPage.data().stream()
                .map(CuentaBancariaMapper.getInstance()::toResponse)
                .toList();
        return new ResponseEntity<>(new Page<>(content, cuentaDtoPage.pageNumber(), cuentaDtoPage.pageSize(),
                cuentaDtoPage.totalElements()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaBancariaResponse> updateCuenta(@PathVariable Long id,
            @RequestBody CuentaBancariaRequest cuentaBancariaRequest) {
        if (!id.equals(cuentaBancariaRequest.id())) {
            throw new IllegalArgumentException("El id de la cuenta no coincide con el id proporcionado");
        }
        DtoValidator.validate(cuentaBancariaRequest);
        CuentaBancariaDto cuentaBancariaDto = CuentaBancariaMapper.getInstance().toDto(cuentaBancariaRequest);
        CuentaBancariaDto updatedCuenta = cuentaBancariaService.update(cuentaBancariaDto);
        return new ResponseEntity<>(CuentaBancariaMapper.getInstance().toResponse(updatedCuenta), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        cuentaBancariaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/ingresar")
    public ResponseEntity<CuentaBancariaResponse> ingresar(@PathVariable Long id,
            @RequestBody Pago pago) {
        CuentaBancariaDto cuentaBancariaDto = cuentaBancariaService.findById(id);
        CuentaBancariaDto updatedCuenta = cuentaBancariaService.ingresar(cuentaBancariaDto, pago.importe(),
                pago.concepto());
        return new ResponseEntity<>(CuentaBancariaMapper.getInstance().toResponse(updatedCuenta), HttpStatus.OK);
    }

    @PostMapping("/{id}/retirar")
    public ResponseEntity<CuentaBancariaResponse> retirar(@PathVariable Long id,
            @RequestBody Pago pago) {
        CuentaBancariaDto cuentaBancariaDto = cuentaBancariaService.findById(id);
        CuentaBancariaDto updatedCuenta = cuentaBancariaService.retirar(cuentaBancariaDto, pago.importe(),
                pago.concepto());
        return new ResponseEntity<>(CuentaBancariaMapper.getInstance().toResponse(updatedCuenta), HttpStatus.OK);
    }
}
