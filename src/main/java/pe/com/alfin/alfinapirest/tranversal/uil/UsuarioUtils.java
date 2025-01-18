package pe.com.alfin.alfinapirest.tranversal.uil;



import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import pe.com.alfin.alfinapirest.tranversal.config.model.UsuarioDetalles;
import pe.com.alfin.alfinapirest.tranversal.dto.UsuarioResponseDTO;
import pe.com.alfin.alfinapirest.tranversal.model.Usuario;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UsuarioUtils {

    public Mono<String> getAuthenticatedUsuario() {
        return ReactiveSecurityContextHolder.getContext()
                .doOnNext(context -> log.debug("Contexto de seguridad obtenido: {}", context))
                .map(securityContext -> securityContext.getAuthentication())
                .filter(authentication-> authentication.isAuthenticated())
                .map(authentication -> (String) authentication.getPrincipal());


    }
    public Mono<List<String>> getAuthenticatedRoles() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication())
                .filter(authentication -> authentication != null && authentication.isAuthenticated())
                .map(authentication -> authentication.getAuthorities())
                .map(authorities -> authorities.stream()
                        .map(grantedAuthority -> grantedAuthority.getAuthority()) // Obtener solo el nombre del rol
                        .map(rol -> rol.replace("ROLE_", "").toUpperCase()) // Remover prefijo (si existe) y formatear
                        .collect(Collectors.toList()))
                .doOnNext(roles -> log.info("Roles encontrados: {}", roles))
                .switchIfEmpty(Mono.error(new IllegalStateException("No se encontraron roles para el usuario autenticado.")));
    }


    public UsuarioResponseDTO toResponseDTO(Usuario usuario, String rol) {
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setNombre(usuario.getNombre());
        responseDTO.setApellidos(usuario.getApellidos());
        responseDTO.setTelefono(usuario.getTelefono());
        responseDTO.setCelular(usuario.getCelular());
        responseDTO.setUsername(usuario.getCredencial().getUsername());
        responseDTO.setRol(rol);
        return responseDTO;
    }
}
