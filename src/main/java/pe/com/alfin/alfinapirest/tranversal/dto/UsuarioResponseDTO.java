package pe.com.alfin.alfinapirest.tranversal.dto;


import lombok.Data;

@Data
public class UsuarioResponseDTO {

    private String nombre;
    private String apellidos;
    private String telefono;
    private String celular;
    private String username;
    private String rol;
}
