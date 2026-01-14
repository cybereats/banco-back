package cybereats.fpmislata.com.banco_back.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cybereats.fpmislata.com.banco_back.domain.dto.AuthResponseDto;
import cybereats.fpmislata.com.banco_back.domain.dto.LoginRequestDto;
import cybereats.fpmislata.com.banco_back.domain.dto.RegisterRequestDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import cybereats.fpmislata.com.banco_back.domain.model.Token;
import cybereats.fpmislata.com.banco_back.domain.service.AuthService;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.AuthRequest;
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

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;
    private AuthResponseDto authResponseDto;
    private String token;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "ismael", "password", "Ismael", "Apellido", "Otro", "12345678X", null);
        authResponseDto = new AuthResponseDto("fake-token", LocalDateTime.now().plusDays(1), 1L, "ismael", "Ismael");
        token = JwtUtil.generateToken(cliente);
    }

    @Nested
    @DisplayName("Tests para el método login")
    class LoginTests {

        @Test
        @DisplayName("Debería devolver 200 y el token cuando el login es exitoso")
        void shouldReturnOkAndToken() throws Exception {
            AuthRequest authRequest = new AuthRequest("ismael", "password");
            when(authService.login(any(LoginRequestDto.class))).thenReturn(authResponseDto);

            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").value("fake-token"))
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.login").value("ismael"));
        }

        @Test
        @DisplayName("Debería devolver 400 cuando el login está vacío")
        void shouldReturnBadRequestWhenLoginEmpty() throws Exception {
            AuthRequest authRequest = new AuthRequest("", "password");

            mockMvc.perform(post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authRequest)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Tests para el método register")
    class RegisterTests {

        @Test
        @DisplayName("Debería devolver 201 y el token cuando el registro es exitoso")
        void shouldReturnCreatedAndToken() throws Exception {
            RegisterRequestDto registerRequest = new RegisterRequestDto("ismael", "password", "Ismael", "Apellido",
                    "Otro", "12345678X");
            when(authService.register(any(RegisterRequestDto.class))).thenReturn(authResponseDto);

            mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(registerRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.token").value("fake-token"));
        }
    }

    @Nested
    @DisplayName("Tests para el método verify")
    class VerifyTests {

        @Test
        @DisplayName("Debería devolver 200 y el perfil cuando el token es válido")
        void shouldReturnOkAndProfileWhenTokenValid() throws Exception {
            when(authService.getClienteFromToken(any(Token.class))).thenReturn(cliente);

            mockMvc.perform(get("/api/auth/verify")
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.login").value("ismael"))
                    .andExpect(jsonPath("$.nombre").value("Ismael"));
        }

        @Test
        @DisplayName("Debería devolver 401 cuando no hay token")
        void shouldReturnUnauthorizedWhenNoToken() throws Exception {
            mockMvc.perform(get("/api/auth/verify"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("Debería devolver 401 cuando el token es inválido")
        void shouldReturnUnauthorizedWhenTokenInvalid() throws Exception {
            mockMvc.perform(get("/api/auth/verify")
                    .header("Authorization", "Invalid-Token"))
                    .andExpect(status().isUnauthorized());
        }
    }
}
