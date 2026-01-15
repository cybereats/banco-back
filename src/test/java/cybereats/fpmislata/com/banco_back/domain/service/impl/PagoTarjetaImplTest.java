package cybereats.fpmislata.com.banco_back.domain.service.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.service.ClienteService;
import cybereats.fpmislata.com.banco_back.domain.service.CuentaBancariaService;
import cybereats.fpmislata.com.banco_back.domain.service.TarjetaCreditoService;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.Autorizacion;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.DatosCuenta;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.Pago;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.PagoTarjetaRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoTarjetaImplTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private CuentaBancariaService cuentaBancariaService;

    @Mock
    private TarjetaCreditoService tarjetaCreditoService;

    @InjectMocks
    private PagoTarjetaImpl pagoTarjetaImpl;

    private PagoTarjetaRequest request;
    private TarjetaCreditoDto tarjetaOrigen;
    private CuentaBancariaDto cuentaOrigenDto;
    private CuentaBancariaDto cuentaDestinoDto;

    @BeforeEach
    void setUp() {
        ClienteDto clienteDto = new ClienteDto(1L, "jdoe", "pass", "John", "Doe", null, "12345678A", "token");
        cuentaOrigenDto = new CuentaBancariaDto(1L, new BigDecimal("100"), "IBAN_OR", clienteDto, null, null);
        cuentaDestinoDto = new CuentaBancariaDto(2L, new BigDecimal("50"), "IBAN_DE", clienteDto, null, null);
        tarjetaOrigen = new TarjetaCreditoDto(1L, "1234567890123456", LocalDate.of(2026, 12, 1), 123, "John");

        Autorizacion autorizacion = new Autorizacion("jdoe", "token");
        DatosCuenta destino = new DatosCuenta("IBAN_DE");
        Pago pago = new Pago(new BigDecimal("10.00"), "Test payment");

        request = new PagoTarjetaRequest(autorizacion, tarjetaOrigen, destino, pago);
    }

    @Test
    @DisplayName("Debería procesar el pago correctamente coordinando los servicios")
    void shouldProcessPayment() {
        // GIVEN
        when(cuentaBancariaService.findByTarjetaCredito(request.origen())).thenReturn(cuentaOrigenDto);
        when(cuentaBancariaService.findByIban(request.destino().iban())).thenReturn(cuentaDestinoDto);

        // WHEN
        pagoTarjetaImpl.pagoTarjeta(request);

        // THEN
        assertAll(
                () -> verify(clienteService).validate("jdoe", "token"),
                () -> verify(tarjetaCreditoService).validate(tarjetaOrigen),
                () -> verify(cuentaBancariaService).retirar(any(CuentaBancariaDto.class), eq(tarjetaOrigen),
                        eq(new BigDecimal("10.00")), eq("Test payment")),
                () -> verify(cuentaBancariaService).ingresar(any(CuentaBancariaDto.class), eq(new BigDecimal("10.00")),
                        eq("Test payment")));
    }
}
