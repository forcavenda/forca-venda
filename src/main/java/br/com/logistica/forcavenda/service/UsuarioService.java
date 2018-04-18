package br.com.logistica.forcavenda.service;

import br.com.logistica.forcavenda.models.Usuario;

public interface UsuarioService extends IService<Usuario, String> {
  Usuario getByNomeUsuario(String nomeUsuario);
}
