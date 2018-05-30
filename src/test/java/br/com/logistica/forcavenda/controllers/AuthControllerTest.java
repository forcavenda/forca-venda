package br.com.logistica.forcavenda.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import br.com.logistica.forcavenda.security.ApplicationTests;
import br.com.logistica.forcavenda.security.Authority;

public class AuthControllerTest extends ApplicationTests {

  @Test
  public void testGetUsuario() throws Exception {

    doGet("/api/auth/usuario")
      .andExpect(status().isUnauthorized());

    loginSysAdmin();
    doGet("/api/auth/usuario")
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.authority", is(Authority.ROLE_ADMIN.name())))
      .andExpect(jsonPath("$.nomeUsuario", is(ADMIN_USERNAME)));

    loginEmpresa();
    doGet("/api/auth/user")
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.authority", is(Authority.ROLE_EMPRESA.name())))
      .andExpect(jsonPath("$.nomeUsuario", is(cnpj0)));

    loginUsuario();
    doGet("/api/auth/usuario")
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.authority", is(Authority.ROLE_USUARIO.name())))
      .andExpect(jsonPath("$.nomeUsuario", is(nomeUsuario1)));
  }

  @Test
  public void testLoginLogout() throws Exception {
    loginSysAdmin();
    doGet("/api/auth/usuario")
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.authority", is(Authority.ROLE_ADMIN.name())))
      .andExpect(jsonPath("$.nomeUsuario", is(ADMIN_USERNAME)));

    logout();
    doGet("/api/auth/usuario")
      .andExpect(status().isUnauthorized());
  }

  @Test
  public void testRefreshToken() throws Exception {
    loginSysAdmin();
    doGet("/api/auth/usuario")
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.authority", is(Authority.ROLE_ADMIN.name())))
      .andExpect(jsonPath("$.nomeUsuario", is(ADMIN_USERNAME)));

    refreshToken();
    doGet("/api/auth/usuario")
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.authority", is(Authority.ROLE_ADMIN.name())))
      .andExpect(jsonPath("$.nomeUsuario", is(ADMIN_USERNAME)));
  }

}
