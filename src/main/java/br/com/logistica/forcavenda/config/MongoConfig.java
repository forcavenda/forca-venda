package br.com.logistica.forcavenda.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

import br.com.logistica.forcavenda.models.Usuario;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "br.com.logistica.forcavenda.models")
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoConfiguration {

  @Value("${spring.application.name}")
  private String proAppName;

  @Value("${spring.data.mongodb.authentication-database}")
  private String authenticationDatabase;

  @Value("${spring.data.mongodb.host}")
  private String mongoHost;

  @Value("${spring.data.mongodb.port}")
  private Integer mongoPort;

  @Value("${spring.data.mongodb.database}")
  private String mongoDB;

  @Value("${spring.data.mongodb.username}")
  private String mongoUser;

  @Value("${spring.data.mongodb.password}")
  private String mongoPwd;

  @Bean
  public AuditorAware<Usuario> auditorProvider() {

    // String uname = SecurityContextHolder.getContext().getAuthentication().getName();
    // return Optional.of(uname);

    // return ReactiveSecurityContextHolder.getContext()
    // .map(SecurityContext::getAuthentication)
    // .filter(Authentication::isAuthenticated)
    // .map(Authentication::getName)
    // .switchIfEmpty(Mono.just("julius"))
    // .blockOptional();

    return () -> {
      Usuario usuario = new Usuario();
      usuario.setNome("Mauricio Rocha");
      usuario.setNomeUsuario("alcaphone");
      return Optional.of(usuario);
    };
  }

  @Bean
  @Override
  public MongoClient mongoClient() {
    return new MongoClient(mongoHost);
    /*
     * return new MongoClient(new ServerAddress(mongoHost, mongoPort), MongoCredential
     * .createCredential(mongoUser, authenticationDatabase, mongoPwd.toCharArray()),
     * new MongoClientOptions.Builder().build());
     */
  }

  @Override
  protected String getDatabaseName() {
    return mongoDB;
  }

  @Bean
  @Override
  public MongoTemplate mongoTemplate() throws Exception {
    return new MongoTemplate(mongoClient(), getDatabaseName());
  }
}
