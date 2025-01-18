package pe.com.alfin.alfinapirest.tranversal.model;

import lombok.AllArgsConstructor;
 import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Table("F01_TIPO_CAMBIO")
public class TipoCambio {

    @Id
    @Column("id")
    private Long id;

    @Column("moneda_origen")
    private String monedaOrigen;

    @Column("moneda_destino")
    private String monedaDestino;

    @Column("tipo_cambio")
    private Double tipoCambio;
}

