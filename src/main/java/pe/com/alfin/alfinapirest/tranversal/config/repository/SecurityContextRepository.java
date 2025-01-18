package pe.com.alfin.alfinapirest.tranversal.config.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import pe.com.alfin.alfinapirest.tranversal.config.jwt.manager.JwtAuthenticationManager;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Component
@Slf4j
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final JwtAuthenticationManager jwtAuthenticationManager;

    public SecurityContextRepository(JwtAuthenticationManager jwtAuthenticationManager) {
        this.jwtAuthenticationManager = jwtAuthenticationManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getAttribute("token");
        if (token == null) {
         return Mono.empty();
        }
        log.debug("Token encontrado en el intercambio: {}", token);
        return jwtAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, token))
                .doOnError(error -> log.error("Error durante la autenticación del token: {}", error.getMessage(), error))
                .map(SecurityContextImpl::new);
    }

}
