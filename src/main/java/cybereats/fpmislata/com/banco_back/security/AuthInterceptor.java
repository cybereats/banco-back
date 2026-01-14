package cybereats.fpmislata.com.banco_back.security;

import cybereats.fpmislata.com.banco_back.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        AuthRequired authRequired = handlerMethod.getMethodAnnotation(AuthRequired.class);

        if (authRequired == null) {
            authRequired = handlerMethod.getBeanType().getAnnotation(AuthRequired.class);
        }

        if (authRequired == null) {
            return true;
        }

        String token = extractToken(request);
        if (token == null || !JwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Acceso no autorizado: Token invalido o faltante");
            return false;
        }

        // Optional: Add user info to request attributes
        request.setAttribute("userId", JwtUtil.extractUserId(token));
        request.setAttribute("userLogin", JwtUtil.extractLogin(token));

        return true;
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
