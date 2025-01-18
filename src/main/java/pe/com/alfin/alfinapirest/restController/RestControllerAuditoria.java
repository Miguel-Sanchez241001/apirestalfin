package pe.com.alfin.alfinapirest.restController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.alfin.alfinapirest.service.AuditoService;
import pe.com.alfin.alfinapirest.tranversal.dto.ws.WsResponse;
import pe.com.alfin.alfinapirest.tranversal.model.Auditoria;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
@Slf4j
public class RestControllerAuditoria {

    private final AuditoService auditoService;

    @GetMapping
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Flux<WsResponse<Auditoria>>> listarAuditorias() {
        Flux<WsResponse<Auditoria>> response = auditoService.listarAuditorias()
                .map(auditoria -> new WsResponse<>(auditoria, "0000", "Consulta exitosa"));

        return ResponseEntity.ok(response);
    }
}
