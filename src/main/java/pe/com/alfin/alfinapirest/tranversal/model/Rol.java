package pe.com.alfin.alfinapirest.tranversal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("F01_ROLES")
public class Rol {

    @Id
    private Long id;
    private String nombre;
}
