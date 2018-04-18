package br.com.logistica.forcavenda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logistica.forcavenda.models.Empresa;
import br.com.logistica.forcavenda.repositories.EmpresaRepository;
import br.com.logistica.forcavenda.service.EmpresaService;

@Service
public class EmpresaServiceImpl extends IServiceImpl<Empresa, String> implements EmpresaService {

  @Autowired
  private EmpresaRepository empresaRepository;
}
