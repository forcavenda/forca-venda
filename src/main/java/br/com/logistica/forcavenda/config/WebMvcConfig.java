package br.com.logistica.forcavenda.config;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.logistica.forcavenda.payload.mapeamento.DTOModelMapper;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  private ApplicationContext applicationContext;

  @Qualifier("mongoTemplate")
  private MongoTemplate mongoTemplate;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(
      applicationContext).build();
    resolvers.add(new DTOModelMapper(objectMapper, mongoTemplate));
  }

  @Bean
  public VelocityEngine velocityEngine() throws VelocityException, IOException {
    VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
    Properties props = new Properties();
    props.put("resource.loader", "class");
    props.put("class.resource.loader.class",
      "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    factory.setVelocityProperties(props);

    return factory.createVelocityEngine();
  }
}
