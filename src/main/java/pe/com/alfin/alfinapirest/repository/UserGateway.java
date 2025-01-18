package pe.com.alfin.alfinapirest.repository;

import pe.com.alfin.alfinapirest.tranversal.config.model.JwtRequest;
import pe.com.alfin.alfinapirest.tranversal.config.model.JwtResponse;
import pe.com.alfin.alfinapirest.tranversal.config.model.UsuarioDetalles;
import pe.com.alfin.alfinapirest.tranversal.dto.UsuarioRequestDTO;
import pe.com.alfin.alfinapirest.tranversal.dto.UsuarioResponseDTO;
import reactor.core.publisher.Mono;

public interface UserGateway {

        Mono<UsuarioResponseDTO> signUp(UsuarioRequestDTO dto);
        Mono<JwtResponse> login(JwtRequest dto);

        Mono<UsuarioDetalles> findByUsername(String username);
}
