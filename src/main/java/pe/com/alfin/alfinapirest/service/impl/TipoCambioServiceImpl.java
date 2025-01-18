package pe.com.alfin.alfinapirest.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.alfin.alfinapirest.repository.AuditoriaRepository;
import pe.com.alfin.alfinapirest.repository.TipoCambioRepository;
import pe.com.alfin.alfinapirest.service.TipoCambioService;
import pe.com.alfin.alfinapirest.tranversal.dto.TransCambioRequestDTO;
import pe.com.alfin.alfinapirest.tranversal.dto.TransCambioResponseDTO;
import pe.com.alfin.alfinapirest.tranversal.model.Auditoria;
import pe.com.alfin.alfinapirest.tranversal.model.TipoCambio;
import pe.com.alfin.alfinapirest.tranversal.uil.UsuarioUtils;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoCambioServiceImpl implements TipoCambioService {

    private final TipoCambioRepository repository;
    private final AuditoriaRepository auditoriaRepository;
    private final UsuarioUtils usuarioUtils;

    @Override
    public Mono<TipoCambio> saveTipoCambio(TipoCambio tipoCambio) {
        return repository.save(tipoCambio);
    }

    @Override
    public Mono<TipoCambio> getTipoCambio(String monedaOrigen, String monedaDestino) {
        return repository.findByMonedaOrigenAndMonedaDestino(monedaOrigen, monedaDestino);
    }
    @Override
    @Transactional
    public Mono<TransCambioResponseDTO> realizarTipoCambio(TransCambioRequestDTO request) {
        return usuarioUtils.getAuthenticatedUsuario() // Obtiene el usuario autenticado
                .zipWith(usuarioUtils.getAuthenticatedRoles()) // Obtiene el usuario y los roles
                .flatMap(tuple -> {
                    String usuario = tuple.getT1();
                    List<String> roles = tuple.getT2();

                    String role = roles.isEmpty() ? "SinRol" : roles.get(0);

                    return repository.findByMonedaOrigenAndMonedaDestino(request.getMonedaOrigen(), request.getMonedaDestino())
                            .switchIfEmpty(Mono.error(new IllegalArgumentException("Tipo de cambio no encontrado")))
                            .flatMap(tipoCambio -> {
                                double montoConvertido = request.getMonto() * tipoCambio.getTipoCambio();
                               String fechasHora =  getCurrentFormattedDate();
                                return audit("REALIZAR_CAMBIO",
                                        "Se realizó un cambio de " + request.getMonedaOrigen() + " a " + request.getMonedaDestino(),
                                        usuario, role, tipoCambio, request.getMonto(), montoConvertido,fechasHora)
                                        .thenReturn(new TransCambioResponseDTO(
                                                montoConvertido,
                                                request.getMonedaDestino(),
                                                tipoCambio.getTipoCambio(),
                                                request.getMonto(),
                                                request.getMonedaOrigen(),
                                                fechasHora
                                        ));
                            });
                });
    }



    @Override
    @Transactional
    public Mono<Void> audit(String accion, String descripcion, String username, String rol, TipoCambio tipoCambio, Double montoOriginal, Double montoConvertido,String feCHhORA) {
        Auditoria auditoria = new Auditoria(
                null,
                accion,
                descripcion,
                username,
                rol,
                tipoCambio.getMonedaOrigen(),
                tipoCambio.getMonedaDestino(),
                tipoCambio.getTipoCambio(),
                montoOriginal,
                montoConvertido,
                feCHhORA
        );
        return auditoriaRepository.save(auditoria).then();
    }
    public  String getCurrentFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
