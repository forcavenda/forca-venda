package br.com.logistica.forcavenda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logistica.forcavenda.exception.AppException;
import br.com.logistica.forcavenda.models.Papel;
import br.com.logistica.forcavenda.repositories.PapelRepository;
import br.com.logistica.forcavenda.service.PapelService;

@Service
public class PapelServiceImpl extends IServiceImpl<Papel, String> implements PapelService {

  @Autowired
  private PapelRepository papelRepository;

  @Override
  public Papel findByNome(String nome) {
    return papelRepository.findByNome(nome).orElseThrow(
      () -> new AppException(String.format("Papel %s não existe!", nome)));
  }
}
