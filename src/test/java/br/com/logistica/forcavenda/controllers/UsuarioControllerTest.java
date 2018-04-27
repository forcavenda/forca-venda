package br.com.logistica.forcavenda.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioControllerTest {

  @Autowired
  private ApplicationContext context;
  private WebTestClient rest;

  @Before
  public void setUp() throws Exception {
    rest = WebTestClient
      .bindToApplicationContext(context)
      // .apply(springSecurity())
      .configureClient()
      // .filter(basicAuthentication("julius", "secret"))
      .build();
  }

  @Test
  public void teste() {
    System.out.println("teste");
  }

}
