package br.com.logistica.forcavenda.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.logistica.forcavenda.models.Papel;
import br.com.logistica.forcavenda.models.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class UsuarioRepositoryTest {

  private static Logger LOGGER = LoggerFactory.getLogger(UsuarioRepositoryTest.class);

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PapelRepository papelRepository;

  Papel papel1;
  Papel papel2;

  @Before
  public void setUp() throws Exception {
    LOGGER.info("Limpando coleção de papeis");
    papelRepository.deleteAll();

    LOGGER.info("Criando coleção de papeis");

    papel1 = papelRepository.insert(new Papel("ROLE_ADMIN").criarId());
    papel2 = papelRepository.save(new Papel("ROLE_USER").criarId());

    LOGGER.info("Limpando coleção de usuarios");
    usuarioRepository.deleteAll();

    criarUsuario();
  }

  @After
  public void tearDown() throws Exception {
    LOGGER.info("Limpando coleção de papeis");
    papelRepository.deleteAll();

    LOGGER.info("Limpando coleção de usuarios");
    usuarioRepository.deleteAll();
  }

  private void criarUsuario() {
    Usuario usuario1 = new Usuario();
    usuario1.criarId();
    usuario1.setNome("Usuario1");
    usuario1.setNomeUsuario("login1");
    usuario1.setEmail("email1@email.com");
    usuario1.setSenha("123");
    usuario1.setHabilitado("S");
    usuario1.setSupervisor(null);
    usuario1.setPapeis(Collections.singleton(papel1));

    usuarioRepository.insert(usuario1);

    Usuario usuario2 = new Usuario();
    usuario2.criarId();
    usuario2.setNome("Usuario2");
    usuario2.setNomeUsuario("login2");
    usuario2.setEmail("email2@email.com");
    usuario2.setSenha("123");
    usuario2.setHabilitado("S");
    usuario2.setSupervisor(usuario1);
    usuario2.setPapeis(Collections.singleton(papel2));

    usuarioRepository.save(usuario2);
  }

  @Test
  public void testGetByNomeUsuario() throws Exception {
    Usuario byNomeUsuario = usuarioRepository.getByNomeUsuario("login2").orElseThrow(
      () -> new RuntimeException("Usuário não encontrado"));
    assertThat(byNomeUsuario.getNome(), is(equalTo("Usuario2")));
    assertThat(byNomeUsuario.getEmail(), is(equalTo("email2@email.com")));
    long papeis = byNomeUsuario.getPapeis().size();
    assertThat(papeis).isEqualTo(1);
  }

  @Test
  public void testFindByNomeUsuarioOrEmail() throws Exception {
    Usuario byNomeUsuario = usuarioRepository.findByNomeUsuarioOrEmail("Usuario1",
      "email1@email.com").orElseThrow(
      () -> new RuntimeException("Usuário não encontrado"));
    assertThat(byNomeUsuario.getNome(), is(equalTo("Usuario1")));
    assertThat(byNomeUsuario.getEmail(), is(equalTo("email1@email.com")));
    long papeis = byNomeUsuario.getPapeis().size();
    assertThat(papeis).isEqualTo(1);
  }
}
