package es.daw2.restaurant_V1.services.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;
    
    private SecretKey generateKey() {

        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);

        System.out.println(new String(passwordDecoded) );
        return Keys.hmacShaKeyFor(passwordDecoded);
    }
    
    public String extractEmail(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts
                .parser()
                .verifyWith( generateKey() )
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        // a. obtener el encabezado Http llamado "Authorization"
        String authHeader = request.getHeader("Authorization");
        if(!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")){
            return null;
        }

        // b. obtener el Token JWT desde el encabezado
        return authHeader.split(" ")[1];
        
    }

    public Date extractExpiration(String jwt) {
        return extractAllClaims(jwt).getExpiration();
    }

    public String extractStatus(String jwt){
        return extractAllClaims(jwt).get("status", String.class);
    }

    public String extractRole(String jwt){
        return extractAllClaims(jwt).get("role", String.class);
    }
}
