package br.com.logistica.forcavenda.repositories.impl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.logistica.forcavenda.models.Usuario;

public final class UserDetailsRepository extends Usuario implements UserDetails {

  private static final long serialVersionUID = 1L;

  public UserDetailsRepository(Usuario usuario) {
    super(usuario);
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
    return getHabilitado() == "S";
  }

}
