package br.com.logistica.forcavenda.service;

import br.com.logistica.forcavenda.models.Usuario;
import br.com.logistica.forcavenda.payload.LoginRequest;

public interface UsuarioService extends IService<Usuario, String> {
  Usuario getByNomeUsuario(String nomeUsuario);

  String getTokenProvider(LoginRequest usuario);

  boolean existsByNomeUsuario(String nomeUsuario);

  boolean existsByEmail(String email);
}
