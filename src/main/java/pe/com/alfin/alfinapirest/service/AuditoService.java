package pe.com.alfin.alfinapirest.service;


import pe.com.alfin.alfinapirest.tranversal.model.Auditoria;
import reactor.core.publisher.Flux;

public interface AuditoService {
    Flux<Auditoria> listarAuditorias();
}
