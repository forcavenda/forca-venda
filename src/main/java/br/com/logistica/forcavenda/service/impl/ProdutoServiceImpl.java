package br.com.logistica.forcavenda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logistica.forcavenda.models.Produto;
import br.com.logistica.forcavenda.repositories.ProdutoRepository;
import br.com.logistica.forcavenda.service.ProdutoService;

@Service
public class ProdutoServiceImpl extends IServiceImpl<Produto, String> implements ProdutoService {

  @Autowired
  private ProdutoRepository produtoRepository;
}
