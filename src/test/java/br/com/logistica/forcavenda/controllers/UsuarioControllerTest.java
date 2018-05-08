package br.com.logistica.forcavenda.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.logistica.forcavenda.service.UsuarioService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
@WebMvcTest(controllers = UsuarioController.class)
public class UsuarioControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;
  @MockBean
  private UsuarioService usuarioServiceMock;

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testGetAll() throws Exception {
    assertThat(usuarioServiceMock).isNotNull();
    mockMvc.perform(MockMvcRequestBuilders.get("/api/usuario/all"))
      .andExpect(status().isOk())
      .andDo(print());
  }

}
