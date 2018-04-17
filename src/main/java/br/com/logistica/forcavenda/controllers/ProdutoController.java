package br.com.logistica.forcavenda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logistica.forcavenda.models.Produto;
import br.com.logistica.forcavenda.service.ProdutoService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/produto")
public class ProdutoController extends AbsController<Produto, String> {

  @Autowired
  public ProdutoController(ProdutoService services) {
    super(services);
  }

}
