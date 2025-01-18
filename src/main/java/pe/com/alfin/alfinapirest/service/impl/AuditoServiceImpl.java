package pe.com.alfin.alfinapirest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.alfin.alfinapirest.repository.AuditoriaRepository;
import pe.com.alfin.alfinapirest.service.AuditoService;
import pe.com.alfin.alfinapirest.tranversal.model.Auditoria;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AuditoServiceImpl implements AuditoService {

    private final AuditoriaRepository auditoriaRepository;

    @Override
    public Flux<Auditoria> listarAuditorias() {
        return auditoriaRepository.findAll();
    }
}

