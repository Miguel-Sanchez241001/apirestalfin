package pe.com.alfin.alfinapirest.tranversal.config.jwt.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Component
@Slf4j
public class JwtFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getPath().value();


        if (Stream.of("/swagger-ui", "/v3/api-docs","/auth", "/swagger-resources").anyMatch(url::contains)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.error(new IllegalArgumentException("Token ausente o mal formado"));
        }

        String token = authHeader.substring(7);

        exchange.getAttributes().put("token", token);

        return chain.filter(exchange)
                .doOnError(error -> log.error("Error procesando la solicitud para la URL: {}: {}", url, error.getMessage()));
    }
}
