package br.com.logistica.forcavenda.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.logistica.forcavenda.payload.AuthResponse;
import br.com.logistica.forcavenda.payload.LoginRequest;
import br.com.logistica.forcavenda.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@Api(value = "/api/auth", consumes = "http", produces = "application/json")
public class AuthController {

  private UsuarioService usuarioService;

  @Autowired
  public AuthController(UsuarioService usuarioService) {
    super();
    this.usuarioService = usuarioService;
  }

  @ApiOperation(value = "Autenticando o usuario.", notes = "API para autenticar um usuário.",
      response = AuthResponse.class)
  @PostMapping("/auth")
  public ResponseEntity<?> auth(@Valid @RequestBody LoginRequest usuario) {
    String token = usuarioService.getTokenProvider(usuario);
    return ResponseEntity.ok(new AuthResponse(token));
  }
}
