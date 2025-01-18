package pe.com.alfin.alfinapirest.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.alfin.alfinapirest.tranversal.model.TipoCambio;
import reactor.core.publisher.Mono;

@Repository
public interface TipoCambioRepository extends ReactiveCrudRepository<TipoCambio, Long> {


    Mono<TipoCambio> findByMonedaOrigenAndMonedaDestino(String monedaOrigen, String monedaDestino);


}
