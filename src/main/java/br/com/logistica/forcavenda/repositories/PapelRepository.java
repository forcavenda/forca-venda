package br.com.logistica.forcavenda.repositories;

import br.com.logistica.forcavenda.models.Papel;

public interface PapelRepository extends IRepository<Papel, String> {

  Papel findByNome(String nome);

}
