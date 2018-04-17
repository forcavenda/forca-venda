package br.com.logistica.forcavenda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.logistica.forcavenda.models.Usuario;
import br.com.logistica.forcavenda.repositories.UsuarioRepository;
import br.com.logistica.forcavenda.service.UsuarioService;

@Service
public class UsuarioServiceImpl extends IServiceImpl<Usuario, String> implements UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  @SuppressWarnings("unchecked")
  public Usuario insert(Usuario usuario) {
    BCryptPasswordEncoder codificarSenha = new BCryptPasswordEncoder();
    usuario.setSenha(codificarSenha.encode(usuario.getSenha()));
    return getRepository().insert(usuario);
  }

  @Override
  public Usuario getByNomeUsuario(String nomeUsuario) {
    return usuarioRepository.getByNomeUsuario(nomeUsuario);
  }
}
