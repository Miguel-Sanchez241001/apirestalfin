package pe.com.alfin.alfinapirest.tranversal.config.jwt.manager;


 import io.jsonwebtoken.Claims;
 import lombok.RequiredArgsConstructor;
 import lombok.extern.slf4j.Slf4j;
 import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
 import pe.com.alfin.alfinapirest.repository.UserGateway;
 import pe.com.alfin.alfinapirest.tranversal.config.jwt.provider.JwtProvider;
 import pe.com.alfin.alfinapirest.tranversal.config.model.UsuarioDetalles;
 import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        try {
            Claims claims = jwtProvider.getClaims(token);
            String username = claims.getSubject();


            List<SimpleGrantedAuthority> authorities = claims.get("roles", List.class).stream()
                    .map(role -> new SimpleGrantedAuthority(role.toString()))
                    .toList();

            return Mono.just(new UsernamePasswordAuthenticationToken(username, null, authorities));
        } catch (Exception e) {
            return Mono.error(new Throwable("Invalid token", e));
        }
    }
}
