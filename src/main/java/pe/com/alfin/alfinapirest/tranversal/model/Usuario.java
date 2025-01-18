package pe.com.alfin.alfinapirest.tranversal.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("F01_USUARIOS")
public class Usuario {

    @Id
    private Long id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String celular;

    private transient Rol rol;

    private transient Credencial credencial;

    public Usuario(Long id, String nombre, String apellidos, String telefono, String celular) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.celular = celular;
    }
}

