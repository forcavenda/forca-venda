package br.com.logistica.forcavenda.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.logistica.forcavenda.models.Papel;
import br.com.logistica.forcavenda.models.Usuario;
import br.com.logistica.forcavenda.service.PapelService;
import br.com.logistica.forcavenda.service.UsuarioService;

@Component
public class InicializarSistema implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private PapelService papelService;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    List<Papel> papeis = papelService.findAll();
    if (papeis.isEmpty()) {

      papelService.insert(new Papel("ROLE_ADMIN").criarId());
      papelService.insert(new Papel("ROLE_SUPERV").criarId());
      papelService.insert(new Papel("ROLE_USER").criarId());
      Papel papel = papelService.findByNome("ROLE_ADMIN");
      Usuario entity = new Usuario();
      entity.setNomeUsuario("alcaphone");
      entity.setNome("Nome");
      entity.setEmail("email@email.com");
      entity.setSenha("senha");
      entity.setPapeis(Collections.singleton(papel));
      usuarioService.insert(entity);

    }
  }
}
