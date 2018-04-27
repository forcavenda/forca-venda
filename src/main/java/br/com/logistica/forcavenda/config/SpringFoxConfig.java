package br.com.logistica.forcavenda.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@PropertySource("classpath:swagger.properties")
public class SpringFoxConfig {
  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("br.com.logistica.forcavenda"))
      .paths(PathSelectors.ant("/api/**"))
      .build()
      .apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(
      "Documentação das API Mobile Fomentar",
      "This application demonstrates documenting os Spring Boot app with Swagger using SpringFox.",
      "1.0.0",
      "TERMS OF SERVICE URL",
      new Contact("Suporte Mobile Fomentar", "http://www.mobilefomentar.com",
        "suporte@mobilefomentar.com"),
      "MIT License",
      "LICENSE URL",
      Collections.emptyList());
  }
}
