package pe.com.alfin.alfinapirest.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.com.alfin.alfinapirest.tranversal.model.Credencial;
import reactor.core.publisher.Mono;

public interface CredencialRepository extends ReactiveCrudRepository<Credencial, Long> {

    /**
     * Busca las credenciales por el username.
     *
     * @param username El email o username del usuario.
     * @return Un Mono que contiene las credenciales encontradas o vacío si no existen.
     */
    Mono<Credencial> findByUsername(String username);
}
