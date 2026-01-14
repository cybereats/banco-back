package cybereats.fpmislata.com.banco_back.domain.service.impl;

import cybereats.fpmislata.com.banco_back.domain.mapper.CuentaBancariaMapper;
import cybereats.fpmislata.com.banco_back.domain.model.CuentaBancaria;
import cybereats.fpmislata.com.banco_back.domain.service.PagoTarjeta;
import cybereats.fpmislata.com.banco_back.domain.service.TarjetaCreditoService;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.PagoTarjetaRequest;
import cybereats.fpmislata.com.banco_back.domain.service.ClienteService;
import cybereats.fpmislata.com.banco_back.domain.service.CuentaBancariaService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PagoTarjetaImpl implements PagoTarjeta {

        private final ClienteService clienteService;
        private final CuentaBancariaService cuentaBancariaService;
        private final TarjetaCreditoService tarjetaCreditoService;

        public PagoTarjetaImpl(ClienteService clienteService, CuentaBancariaService cuentaBancariaService,
                        TarjetaCreditoService tarjetaCreditoService) {
                this.clienteService = clienteService;
                this.cuentaBancariaService = cuentaBancariaService;
                this.tarjetaCreditoService = tarjetaCreditoService;
        }

        @Override
        public void pagoTarjeta(PagoTarjetaRequest pagoTarjetaRequest) {

                CuentaBancaria cuentaBancariaOrigen = CuentaBancariaMapper.getInstance()
                                .toModel(cuentaBancariaService.findByTarjetaCredito(pagoTarjetaRequest.origen()));
                CuentaBancaria cuentaBancariaDestino = CuentaBancariaMapper.getInstance()
                                .toModel(cuentaBancariaService.findByIban(pagoTarjetaRequest.destino().iban()));

                clienteService.validate(cuentaBancariaOrigen.getCliente().getLogin(),
                                cuentaBancariaOrigen.getCliente().getApiToken());
                tarjetaCreditoService.validate(pagoTarjetaRequest.origen());

                cuentaBancariaService.retirar(CuentaBancariaMapper.getInstance().toDto(cuentaBancariaOrigen),
                                pagoTarjetaRequest.pago().importe(), pagoTarjetaRequest.pago().concepto());
                cuentaBancariaService.ingresar(CuentaBancariaMapper.getInstance().toDto(cuentaBancariaDestino),
                                pagoTarjetaRequest.pago().importe(), pagoTarjetaRequest.pago().concepto());
        }
}
