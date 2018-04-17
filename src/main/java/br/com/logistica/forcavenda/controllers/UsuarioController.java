package br.com.logistica.forcavenda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logistica.forcavenda.models.Usuario;
import br.com.logistica.forcavenda.service.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/usuario")
public class UsuarioController extends AbsController<Usuario, String> {

  @Autowired
  public UsuarioController(UsuarioService services) {
    super(services);
  }
}
