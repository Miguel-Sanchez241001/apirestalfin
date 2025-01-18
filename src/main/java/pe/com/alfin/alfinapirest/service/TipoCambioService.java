package pe.com.alfin.alfinapirest.service;

import org.springframework.transaction.annotation.Transactional;
import pe.com.alfin.alfinapirest.tranversal.dto.TransCambioRequestDTO;
import pe.com.alfin.alfinapirest.tranversal.dto.TransCambioResponseDTO;
import pe.com.alfin.alfinapirest.tranversal.model.TipoCambio;
import reactor.core.publisher.Mono;

public interface TipoCambioService {

    Mono<TipoCambio> saveTipoCambio(TipoCambio tipoCambio);

    Mono<TipoCambio> getTipoCambio(String monedaOrigen, String monedaDestino);

    Mono<TransCambioResponseDTO> realizarTipoCambio(TransCambioRequestDTO request);

    Mono<Void> audit(String accion, String descripcion, String username, String rol, TipoCambio tipoCambio, Double montoOriginal, Double montoConvertido, String feCHhORA);
}

