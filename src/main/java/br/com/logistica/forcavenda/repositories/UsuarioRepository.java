package br.com.logistica.forcavenda.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.logistica.forcavenda.models.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

  Optional<Usuario> getByNomeUsuario(String nomeUsuario);

  Optional<Usuario> findByNomeUsuarioOrEmail(String nomeUsuario, String email);

  Boolean existsByNomeUsuario(String nomeUsuario);

  Boolean existsByEmail(String email);
}
