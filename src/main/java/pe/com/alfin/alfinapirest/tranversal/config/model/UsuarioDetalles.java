package pe.com.alfin.alfinapirest.tranversal.config.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pe.com.alfin.alfinapirest.tranversal.model.Usuario;

import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UsuarioDetalles implements UserDetails {

    private final Usuario usuario;

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().getNombre()));
    }

    @Override
    public String getPassword() {
        return usuario.getCredencial().getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getCredencial().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
