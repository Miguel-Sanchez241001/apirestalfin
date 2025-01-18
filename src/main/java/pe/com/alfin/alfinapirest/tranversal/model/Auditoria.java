package pe.com.alfin.alfinapirest.tranversal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("F01_AUDITORIA")
public class Auditoria {

    @Id
    @Column("id")
    private Long id;

    @Column("accion")
    private String accion;

    @Column("descripcion")
    private String descripcion;

    @Column("username")
    private String username;

    @Column("rol")
    private String rol;

    @Column("moneda_origen")
    private String monedaOrigen;

    @Column("moneda_destino")
    private String monedaDestino;

    @Column("tipo_cambio")
    private Double tipoCambio;

    @Column("monto_original")
    private Double montoOriginal;

    @Column("monto_convertido")
    private Double montoConvertido;

    @Column("fecha")
    private String fecha;
}
