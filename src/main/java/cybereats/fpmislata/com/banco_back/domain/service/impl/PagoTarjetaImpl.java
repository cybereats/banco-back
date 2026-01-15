package cybereats.fpmislata.com.banco_back.domain.service.impl;

import cybereats.fpmislata.com.banco_back.domain.mapper.CuentaBancariaMapper;
import cybereats.fpmislata.com.banco_back.domain.model.CuentaBancaria;
import cybereats.fpmislata.com.banco_back.domain.service.PagoTarjeta;
import cybereats.fpmislata.com.banco_back.domain.service.TarjetaCreditoService;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.PagoTarjetaRequest;
import cybereats.fpmislata.com.banco_back.domain.service.ClienteService;
import cybereats.fpmislata.com.banco_back.domain.service.CuentaBancariaService;
import cybereats.fpmislata.com.banco_back.exception.BusinessException;

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

                // Validamos la tarjeta primero para confirmar que existe y los datos son
                // correctos
                tarjetaCreditoService.validate(pagoTarjetaRequest.origen());

                CuentaBancaria cuentaBancariaOrigen = CuentaBancariaMapper.getInstance()
                                .toModel(cuentaBancariaService.findByTarjetaCredito(pagoTarjetaRequest.origen()));

                if (cuentaBancariaOrigen == null) {
                        throw new BusinessException("No se encontró la cuenta asociada a la tarjeta: "
                                        + pagoTarjetaRequest.origen().numeroTarjeta());
                }

                CuentaBancaria cuentaBancariaDestino = CuentaBancariaMapper.getInstance()
                                .toModel(cuentaBancariaService.findByIban(pagoTarjetaRequest.destino().iban()));

                if (cuentaBancariaDestino == null) {
                        throw new BusinessException("No se encontró la cuenta de destino con IBAN: "
                                        + pagoTarjetaRequest.destino().iban());
                }

                clienteService.validate(cuentaBancariaOrigen.getCliente().getLogin(),
                                cuentaBancariaOrigen.getCliente().getApiToken());

                cuentaBancariaService.retirar(CuentaBancariaMapper.getInstance().toDto(cuentaBancariaOrigen),
                                pagoTarjetaRequest.origen(), pagoTarjetaRequest.pago().importe(),
                                pagoTarjetaRequest.pago().concepto());
                cuentaBancariaService.ingresar(CuentaBancariaMapper.getInstance().toDto(cuentaBancariaDestino),
                                pagoTarjetaRequest.pago().importe(), pagoTarjetaRequest.pago().concepto());
        }
}
