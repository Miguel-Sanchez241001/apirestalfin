package pe.com.alfin.alfinapirest.tranversal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransCambioResponseDTO {
    private Double montoConvertido;
    private String monedaDestino;
    private Double tipoCambio;
    private Double montoOriginal;
    private String monedaOrigen;
    private String fechaRealizacion;


}
