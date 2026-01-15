package cybereats.fpmislata.com.banco_back.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.service.PagoTarjeta;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.Autorizacion;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.DatosCuenta;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.Pago;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.PagoTarjetaRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagoTarjetaController.class)
class PagoTarjetaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoTarjeta pagoTarjeta;

    @Autowired
    private ObjectMapper objectMapper;

    private PagoTarjetaRequest request;

    @BeforeEach
    void setUp() {
        Autorizacion autorizacion = new Autorizacion("usuario1", "token123");
        TarjetaCreditoDto origen = new TarjetaCreditoDto(1L, "1234567890123456", LocalDate.of(2028, 12, 31), 123,
                "ISMAEL GARCIA GARCIA");
        DatosCuenta destino = new DatosCuenta("ES1234567890123456789012");
        Pago pago = new Pago(new BigDecimal("10.00"), "Test payment");

        request = new PagoTarjetaRequest(autorizacion, origen, destino, pago);
    }

    @Test
    @DisplayName("Debería procesar un pago con tarjeta exitosamente")
    void shouldProcessPagoTarjeta() throws Exception {
        doNothing().when(pagoTarjeta).pagoTarjeta(any(PagoTarjetaRequest.class));

        mockMvc.perform(post("/api/pagos/tarjeta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
