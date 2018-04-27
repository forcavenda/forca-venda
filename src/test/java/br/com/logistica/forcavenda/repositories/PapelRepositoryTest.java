package br.com.logistica.forcavenda.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class PapelRepositoryTest {

  private static Logger LOGGER = LoggerFactory.getLogger(PapelRepositoryTest.class);

  @Autowired
  private PapelRepository papelRepository;

  @Before
  public void setUp() throws Exception {
    LOGGER.info("Limpando coleção de papeis");
    papelRepository.deleteAll();
  }

  @Test
  public void testFindByNome() throws Exception {
    LOGGER.info("Criando coleção de papeis");

    Papel papel1 = new Papel("ROLE_ADMIN");
    papelRepository.insert(papel1);

    Papel papel2 = new Papel("ROLE_USER");
    papelRepository.save(papel2);

    long total = papelRepository.findAll().size();

    assertThat(total).isEqualTo(2);

    Papel byNome = papelRepository.findByNome("ROLE_ADMIN")
      .orElseThrow(() -> new RuntimeException("Papel não encontrado"));
    assertThat(byNome.getNome(), is(equalTo("ROLE_ADMIN")));
  }

  @After
  public void tearDown() throws Exception {
    LOGGER.info("Limpando coleção de papeis");
    papelRepository.deleteAll();
  }
}
