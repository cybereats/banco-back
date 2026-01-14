package cybereats.fpmislata.com.banco_back.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.dto.CuentaBancariaDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.domain.service.CuentaBancariaService;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.CuentaBancariaRequest;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.Pago;
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

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CuentaBancariaController.class)
class CuentaBancariaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CuentaBancariaService cuentaBancariaService;

    @Autowired
    private ObjectMapper objectMapper;

    private CuentaBancariaDto cuentaDto;
    private CuentaBancariaRequest cuentaRequest;
    private String token;

    @BeforeEach
    void setUp() {
        ClienteDto clientDto = new ClienteDto(1L, "ismael", "pass", "Ismael", "Ap", "Otro", "12345678X", "api");
        cuentaDto = new CuentaBancariaDto(1L, new BigDecimal("1000.00"), "ES1234567890123456789012", clientDto,
                List.of(), List.of());
        cuentaRequest = new CuentaBancariaRequest(1L, new BigDecimal("1000.00"), "ES1234567890123456789012", 1L,
                List.of());

        Cliente adminClient = new Cliente(1L, "admin", "pass", "Admin", "Adm", null, "11111111H", null);
        token = JwtUtil.generateToken(adminClient);
    }

    @Nested
    @DisplayName("Tests para operaciones CRUD")
    class CRUDTests {

        @Test
        @DisplayName("Debería crear una cuenta bancaria")
        void shouldCreateCuenta() throws Exception {
            when(cuentaBancariaService.create(any(CuentaBancariaDto.class))).thenReturn(cuentaDto);

            mockMvc.perform(post("/api/cuentas")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cuentaRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.iban").value("ES1234567890123456789012"));
        }

        @Test
        @DisplayName("Debería obtener una cuenta por ID")
        void shouldGetCuentaById() throws Exception {
            when(cuentaBancariaService.findById(1L)).thenReturn(cuentaDto);

            mockMvc.perform(get("/api/cuentas/1")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L));
        }

        @Test
        @DisplayName("Debería obtener cuentas por cliente")
        void shouldGetCuentasByCliente() throws Exception {
            Page<CuentaBancariaDto> page = new Page<>(List.of(cuentaDto), 1, 10, 1L);
            when(cuentaBancariaService.findByClient(any(ClienteDto.class))).thenReturn(page);

            mockMvc.perform(get("/api/cuentas/cliente/1")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data[0].id").value(1L));
        }

        @Test
        @DisplayName("Debería actualizar una cuenta")
        void shouldUpdateCuenta() throws Exception {
            when(cuentaBancariaService.update(any(CuentaBancariaDto.class))).thenReturn(cuentaDto);

            mockMvc.perform(put("/api/cuentas/1")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cuentaRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L));
        }

        @Test
        @DisplayName("Debería borrar una cuenta")
        void shouldDeleteCuenta() throws Exception {
            doNothing().when(cuentaBancariaService).delete(1L);

            mockMvc.perform(delete("/api/cuentas/1")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("Tests para operaciones bancarias")
    class BankingOperationsTests {

        @Test
        @DisplayName("Debería ingresar dinero en la cuenta")
        void shouldIngresarDinero() throws Exception {
            Pago pago = new Pago(new BigDecimal("100.00"), "Ingreso test");
            when(cuentaBancariaService.findById(1L)).thenReturn(cuentaDto);
            when(cuentaBancariaService.ingresar(any(CuentaBancariaDto.class), eq(new BigDecimal("100.00")),
                    eq("Ingreso test"))).thenReturn(cuentaDto);

            mockMvc.perform(post("/api/cuentas/1/ingresar")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(pago)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L));
        }

        @Test
        @DisplayName("Debería retirar dinero de la cuenta")
        void shouldRetirarDinero() throws Exception {
            Pago pago = new Pago(new BigDecimal("50.00"), "Retirada test");
            when(cuentaBancariaService.findById(1L)).thenReturn(cuentaDto);
            when(cuentaBancariaService.retirar(any(CuentaBancariaDto.class), eq(new BigDecimal("50.00")),
                    eq("Retirada test"))).thenReturn(cuentaDto);

            mockMvc.perform(post("/api/cuentas/1/retirar")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(pago)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L));
        }
    }
}
