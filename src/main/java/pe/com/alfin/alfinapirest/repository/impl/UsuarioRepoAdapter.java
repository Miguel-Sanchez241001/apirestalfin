package pe.com.alfin.alfinapirest.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import pe.com.alfin.alfinapirest.repository.CredencialRepository;
import pe.com.alfin.alfinapirest.repository.RolRepositor;
import pe.com.alfin.alfinapirest.repository.UserGateway;
import pe.com.alfin.alfinapirest.repository.UsuarioRepository;
import pe.com.alfin.alfinapirest.tranversal.config.jwt.provider.JwtProvider;
import pe.com.alfin.alfinapirest.tranversal.config.model.JwtRequest;
import pe.com.alfin.alfinapirest.tranversal.config.model.JwtResponse;
import pe.com.alfin.alfinapirest.tranversal.config.model.UsuarioDetalles;
import pe.com.alfin.alfinapirest.tranversal.dto.UsuarioRequestDTO;
import pe.com.alfin.alfinapirest.tranversal.dto.UsuarioResponseDTO;
import pe.com.alfin.alfinapirest.tranversal.model.Credencial;
import pe.com.alfin.alfinapirest.tranversal.model.Usuario;
import pe.com.alfin.alfinapirest.tranversal.uil.UsuarioUtils;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UsuarioRepoAdapter implements UserGateway {
    private final UsuarioRepository usuarioRepository;
    private final CredencialRepository credencialRepository;
    private final RolRepositor rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UsuarioUtils usuarioMapper;
    @Override
    public Mono<UsuarioResponseDTO> signUp(UsuarioRequestDTO dto) {
        return rolRepository.findById(dto.getRolId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Rol no encontrado")))
                .flatMap(rol -> {
                    Usuario usuario = new Usuario(null, dto.getNombre(), dto.getApellidos(), dto.getTelefono(), dto.getCelular());
                    return usuarioRepository.save(usuario)
                            .flatMap(usuarioGuardado -> {
                                Credencial credencial = new Credencial(null, usuarioGuardado.getId(), rol.getId(),
                                        dto.getUsername(), passwordEncoder.encode(dto.getPassword()));
                                return credencialRepository.save(credencial)
                                        .map(credencialGuardada -> {
                                            usuarioGuardado.setCredencial(credencialGuardada);
                                            usuarioGuardado.setRol(rol);
                                            return usuarioMapper.toResponseDTO(usuarioGuardado, rol.getNombre());
                                        });
                            });
                })
                .doOnSuccess(response -> log.info("Usuario registrado correctamente: {}", response))
                .doOnError(e -> log.error("Error en el registro del usuario: {}", e.getMessage(), e));
    }



    @Override
    public Mono<JwtResponse> login(JwtRequest dto) {
        return credencialRepository.findByUsername(dto.getUsername())
                .flatMap(credencial -> usuarioRepository.findById(credencial.getUsuarioId())
                        .flatMap(usuario -> rolRepository.findById(credencial.getRolId())
                                .map(rol -> {
                                    // Completa los datos del usuario
                                    usuario.setCredencial(credencial);
                                    usuario.setRol(rol);
                                    return usuario;
                                })
                        )
                )
                .map(UsuarioDetalles::new) // Convierte el usuario a un UserDetails
                .filter(userDetails -> passwordEncoder.matches(dto.getPassword(), userDetails.getPassword())) // Verifica la contraseña
                .map(userDetails -> new JwtResponse(jwtProvider.generateToken(userDetails))) // Genera el token
                .switchIfEmpty(Mono.error(new Throwable("bad credentials"))); // Si no se encuentra o falla, lanza un error
    }

    @Override
    public Mono<UsuarioDetalles> findByUsername(String username) {
        return credencialRepository.findByUsername(username)
                .flatMap(credencial -> usuarioRepository.findById(credencial.getUsuarioId())
                        .flatMap(usuario -> rolRepository.findById(credencial.getRolId())
                                .map(rol -> {
                                    // Completa los datos del usuario
                                    usuario.setCredencial(credencial);
                                    usuario.setRol(rol);
                                    return usuario;
                                })
                        )
                )
                .map(UsuarioDetalles::new);
    }


}
