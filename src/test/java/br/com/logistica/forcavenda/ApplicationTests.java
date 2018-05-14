package br.com.logistica.forcavenda;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

  public static final String ADMIN_USERNAME = "alcaphone";
  public static final String ADMIN_PASSWORD = "senha";

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders
      .webAppContextSetup(context)
      .apply(springSecurity())
      .alwaysDo(print())
      .build();
  }

  @Test
  public void loginAvailableForAll() throws Exception {
    mockMvc
      .perform(get("/api/auth"))
      .andExpect(status().isOk());
  }

  @Test
  public void adminCanLog() throws Exception {
    mockMvc
      .perform(formLogin("/api/auth")
        .acceptMediaType(MediaType.APPLICATION_JSON)
        .user("nomeUsuario", ApplicationTests.ADMIN_USERNAME)
        .password("senha", ApplicationTests.ADMIN_PASSWORD))
      .andExpect(status().isFound())
      .andExpect(redirectedUrl("/api/dashboard"))
      .andExpect(authenticated().withUsername(ApplicationTests.ADMIN_USERNAME));

    mockMvc
      .perform(logout())
      .andExpect(status().isFound())
      .andExpect(redirectedUrl("/api/index"));
  }

  @Test
  public void invalidLoginDenied() throws Exception {
    mockMvc
      .perform(formLogin("/api/auth")
        .acceptMediaType(MediaType.APPLICATION_JSON)
        .user("nomeUsuario", "")
        .password("senha", ApplicationTests.ADMIN_PASSWORD))
      .andExpect(status().isFound())
      .andExpect(redirectedUrl("/api/auth?error=true"))
      .andExpect(unauthenticated());

    mockMvc
      .perform(get("/api/auth?error=true"))
      .andExpect(content().string(containsString("Usuário e senha invalido")));
  }

}
