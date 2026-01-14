package cybereats.fpmislata.com.banco_back.presentation.webModel.request;

import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;

public record PagoTarjetaRequest(
                Autorizacion autorizacion,
                TarjetaCreditoDto origen,
                DatosCuenta destino,
                Pago pago) {
}
