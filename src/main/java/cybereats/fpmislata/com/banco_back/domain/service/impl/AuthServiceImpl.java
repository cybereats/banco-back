package cybereats.fpmislata.com.banco_back.domain.service.impl;

import cybereats.fpmislata.com.banco_back.domain.dto.AuthResponseDto;
import cybereats.fpmislata.com.banco_back.domain.dto.LoginRequestDto;
import cybereats.fpmislata.com.banco_back.domain.dto.RegisterRequestDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import cybereats.fpmislata.com.banco_back.domain.model.Token;
import cybereats.fpmislata.com.banco_back.domain.repository.AuthRepository;
import cybereats.fpmislata.com.banco_back.domain.service.AuthService;
import cybereats.fpmislata.com.banco_back.exception.BusinessException;
import cybereats.fpmislata.com.banco_back.util.JwtUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        Cliente cliente = authRepository.findByLogin(loginRequestDto.login())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        if (!passwordEncoder.matches(loginRequestDto.password(), cliente.getPassword())) {
            throw new BusinessException("Contraseña incorrecta");
        }

        String token = JwtUtil.generateToken(cliente);
        LocalDateTime expiration = JwtUtil.extractExpirationDate(token);
        return new AuthResponseDto(token, expiration, cliente.getId(), cliente.getLogin(), cliente.getNombre());
    }

    @Override
    public AuthResponseDto register(RegisterRequestDto registerRequestDto) {
        Optional<Cliente> existing = authRepository.findByLogin(registerRequestDto.login());
        if (existing.isPresent()) {
            throw new BusinessException("El nombre de usuario ya existe");
        }

        Cliente cliente = new Cliente();
        cliente.setLogin(registerRequestDto.login());
        cliente.setPassword(passwordEncoder.encode(registerRequestDto.password()));
        cliente.setNombre(registerRequestDto.nombre());
        cliente.setApellido1(registerRequestDto.apellido1());
        cliente.setApellido2(registerRequestDto.apellido2());
        cliente.setDni(registerRequestDto.dni());

        authRepository.save(cliente);

        Cliente savedCliente = authRepository.findByLogin(cliente.getLogin())
                .orElseThrow(() -> new BusinessException("Error al registrar el cliente"));

        String token = JwtUtil.generateToken(savedCliente);
        LocalDateTime expiration = JwtUtil.extractExpirationDate(token);
        return new AuthResponseDto(token, expiration, savedCliente.getId(), savedCliente.getLogin(),
                savedCliente.getNombre());
    }

    @Override
    public Cliente getClienteFromToken(Token token) {
        if (!JwtUtil.validateToken(token.getToken())) {
            throw new BusinessException("El token es invalido o ha expirado");
        }

        String login = JwtUtil.extractLogin(token.getToken());
        return authRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado en el token"));
    }
}
