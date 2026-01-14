package cybereats.fpmislata.com.banco_back.domain.service;

import cybereats.fpmislata.com.banco_back.domain.dto.AuthResponseDto;
import cybereats.fpmislata.com.banco_back.domain.dto.LoginRequestDto;
import cybereats.fpmislata.com.banco_back.domain.dto.RegisterRequestDto;
import cybereats.fpmislata.com.banco_back.domain.model.Cliente;
import cybereats.fpmislata.com.banco_back.domain.model.Token;

public interface AuthService {
    AuthResponseDto login(LoginRequestDto loginRequestDto);

    AuthResponseDto register(RegisterRequestDto registerRequestDto);

    Cliente getClienteFromToken(Token token);
}
