package br.com.logistica.forcavenda.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logistica.forcavenda.models.CategoriaProduto;
import br.com.logistica.forcavenda.repositories.CategoriaProdutoRepository;
import br.com.logistica.forcavenda.service.CategoriaProdutoService;

@Service
public class CategoriaProdutoServiceImpl extends IServiceImpl<CategoriaProduto, String>
    implements CategoriaProdutoService {
  @Autowired
  private CategoriaProdutoRepository categoriaProdutoRepository;
}
