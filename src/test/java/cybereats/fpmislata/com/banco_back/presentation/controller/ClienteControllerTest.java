package cybereats.fpmislata.com.banco_back.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cybereats.fpmislata.com.banco_back.domain.dto.ClienteDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import cybereats.fpmislata.com.banco_back.domain.model.Page;
import cybereats.fpmislata.com.banco_back.domain.service.ClienteService;
import cybereats.fpmislata.com.banco_back.exception.ResourceNotFoundException;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.ClienteRequest;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClienteDto clienteDto;
    private ClienteRequest clienteRequest;
    private String token;

    @BeforeEach
    void setUp() {
        clienteDto = new ClienteDto(1L, "ismael", "password", "Ismael", "Apellido", "Otro", "12345678X", "api-token");
        clienteRequest = new ClienteRequest(1L, "ismael", "password", "Ismael", "Apellido", "Otro", "12345678X",
                "api-token");

        Cliente adminClient = new Cliente(1L, "admin", "pass", "Admin", "Adm", null, "11111111H", null);
        token = JwtUtil.generateToken(adminClient);
    }

    @Nested
    @DisplayName("Tests para el método createCliente")
    class CreateClienteTests {

        @Test
        @DisplayName("Debería devolver 201 cuando el cliente es válido")
        void shouldReturnCreatedWhenValid() throws Exception {
            when(clienteService.create(any(ClienteDto.class))).thenReturn(clienteDto);

            mockMvc.perform(post("/api/clientes")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(clienteRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.login").value("ismael"));
        }
    }

    @Nested
    @DisplayName("Tests para el método getClienteById")
    class GetClienteByIdTests {

        @Test
        @DisplayName("Debería devolver 200 cuando el ID existe")
        void shouldReturnOkWhenExists() throws Exception {
            when(clienteService.findById(1L)).thenReturn(clienteDto);

            mockMvc.perform(get("/api/clientes/1")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.login").value("ismael"));
        }

        @Test
        @DisplayName("Debería devolver 404 cuando el ID no existe")
        void shouldReturnNotFoundWhenDoesNotExist() throws Exception {
            when(clienteService.findById(1L)).thenThrow(new ResourceNotFoundException("Cliente not found"));

            mockMvc.perform(get("/api/clientes/1")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Tests para el método getAllClientes")
    class GetAllClientesTests {

        @Test
        @DisplayName("Debería devolver 200 y la página de clientes")
        void shouldReturnOkAndPage() throws Exception {
            Page<ClienteDto> page = new Page<>(List.of(clienteDto), 1, 10, 1L);
            when(clienteService.findAll()).thenReturn(page);

            mockMvc.perform(get("/api/clientes")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data[0].id").value(1L))
                    .andExpect(jsonPath("$.totalElements").value(1));
        }
    }

    @Nested
    @DisplayName("Tests para el método updateCliente")
    class UpdateClienteTests {

        @Test
        @DisplayName("Debería devolver 200 cuando la petición es válida")
        void shouldReturnOkWhenValid() throws Exception {
            when(clienteService.update(any(ClienteDto.class))).thenReturn(clienteDto);

            mockMvc.perform(put("/api/clientes/1")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(clienteRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L));
        }

        @Test
        @DisplayName("Debería devolver 400 cuando los IDs no coinciden")
        void shouldReturnBadRequestWhenIdMismatch() throws Exception {
            ClienteRequest mismatchRequest = new ClienteRequest(2L, "ismael", "password", "Ismael", "Apellido", "Otro",
                    "12345678X", "api-token");

            mockMvc.perform(put("/api/clientes/1")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(mismatchRequest)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Tests para el método deleteCliente")
    class DeleteClienteTests {

        @Test
        @DisplayName("Debería devolver 204 cuando el borrado es exitoso")
        void shouldReturnNoContent() throws Exception {
            doNothing().when(clienteService).delete(1L);

            mockMvc.perform(delete("/api/clientes/1")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isNoContent());
        }
    }
}
