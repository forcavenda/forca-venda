package br.com.logistica.forcavenda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logistica.forcavenda.models.Regiao;
import br.com.logistica.forcavenda.service.RegiaoService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/regiao")
public class RegiaoController extends AbsController<Regiao, String> {

  @Autowired
  public RegiaoController(RegiaoService services) {
    super(services);
  }

}
