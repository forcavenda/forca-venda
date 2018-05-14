package br.com.logistica.forcavenda.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.logistica.forcavenda.models.Usuario;
import br.com.logistica.forcavenda.payload.ApiResponse;
import br.com.logistica.forcavenda.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/usuario")
@Api(value = "/api/usuario", consumes = "http", produces = "application/json")
public class UsuarioController extends AbsController<Usuario, String> {

  private UsuarioService usuarioService;

  @Autowired
  public UsuarioController(UsuarioService services) {
    super(services);
    usuarioService = services;
  }

  @SuppressWarnings("unchecked")
  @PostMapping("/registrar")
  @ApiOperation(value = "Criando usuario.", notes = "API para registrar um novo usuário.",
      response = ApiResponse.class)
  public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {

    if (usuarioService.existsByNomeUsuario(usuario.getNomeUsuario())) {
      return new ResponseEntity(new ApiResponse(false, "O nome de usuário já está sendo usado!"),
        HttpStatus.BAD_REQUEST);
    }

    if (usuarioService.existsByEmail(usuario.getEmail())) {
      return new ResponseEntity(new ApiResponse(false, "Endereço de email já está em uso!"),
        HttpStatus.BAD_REQUEST);
    }

    Usuario result = usuarioService.insert(usuario);

    URI location = ServletUriComponentsBuilder
      .fromCurrentContextPath().path("/api/usuario/{username}")
      .buildAndExpand(result.getNomeUsuario()).toUri();

    return ResponseEntity.created(location).body(new ApiResponse(true,
      "Usuário cadastrado com sucesso!"));
  }
}
