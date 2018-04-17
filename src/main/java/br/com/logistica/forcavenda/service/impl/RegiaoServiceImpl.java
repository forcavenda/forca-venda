package br.com.logistica.forcavenda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logistica.forcavenda.models.Regiao;
import br.com.logistica.forcavenda.repositories.RegiaoRepository;
import br.com.logistica.forcavenda.service.RegiaoService;

@Service
public class RegiaoServiceImpl extends IServiceImpl<Regiao, String> implements RegiaoService {

  @Autowired
  private RegiaoRepository regiaoRepository;
}
