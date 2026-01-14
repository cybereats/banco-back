package cybereats.fpmislata.com.banco_back.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.dto.TarjetaCreditoDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import cybereats.fpmislata.com.banco_back.domain.service.TarjetaCreditoService;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.TarjetaCreditoRequest;
import cybereats.fpmislata.com.banco_back.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TarjetaCreditoController.class)
class TarjetaCreditoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TarjetaCreditoService tarjetaCreditoService;

    @Autowired
    private ObjectMapper objectMapper;

    private TarjetaCreditoDto tarjetaDto;
    private TarjetaCreditoRequest tarjetaRequest;
    private String token;

    @BeforeEach
    void setUp() {
        tarjetaDto = new TarjetaCreditoDto(1L, "1234567812345678", LocalDate.now().plusYears(1), 123, "ISMAEL TEST");
        tarjetaRequest = new TarjetaCreditoRequest(1L, "1234567812345678", LocalDate.now().plusYears(1), 123,
                "ISMAEL TEST");

        Cliente adminClient = new Cliente(1L, "admin", "pass", "Admin", "Adm", null, "11111111H", null);
        token = JwtUtil.generateToken(adminClient);
    }

    @Nested
    @DisplayName("Tests para el método createTarjeta")
    class CreateTarjetaTests {

        @Test
        @DisplayName("Debería crear una tarjeta bancaria")
        void shouldCreateTarjeta() throws Exception {
            when(tarjetaCreditoService.create(any(TarjetaCreditoDto.class))).thenReturn(tarjetaDto);

            mockMvc.perform(post("/api/tarjetas")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(tarjetaRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.numeroTarjeta").value("1234567812345678"));
        }
    }

    @Nested
    @DisplayName("Tests para el método getTarjetaById")
    class GetTarjetaByIdTests {

        @Test
        @DisplayName("Debería obtener una tarjeta por ID")
        void shouldGetTarjetaById() throws Exception {
            when(tarjetaCreditoService.findById(1L)).thenReturn(tarjetaDto);

            mockMvc.perform(get("/api/tarjetas/1")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L));
        }
    }

    @Nested
    @DisplayName("Tests para el método getTarjetasByCuenta")
    class GetTarjetasByCuentaTests {

        @Test
        @DisplayName("Debería obtener tarjetas por ID de cuenta")
        void shouldGetTarjetasByCuenta() throws Exception {
            when(tarjetaCreditoService.findByCuentaBancaria(any(CuentaBancariaDto.class)))
                    .thenReturn(List.of(tarjetaDto));

            mockMvc.perform(get("/api/tarjetas/cuenta/1")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1L));
        }
    }

    @Nested
    @DisplayName("Tests para el método updateTarjeta")
    class UpdateTarjetaTests {

        @Test
        @DisplayName("Debería actualizar una tarjeta")
        void shouldUpdateTarjeta() throws Exception {
            when(tarjetaCreditoService.update(any(TarjetaCreditoDto.class))).thenReturn(tarjetaDto);

            mockMvc.perform(put("/api/tarjetas/1")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(tarjetaRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L));
        }
    }

    @Nested
    @DisplayName("Tests para el método deleteTarjeta")
    class DeleteTarjetaTests {

        @Test
        @DisplayName("Debería borrar una tarjeta")
        void shouldDeleteTarjeta() throws Exception {
            doNothing().when(tarjetaCreditoService).delete(1L);

            mockMvc.perform(delete("/api/tarjetas/1")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isNoContent());
        }
    }
}
