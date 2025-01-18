package pe.com.alfin.alfinapirest.restController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import pe.com.alfin.alfinapirest.repository.UserGateway;
import pe.com.alfin.alfinapirest.tranversal.config.model.JwtRequest;
import pe.com.alfin.alfinapirest.tranversal.config.model.JwtResponse;
import pe.com.alfin.alfinapirest.tranversal.dto.UsuarioRequestDTO;
import pe.com.alfin.alfinapirest.tranversal.dto.UsuarioResponseDTO;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class RestControllerAuth {

    private final UserGateway userGateway;

    @PostMapping("/signUp")
    public Mono<ResponseEntity<UsuarioResponseDTO>> signUp(@RequestBody UsuarioRequestDTO req) {
        return userGateway.signUp(req)
                .map(ResponseEntity::ok)
                .doOnSuccess(response -> log.info("Registro exitoso: {}", response))
                .doOnError(e -> log.error("Error durante el registro: {}", e.getMessage(), e));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<JwtResponse>> login(@RequestBody JwtRequest req) {
        return userGateway.login(req)
                .map(ResponseEntity::ok)
                .doOnSuccess(response -> log.info("Inicio de sesión exitoso para el usuario: {}", req.getUsername()))
                .doOnError(e -> log.error("Error durante el inicio de sesión: {}", e.getMessage(), e));
    }
}
