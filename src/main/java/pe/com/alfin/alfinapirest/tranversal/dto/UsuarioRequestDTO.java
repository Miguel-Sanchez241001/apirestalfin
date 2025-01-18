package pe.com.alfin.alfinapirest.tranversal.dto;

import lombok.Data;

@Data
public class UsuarioRequestDTO {
     private String nombre;
    private String apellidos;
    private String telefono;
    private String celular;
    private String username;
    private String password;
    private Long rolId;
}
