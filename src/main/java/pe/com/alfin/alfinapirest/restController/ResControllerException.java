package pe.com.alfin.alfinapirest.restController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ResControllerException {

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException: {}", ex.getMessage());

        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(createErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Validation error: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Validation failed");
        response.put("errors", errors);

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @ExceptionHandler(Throwable.class)
    public Mono<ResponseEntity<Object>> handleGenericException(Throwable ex) {
        log.error("Unhandled exception occurred", ex);
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage())));
    }

    private ErrorResponse createErrorResponse(int statusCode, String message) {
        return new ErrorResponse(statusCode, message);
    }

    private record ErrorResponse(int statusCode, String message) {
    }
}
