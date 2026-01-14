package cybereats.fpmislata.com.banco_back.presentation.controller;

import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.service.TarjetaCreditoService;
import cybereats.fpmislata.com.banco_back.domain.validation.DtoValidator;
import cybereats.fpmislata.com.banco_back.presentation.mapper.TarjetaCreditoMapper;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.TarjetaCreditoRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.response.TarjetaCreditoResponse;
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
@RequestMapping("/api/tarjetas")
public class TarjetaCreditoController {

    private final TarjetaCreditoService tarjetaCreditoService;

    public TarjetaCreditoController(TarjetaCreditoService tarjetaCreditoService) {
        this.tarjetaCreditoService = tarjetaCreditoService;
    }

    @PostMapping
    public ResponseEntity<TarjetaCreditoResponse> createTarjeta(
            @RequestBody TarjetaCreditoRequest tarjetaCreditoRequest) {
        DtoValidator.validate(tarjetaCreditoRequest);
        TarjetaCreditoDto tarjetaCreditoDto = TarjetaCreditoMapper.getInstance().toDto(tarjetaCreditoRequest);
        TarjetaCreditoDto createdTarjeta = tarjetaCreditoService.create(tarjetaCreditoDto);
        return new ResponseEntity<>(TarjetaCreditoMapper.getInstance().toResponse(createdTarjeta), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarjetaCreditoResponse> getTarjetaById(@PathVariable Long id) {
        TarjetaCreditoDto tarjetaCreditoDto = tarjetaCreditoService.findById(id);
        return new ResponseEntity<>(TarjetaCreditoMapper.getInstance().toResponse(tarjetaCreditoDto), HttpStatus.OK);
    }

    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<TarjetaCreditoResponse>> getTarjetasByCuenta(@PathVariable Long cuentaId) {
        // Construct partial CuentaBancariaDto with ID
        CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto(cuentaId, null, null, null, null, null);
        List<TarjetaCreditoDto> tarjetasDto = tarjetaCreditoService.findByCuentaBancaria(cuentaBancariaDto);

        List<TarjetaCreditoResponse> response = tarjetasDto.stream()
                .map(TarjetaCreditoMapper.getInstance()::toResponse)
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarjetaCreditoResponse> updateTarjeta(@PathVariable Long id,
            @RequestBody TarjetaCreditoRequest tarjetaCreditoRequest) {
        if (!id.equals(tarjetaCreditoRequest.id())) {
            throw new IllegalArgumentException("El id de la tarjeta no coincide con el id proporcionado");
        }
        DtoValidator.validate(tarjetaCreditoRequest);
        TarjetaCreditoDto tarjetaCreditoDto = TarjetaCreditoMapper.getInstance().toDto(tarjetaCreditoRequest);
        TarjetaCreditoDto updatedTarjeta = tarjetaCreditoService.update(tarjetaCreditoDto);
        return new ResponseEntity<>(TarjetaCreditoMapper.getInstance().toResponse(updatedTarjeta), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarjeta(@PathVariable Long id) {
        tarjetaCreditoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
