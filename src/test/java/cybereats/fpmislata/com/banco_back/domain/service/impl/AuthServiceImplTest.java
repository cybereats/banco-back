package cybereats.fpmislata.com.banco_back.domain.service.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.AuthResponseDto;
import cybereats.fpmislata.com.banco_back.domain.dto.LoginRequestDto;
import cybereats.fpmislata.com.banco_back.domain.dto.RegisterRequestDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import cybereats.fpmislata.com.banco_back.domain.model.Token;
import cybereats.fpmislata.com.banco_back.domain.repository.AuthRepository;
import cybereats.fpmislata.com.banco_back.exception.BusinessException;
import cybereats.fpmislata.com.banco_back.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setLogin("jdoe");
        cliente.setPassword("encoded_pass");
        cliente.setNombre("John");
    }

    @Nested
    @DisplayName("Tests para el método login")
    class LoginTests {
        @Test
        @DisplayName("Debería iniciar sesión correctamente")
        void shouldLoginSuccessfully() {
            LoginRequestDto request = new LoginRequestDto("jdoe", "password");
            when(authRepository.findByLogin("jdoe")).thenReturn(Optional.of(cliente));
            when(passwordEncoder.matches("password", "encoded_pass")).thenReturn(true);

            LocalDateTime expiration = LocalDateTime.now().plusHours(1);
            try (MockedStatic<JwtUtil> mockedJwt = mockStatic(JwtUtil.class)) {
                mockedJwt.when(() -> JwtUtil.generateToken(cliente)).thenReturn("mocked_token");
                mockedJwt.when(() -> JwtUtil.extractExpirationDate("mocked_token")).thenReturn(expiration);

                AuthResponseDto response = authService.login(request);

                assertAll(
                        () -> assertNotNull(response),
                        () -> assertEquals("mocked_token", response.token()),
                        () -> assertEquals(expiration, response.expiration()));
                verify(authRepository).findByLogin("jdoe");
            }
        }

        @Test
        @DisplayName("Debería lanzar error si el usuario no existe")
        void shouldThrowWhenUserNotFound() {
            LoginRequestDto request = new LoginRequestDto("unknown", "password");
            when(authRepository.findByLogin("unknown")).thenReturn(Optional.empty());

            assertThrows(BusinessException.class, () -> authService.login(request));
        }

        @Test
        @DisplayName("Debería lanzar error si la contraseña es incorrecta")
        void shouldThrowWhenWrongPassword() {
            LoginRequestDto request = new LoginRequestDto("jdoe", "wrong");
            when(authRepository.findByLogin("jdoe")).thenReturn(Optional.of(cliente));
            when(passwordEncoder.matches("wrong", "encoded_pass")).thenReturn(false);

            assertThrows(BusinessException.class, () -> authService.login(request));
        }
    }

    @Nested
    @DisplayName("Tests para el método register")
    class RegisterTests {
        @Test
        @DisplayName("Debería registrar un cliente nuevo")
        void shouldRegisterSuccessfully() {
            RegisterRequestDto request = new RegisterRequestDto("newuser", "pass", "Name", "A1", "A2", "12345678A");
            when(authRepository.findByLogin("newuser")).thenReturn(Optional.empty()).thenReturn(Optional.of(cliente));
            when(passwordEncoder.encode("pass")).thenReturn("encoded_pass");

            try (MockedStatic<JwtUtil> mockedJwt = mockStatic(JwtUtil.class)) {
                mockedJwt.when(() -> JwtUtil.generateToken(any())).thenReturn("token");
                mockedJwt.when(() -> JwtUtil.extractExpirationDate(any())).thenReturn(null);

                AuthResponseDto response = authService.register(request);

                assertNotNull(response);
                verify(authRepository).save(any(Cliente.class));
            }
        }

        @Test
        @DisplayName("Debería lanzar error si el usuario ya existe")
        void shouldThrowWhenUserAlreadyExists() {
            RegisterRequestDto request = new RegisterRequestDto("jdoe", "pass", "Name", "A1", "A2", "12345678A");
            when(authRepository.findByLogin("jdoe")).thenReturn(Optional.of(cliente));

            assertThrows(BusinessException.class, () -> authService.register(request));
        }
    }

    @Nested
    @DisplayName("Tests para getClienteFromToken")
    class GetClienteTokenTests {
        @Test
        @DisplayName("Debería obtener el cliente desde un token válido")
        void shouldGetClienteFromToken() {
            Token token = new Token("valid_token", LocalDateTime.now().plusHours(1));
            try (MockedStatic<JwtUtil> mockedJwt = mockStatic(JwtUtil.class)) {
                mockedJwt.when(() -> JwtUtil.validateToken("valid_token")).thenReturn(true);
                mockedJwt.when(() -> JwtUtil.extractLogin("valid_token")).thenReturn("jdoe");
                when(authRepository.findByLogin("jdoe")).thenReturn(Optional.of(cliente));

                Cliente result = authService.getClienteFromToken(token);

                assertAll(
                        () -> assertNotNull(result),
                        () -> assertEquals("jdoe", result.getLogin()));
            }
        }
    }
}
