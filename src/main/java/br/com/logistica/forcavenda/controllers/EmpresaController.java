package br.com.logistica.forcavenda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logistica.forcavenda.models.Empresa;
import br.com.logistica.forcavenda.service.EmpresaService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/empresa")
public class EmpresaController extends AbsController<Empresa, String> {

  @Autowired
  public EmpresaController(EmpresaService services) {
    super(services);
  }

  public void teste() {
    System.out.println("teste");
  }
}
