package br.com.logistica.forcavenda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logistica.forcavenda.models.Produto;
import br.com.logistica.forcavenda.service.ProdutoService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/produto")
@PreAuthorize("hasRole('USER')")
public class ProdutoController extends AbsController<Produto, String> {

  @Autowired
  public ProdutoController(ProdutoService services) {
    super(services);
  }

  public void teste() {

  }

}
