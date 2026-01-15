package cybereats.fpmislata.com.banco_back.presentation.controller;

import cybereats.fpmislata.com.banco_back.domain.service.PagoTarjeta;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.PagoTarjetaRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagos")
public class PagoTarjetaController {

    private final PagoTarjeta pagoTarjeta;

    public PagoTarjetaController(PagoTarjeta pagoTarjeta) {
        this.pagoTarjeta = pagoTarjeta;
    }

    @PostMapping("/tarjeta")
    public ResponseEntity<Void> pagoTarjeta(@RequestBody PagoTarjetaRequest pagoTarjetaRequest) {
        pagoTarjeta.pagoTarjeta(pagoTarjetaRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
