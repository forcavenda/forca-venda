package br.com.logistica.forcavenda.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.logistica.forcavenda.payload.mapeamento.DTOModelMapper;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final ApplicationContext applicationContext;
  private final MongoTemplate mongoTemplate;

  @Autowired
  public WebMvcConfig(ApplicationContext applicationContext, MongoTemplate mongoTemplate) {
    this.applicationContext = applicationContext;
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(
      applicationContext).build();
    resolvers.add(new DTOModelMapper(objectMapper, mongoTemplate));
  }
}
