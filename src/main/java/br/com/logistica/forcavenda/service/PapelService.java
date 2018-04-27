package br.com.logistica.forcavenda.service;

import br.com.logistica.forcavenda.models.Papel;

public interface PapelService extends IService<Papel, String> {

  Papel findByNome(String nome);
}
