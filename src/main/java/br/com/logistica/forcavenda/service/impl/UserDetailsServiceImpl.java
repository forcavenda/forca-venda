package br.com.logistica.forcavenda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.logistica.forcavenda.models.Usuario;
import br.com.logistica.forcavenda.repositories.UsuarioRepository;
import br.com.logistica.forcavenda.repositories.impl.UserDetailsRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UsuarioRepository usuarioRepository;

  @Autowired
  public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.getByNomeUsuario(nomeUsuario);

    if (usuario == null) {
      throw new UsernameNotFoundException(String.format("Usuário %s não existe!", nomeUsuario));
    }

    return new UserDetailsRepository(usuario);
  }
}
