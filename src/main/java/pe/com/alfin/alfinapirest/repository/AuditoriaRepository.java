package pe.com.alfin.alfinapirest.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.alfin.alfinapirest.tranversal.model.Auditoria;

@Repository
public interface AuditoriaRepository extends ReactiveCrudRepository<Auditoria, Long> {
}
