package AgentManagement.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkeymysecretkey";

    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 1000 * 60 * 60 * 24))
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {

        Claims claims =
                Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token,
                                 String email) {

        return extractEmail(token).equals(email);
    }
}