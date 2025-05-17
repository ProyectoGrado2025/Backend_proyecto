package es.daw2.restaurant_V1.security.config.filters;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import es.daw2.restaurant_V1.services.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        /*
         * Se repite el mismo proceso que en el microservicio de usuarios
         * Ciertos aspectos cambian:
         *  - No se puede validar el token desde la base de datos
         *  - Se valida que el token no venga vacío
         *  - Se valida que el Token esta firmado correctamente
         *  - Se valida la fecha de expiración
         *  - Se valida el status del usuario
         *  - Se extrae el rol del usuario
         */

        // 1. Obtener el header "Authorization"
        // 2. Obtener el Token
        String jwt = jwtService.extractJwtFromRequest(request);
        if(jwt == null || !StringUtils.hasText(jwt)){
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Comprobar que el Token no ha expirado
        if(!isTokenNotExpired(jwtService.extractExpiration(jwt))){
            filterChain.doFilter(request, response);
            return;
        };

        // 4. Si no ha expirado, se comprueba el status del solicitante
        if(!isTokenStatusEnabled(jwtService.extractStatus(jwt))){
            filterChain.doFilter(request, response);
            return;
        };

        String rol = jwtService.extractRole(jwt);
        String email = jwtService.extractEmail(jwt);

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+rol);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            email,
            null,
            List.of(authority)
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private boolean isTokenNotExpired(Date jwtExpiration) {
        Date now = new Date(System.currentTimeMillis());
        return jwtExpiration.after(now);
    }


    private boolean isTokenStatusEnabled(String jwtStatus) {
        return jwtStatus.equalsIgnoreCase("ENABLED");
    }

}
