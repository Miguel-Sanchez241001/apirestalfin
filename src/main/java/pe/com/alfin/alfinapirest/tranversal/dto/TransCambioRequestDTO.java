package pe.com.alfin.alfinapirest.tranversal.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransCambioRequestDTO {

    @NotNull(message = "El monto es obligatorio.")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0.")
    private Double monto;

    @NotBlank(message = "La moneda de origen es obligatoria.")
    private String monedaOrigen;

    @NotBlank(message = "La moneda de destino es obligatoria.")
    private String monedaDestino;

    @NotNull(message = "El tipo de cambio es obligatorio.")
    @DecimalMin(value = "0.01", message = "El tipo de cambio debe ser mayor a 0.")
    private Double tipoCambio;
}
