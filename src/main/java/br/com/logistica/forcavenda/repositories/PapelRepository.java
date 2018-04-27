package br.com.logistica.forcavenda.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.logistica.forcavenda.models.Papel;

@Repository
public interface PapelRepository extends IRepository<Papel, String> {

  Optional<Papel> findByNome(String nome);
}
