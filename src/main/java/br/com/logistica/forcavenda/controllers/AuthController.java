package br.com.logistica.forcavenda.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logistica.forcavenda.exception.AppErrorCode;
import br.com.logistica.forcavenda.exception.AppException;
import br.com.logistica.forcavenda.payload.AuthResponse;
import br.com.logistica.forcavenda.payload.LoginRequest;
import br.com.logistica.forcavenda.security.UsuarioPrincipal;
import br.com.logistica.forcavenda.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
@Api(value = "/api", consumes = "http", produces = "application/json")
public class AuthController {

  private UsuarioService usuarioService;

  @Autowired
  public AuthController(UsuarioService usuarioService) {
    super();
    this.usuarioService = usuarioService;
  }

  @ApiOperation(value = "Autenticando o usuario.", notes = "API para autenticar um usuário.",
      response = AuthResponse.class)
  @PostMapping("/auth/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest usuario) {
    String token = usuarioService.getTokenProvider(usuario);
    return ResponseEntity.ok(new AuthResponse(token));
  }

  @ApiOperation(value = "Usuário Autenticado.", notes = "API para mostrar o usuário autenticado.",
      response = UsuarioPrincipal.class)
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/auth/usuario")
  public @ResponseBody UsuarioPrincipal getUsuario() throws AppException {
    try {
      UsuarioPrincipal usuarioCorrente = getUsuarioCorrente();
      return new UsuarioPrincipal(usuarioService.getByNomeUsuario(usuarioCorrente
        .getNomeUsuario()));
    } catch (Exception e) {
      throw new AppException(e.getMessage(), AppErrorCode.ITEM_NOT_FOUND);
    }
  }

  private UsuarioPrincipal getUsuarioCorrente() throws AppException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UsuarioPrincipal) {
      return (UsuarioPrincipal) authentication.getPrincipal();
    } else {
      throw new AppException("Você não está autorizado a realizar esta operação!",
        AppErrorCode.AUTHENTICATION);
    }
  }
}
