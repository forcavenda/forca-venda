package br.com.logistica.forcavenda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.JsonNode;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
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
    TypeResolver typeResolver = new TypeResolver();
    final ResolvedType jsonNodeType = typeResolver.resolve(
      JsonNode.class);
    final ResolvedType stringType = typeResolver.resolve(
      String.class);

    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("Mobile Fomentar")
      .apiInfo(apiInfo())
      .alternateTypeRules(
        new AlternateTypeRule(
          jsonNodeType,
          stringType))
      .select()
      .apis(RequestHandlerSelectors.basePackage("br.com.logistica.forcavenda"))
      .paths(PathSelectors.ant("/api/**"))
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Documentação das API Mobile Fomentar")
      .description(
        "This application demonstrates documenting os Spring Boot app with Swagger using SpringFox.")
      .contact(new Contact("Suporte Mobile Fomentar", "http://www.mobilefomentar.com",
        "suporte@mobilefomentar.com"))
      .license("Apache License Version 2.0")
      .licenseUrl("https://github.com/forcavenda/forca-venda")
      .version("1.0.0")
      .build();
  }
}
