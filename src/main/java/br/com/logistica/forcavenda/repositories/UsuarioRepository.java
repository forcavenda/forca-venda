package br.com.logistica.forcavenda.repositories;

import br.com.logistica.forcavenda.models.Usuario;

public interface UsuarioRepository extends IRepository<Usuario, String> {
  Usuario getByNomeUsuario(String nomeUsuario);
}
