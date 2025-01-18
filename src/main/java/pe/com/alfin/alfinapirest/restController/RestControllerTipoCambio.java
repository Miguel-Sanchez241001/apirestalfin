package pe.com.alfin.alfinapirest.restController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.alfin.alfinapirest.service.TipoCambioService;
import pe.com.alfin.alfinapirest.tranversal.dto.TransCambioRequestDTO;
import pe.com.alfin.alfinapirest.tranversal.dto.TransCambioResponseDTO;
import pe.com.alfin.alfinapirest.tranversal.dto.ws.WsResponse;
import pe.com.alfin.alfinapirest.tranversal.model.TipoCambio;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/tipocambio")
@RequiredArgsConstructor
@Slf4j
public class RestControllerTipoCambio {

    private final TipoCambioService service;

 
    @GetMapping()
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public Mono<ResponseEntity<WsResponse<TipoCambio>>> buscarTipoCambio(
            @RequestParam(name = "MO") String monedaOrigen,
            @RequestParam(name = "MD") String monedaDestino) {
        return service.getTipoCambio(monedaOrigen, monedaDestino)
                .map(tipoCambio -> ResponseEntity.ok(
                        new WsResponse<>(tipoCambio, "0000", "Exitoso")
                ))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new WsResponse<>(null, "404", "Tipo de cambio no encontrado")
                ))
                .onErrorResume(e -> {
                    log.error("Error al buscar el tipo de cambio para MO={} y MD={}: {}", monedaOrigen, monedaDestino, e.getMessage(), e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                            new WsResponse<>(null, "9999", "Error interno")
                    ));
                });
    }


    @PostMapping()
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public Mono<ResponseEntity<WsResponse<TipoCambio>>> crearTipoCambio( @RequestBody TipoCambio request) {
        return service.saveTipoCambio(request)
                .map(tipoCambio -> ResponseEntity.status(HttpStatus.CREATED).body(
                        new WsResponse<>(tipoCambio, "0000", "Tipo de cambio creado con éxito")
                ))
                .onErrorResume(e -> {
                    log.error("Error al crear el tipo de cambio: {}", e.getMessage(), e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                            new WsResponse<>(null, "9999", "Error interno")
                    ));
                });
    }


    @PutMapping()
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public Mono<ResponseEntity<WsResponse<TipoCambio>>> modificarTipoCambio(  @RequestBody TipoCambio request) {
        return service.saveTipoCambio(request)
                .map(tipoCambio -> ResponseEntity.ok(
                        new WsResponse<>(tipoCambio, "0000", "Tipo de cambio modificado con éxito")
                ))
                .onErrorResume(e -> {
                    log.error("Error al modificar el tipo de cambio: {}", e.getMessage(), e); // Log del error
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                            new WsResponse<>(null, "9999", "Error interno")
                    ));
                });
    }


    @PostMapping("/doit")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public Mono<ResponseEntity<WsResponse<TransCambioResponseDTO>>> realizarTipoCambio(
            @Valid @RequestBody TransCambioRequestDTO request) {
        return service.realizarTipoCambio(request)
                .map(tipoCambio -> ResponseEntity.ok(
                        new WsResponse<>(tipoCambio, "0000", "Tipo de cambio realizado con éxito")
                ))
                .doOnError(ex -> log.error("Error realizando tipo de cambio", ex))
                .onErrorResume(e -> {
                    log.error("Error al modificar el tipo de cambio: {}", e.getMessage(), e); // Log del error
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                            new WsResponse<>(null, "9999", "Error interno")
                    ));
                });
    }

}


