package pe.com.alfin.alfinapirest.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.alfin.alfinapirest.tranversal.model.Rol;

@Repository
public interface RolRepositor extends ReactiveCrudRepository<Rol, Long> {
}

