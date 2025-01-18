package pe.com.alfin.alfinapirest.tranversal.config.jwt.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pe.com.alfin.alfinapirest.tranversal.config.model.UsuarioDetalles;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.days}")
    private Integer days;

    @Value("${jwt.expiration.hours}")
    private Integer hours;

    @Value("${jwt.expiration.minutes}")
    private Integer minutes;

    @Value("${jwt.expiration.seconds}")
    private Integer seconds;

    public String generateToken(UsuarioDetalles userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .issuedAt(new Date())
                .expiration(calculateExpirationDate())
                .signWith(getKey(secret))
                .compact();
    }
    private Date calculateExpirationDate() {
        long expirationInMillis = (days * 24L * 60 * 60 * 1000) + // Días
                (hours * 60L * 60 * 1000) +     // Horas
                (minutes * 60L * 1000) +        // Minutos
                (seconds * 1000L);              // Segundos
        return new Date(System.currentTimeMillis() + expirationInMillis);
    }
    public Claims getClaims(String token) throws Exception {
        try {
        return Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (ExpiredJwtException e) {

            log.error("token expired");
            throw new Exception(e);
        } catch (UnsupportedJwtException e) {

            log.error("token unsupported");
            throw new Exception(e);
        } catch (MalformedJwtException e) {

            log.error("token malformed");
            throw new Exception(e);
        } catch (SignatureException e) {
            log.error("bad signature");
            throw new Exception(e);
        } catch (IllegalArgumentException e) {
            log.error("illegal args");
            throw new Exception(e);
        }
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }



    private SecretKey getKey(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}