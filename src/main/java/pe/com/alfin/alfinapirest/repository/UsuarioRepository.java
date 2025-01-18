package pe.com.alfin.alfinapirest.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.alfin.alfinapirest.tranversal.model.Usuario;
import reactor.core.publisher.Flux;

@Repository
public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, Long> {

    /**
     * Busca usuarios por nombre.
     *
     * @param nombre Nombre del usuario.
     * @return Un flujo de usuarios con el nombre especificado.
     */
    Flux<Usuario> findByNombre(String nombre);
}
