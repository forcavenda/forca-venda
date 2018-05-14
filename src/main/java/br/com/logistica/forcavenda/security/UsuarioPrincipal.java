package br.com.logistica.forcavenda.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.logistica.forcavenda.models.Usuario;

public final class UsuarioPrincipal extends Usuario implements UserDetails {

  private static final long serialVersionUID = 1L;

  public UsuarioPrincipal(Usuario usuario) {
    super(usuario);
  }

  public Usuario getUsuario() {
    return this;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getPapeis();
  }

  @Override
  public String getUsername() {
    return getNomeUsuario();
  }

  @Override
  public String getPassword() {
    return getSenha();
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
    return getHabilitado().equals("S");
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (id == null ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    UsuarioPrincipal other = (UsuarioPrincipal) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

}
