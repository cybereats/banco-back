package cybereats.fpmislata.com.banco_back.presentation.controller;

import cybereats.fpmislata.com.banco_back.domain.dto.AuthResponseDto;
import cybereats.fpmislata.com.banco_back.domain.dto.ClienteProfileResponseDto;
import cybereats.fpmislata.com.banco_back.domain.dto.LoginRequestDto;
import cybereats.fpmislata.com.banco_back.domain.dto.RegisterRequestDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import cybereats.fpmislata.com.banco_back.domain.model.Token;
import cybereats.fpmislata.com.banco_back.domain.service.AuthService;
import cybereats.fpmislata.com.banco_back.domain.validation.DtoValidator;
import cybereats.fpmislata.com.banco_back.presentation.mapper.AuthMapper;
import cybereats.fpmislata.com.banco_back.presentation.webModel.request.AuthRequest;
import cybereats.fpmislata.com.banco_back.security.AuthRequired;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequest authRequest) {
        DtoValidator.validate(authRequest);
        AuthResponseDto response = authService.login(new LoginRequestDto(authRequest.login(), authRequest.password()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        DtoValidator.validate(registerRequestDto);
        AuthResponseDto response = authService.register(registerRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @AuthRequired
    @GetMapping("/verify")
    public ResponseEntity<ClienteProfileResponseDto> verify(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String jwt = authHeader.substring(7);
        Cliente cliente = authService.getClienteFromToken(new Token(jwt, null));
        ClienteProfileResponseDto profile = AuthMapper.getInstance().toProfileResponse(cliente);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
