package pe.com.alfin.alfinapirest.tranversal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("F01_CREDENCIALES")
public class Credencial {

    @Id
    private Long id;
    private Long usuarioId;
    private Long rolId;
    private String username;
    private String password;
}

